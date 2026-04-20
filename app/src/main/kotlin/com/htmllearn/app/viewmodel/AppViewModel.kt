package com.htmllearn.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.htmllearn.app.content.*
import com.htmllearn.app.data.db.entity.*
import com.htmllearn.app.data.preferences.UserPreferences
import com.htmllearn.app.data.preferences.dataStore
import com.htmllearn.app.data.repository.AppRepository
import com.htmllearn.app.notification.NotificationHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardState(
    val user: UserEntity? = null,
    val completedCount: Int = 0,
    val totalLessons: Int = 0,
    val todayMinutes: Int = 0,
    val recentBadges: List<BadgeEntity> = emptyList(),
    val recentActivity: List<DailyActivityEntity> = emptyList(),
    val levelProgress: Float = 0f,
    val nextLevelXp: Int = 0,
    val currentLevelXp: Int = 0,
    val levelName: String = ""
)

data class LessonState(
    val allProgress: Map<String, LessonProgressEntity> = emptyMap()
)

data class ProfileState(
    val user: UserEntity? = null,
    val badges: List<BadgeEntity> = emptyList(),
    val activity: List<DailyActivityEntity> = emptyList(),
    val levelName: String = "",
    val levelProgress: Float = 0f,
    val studyGoalMinutes: Int = 30,
    val notificationEnabled: Boolean = true,
    val morningHour: Int = 9,
    val morningMinute: Int = 0,
    val eveningHour: Int = 20,
    val eveningMinute: Int = 0
)

class AppViewModel(application: Application) : AndroidViewModel(application) {

    val repository = AppRepository(application)

    val dashboardState: StateFlow<DashboardState> = combine(
        repository.getUser(),
        repository.getCompletedCount(),
        repository.getAllBadges(),
        repository.getRecentActivity()
    ) { user, completed, badges, activity ->
        val level = user?.level ?: 1
        val xp = user?.xp ?: 0
        val currentLvlXp = repository.getXpForLevel(level)
        val nextLvlXp = repository.getXpForNextLevel(level)
        val progress = if (nextLvlXp > currentLvlXp)
            (xp - currentLvlXp).toFloat() / (nextLvlXp - currentLvlXp)
        else 1f
        val todayActivity = activity.firstOrNull {
            it.date == java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                .format(java.util.Date())
        }
        DashboardState(
            user = user,
            completedCount = completed,
            totalLessons = LessonContent.getAllLessons().size,
            todayMinutes = todayActivity?.studyMinutes ?: 0,
            recentBadges = badges.take(5),
            recentActivity = activity.take(30),
            levelProgress = progress.coerceIn(0f, 1f),
            nextLevelXp = nextLvlXp,
            currentLevelXp = currentLvlXp,
            levelName = repository.getLevelName(level)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardState())

    val lessonState: StateFlow<LessonState> = repository.getAllLessonsProgress()
        .map { list -> LessonState(list.associateBy { it.lessonId }) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LessonState())

    val profileState: StateFlow<ProfileState> = combine(
        repository.getUser(),
        repository.getAllBadges(),
        repository.getRecentActivity(),
        repository.prefs.studyGoalMinutes,
        repository.prefs.notificationEnabled
    ) { user, badges, activity, goal, notifEnabled ->
        val level = user?.level ?: 1
        val xp = user?.xp ?: 0
        val currentLvlXp = repository.getXpForLevel(level)
        val nextLvlXp = repository.getXpForNextLevel(level)
        val progress = if (nextLvlXp > currentLvlXp)
            (xp - currentLvlXp).toFloat() / (nextLvlXp - currentLvlXp)
        else 1f
        ProfileState(
            user = user,
            badges = badges,
            activity = activity,
            levelName = repository.getLevelName(level),
            levelProgress = progress.coerceIn(0f, 1f),
            studyGoalMinutes = goal,
            notificationEnabled = notifEnabled
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileState())

    private val _quizResult = MutableStateFlow<Pair<Int, Int>?>(null)
    val quizResult: StateFlow<Pair<Int, Int>?> = _quizResult

    private val _badgeUnlocked = MutableStateFlow<BadgeInfo?>(null)
    val badgeUnlocked: StateFlow<BadgeInfo?> = _badgeUnlocked

    private val _levelUp = MutableStateFlow<Int?>(null)
    val levelUp: StateFlow<Int?> = _levelUp

    private var sessionStartTime: Long = 0L
    private var currentSessionLessonId: String = ""

    fun initUser(name: String) = viewModelScope.launch {
        repository.initUser(name)
    }

    fun startLesson(lessonId: String) = viewModelScope.launch {
        repository.markLessonStarted(lessonId)
        sessionStartTime = System.currentTimeMillis()
        currentSessionLessonId = lessonId
    }

    fun completeLesson(lessonId: String) = viewModelScope.launch {
        val oldUser = repository.getUser().first()
        val oldLevel = oldUser?.level ?: 1
        repository.markLessonCompleted(lessonId)
        if (sessionStartTime > 0) {
            repository.logSession(lessonId, sessionStartTime, System.currentTimeMillis())
            sessionStartTime = 0L
        }
        val newUser = repository.getUser().first()
        val newLevel = newUser?.level ?: 1
        if (newLevel > oldLevel) {
            _levelUp.value = newLevel
            NotificationHelper.showLevelUp(
                getApplication(),
                newLevel,
                repository.getLevelName(newLevel)
            )
        }
        checkNewBadges(oldUser)
    }

    fun submitQuiz(lessonId: String, score: Int, total: Int) = viewModelScope.launch {
        val oldUser = repository.getUser().first()
        repository.saveQuizResult(lessonId, score, total)
        _quizResult.value = Pair(score, total)
        if (score == total) repository.awardBadge("perfect_quiz")
        checkNewBadges(oldUser)
    }

    private suspend fun checkNewBadges(oldUser: UserEntity?) {
        val oldBadges = repository.getAllBadges().first().map { it.badgeId }.toSet()
        repository.checkAndAwardBadges()
        val newBadges = repository.getAllBadges().first()
        val newlyEarned = newBadges.firstOrNull { it.badgeId !in oldBadges }
        if (newlyEarned != null) {
            val info = BadgeContent.getBadgeById(newlyEarned.badgeId)
            _badgeUnlocked.value = info
            if (info != null) {
                NotificationHelper.showAchievement(
                    getApplication(),
                    info.title,
                    info.description
                )
            }
        }
    }

    fun clearQuizResult() { _quizResult.value = null }
    fun clearBadgeUnlocked() { _badgeUnlocked.value = null }
    fun clearLevelUp() { _levelUp.value = null }

    fun setStudyGoal(minutes: Int) = viewModelScope.launch {
        repository.updateStudyGoal(minutes)
    }

    fun setNotificationEnabled(enabled: Boolean) = viewModelScope.launch {
        repository.prefs.setNotificationEnabled(enabled)
        if (enabled) {
            NotificationHelper.scheduleInitialReminders(getApplication())
        } else {
            NotificationHelper.cancelAll(getApplication())
        }
    }

    fun setMorningTime(hour: Int, minute: Int) = viewModelScope.launch {
        repository.prefs.setMorningTime(hour, minute)
        NotificationHelper.scheduleMorningReminder(getApplication(), hour, minute)
    }

    fun setEveningTime(hour: Int, minute: Int) = viewModelScope.launch {
        repository.prefs.setEveningTime(hour, minute)
        NotificationHelper.scheduleEveningReminder(getApplication(), hour, minute)
    }
}
