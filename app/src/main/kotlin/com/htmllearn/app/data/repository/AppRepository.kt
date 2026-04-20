package com.htmllearn.app.data.repository

import android.content.Context
import com.htmllearn.app.content.*
import com.htmllearn.app.data.db.AppDatabase
import com.htmllearn.app.data.db.entity.*
import com.htmllearn.app.data.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class AppRepository(context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val userDao = db.userDao()
    private val lessonProgressDao = db.lessonProgressDao()
    private val dailyActivityDao = db.dailyActivityDao()
    private val badgeDao = db.badgeDao()
    private val sessionLogDao = db.sessionLogDao()
    val prefs = UserPreferences(context)

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // ==================== User ====================
    fun getUser(): Flow<UserEntity?> = userDao.getUser()

    suspend fun initUser(name: String) {
        val existing = userDao.getUserOnce()
        if (existing == null) {
            userDao.insertUser(UserEntity(name = name))
        }
    }

    suspend fun addXp(amount: Int) {
        userDao.addXp(amount)
        val user = userDao.getUserOnce() ?: return
        val newXp = user.xp + amount
        val newLevel = calculateLevel(newXp)
        if (newLevel > user.level) {
            userDao.updateLevel(newLevel)
        }
    }

    suspend fun updateStreak() {
        val user = userDao.getUserOnce() ?: return
        val today = dateFormat.format(Date())
        val lastDate = if (user.lastStudyDate > 0) dateFormat.format(Date(user.lastStudyDate)) else ""
        val yesterday = dateFormat.format(Date(System.currentTimeMillis() - 86400000L))

        val newStreak = when {
            lastDate == today -> user.streak
            lastDate == yesterday -> user.streak + 1
            else -> 1
        }
        val maxStreak = maxOf(user.maxStreak, newStreak)
        userDao.updateStreak(newStreak, maxStreak, System.currentTimeMillis())
    }

    suspend fun addStudyTime(seconds: Long) {
        userDao.addStudyTime(seconds)
    }

    suspend fun updateStudyGoal(minutes: Int) {
        userDao.updateStudyGoal(minutes)
        prefs.setStudyGoal(minutes)
    }

    fun calculateLevel(xp: Int): Int = when {
        xp < 500 -> 1
        xp < 1500 -> 2
        xp < 3000 -> 3
        xp < 5500 -> 4
        xp < 9000 -> 5
        xp < 14000 -> 6
        xp < 20000 -> 7
        xp < 30000 -> 8
        xp < 45000 -> 9
        else -> 10
    }

    fun getLevelName(level: Int): String = when (level) {
        1 -> "শিক্ষানবিশ"
        2 -> "শিক্ষার্থী"
        3 -> "অনুশীলনকারী"
        4 -> "দক্ষ"
        5 -> "পারদর্শী"
        6 -> "বিশেষজ্ঞ"
        7 -> "অভিজ্ঞ"
        8 -> "মাস্টার"
        9 -> "গ্র্যান্ড মাস্টার"
        10 -> "HTML গুরু"
        else -> "শিক্ষানবিশ"
    }

    fun getXpForLevel(level: Int): Int = when (level) {
        1 -> 0; 2 -> 500; 3 -> 1500; 4 -> 3000; 5 -> 5500
        6 -> 9000; 7 -> 14000; 8 -> 20000; 9 -> 30000; 10 -> 45000
        else -> 0
    }

    fun getXpForNextLevel(level: Int): Int = getXpForLevel(level + 1)

    // ==================== Lessons ====================
    fun getAllLessonsProgress(): Flow<List<LessonProgressEntity>> =
        lessonProgressDao.getAllProgress()

    fun getCompletedCount(): Flow<Int> = lessonProgressDao.getCompletedCount()

    suspend fun getLessonProgress(lessonId: String): LessonProgressEntity? =
        lessonProgressDao.getProgress(lessonId)

    suspend fun markLessonStarted(lessonId: String) {
        val existing = lessonProgressDao.getProgress(lessonId)
        if (existing == null) {
            lessonProgressDao.insertOrUpdate(
                LessonProgressEntity(lessonId = lessonId, isStarted = true, lastAttemptAt = System.currentTimeMillis())
            )
        } else {
            lessonProgressDao.insertOrUpdate(existing.copy(isStarted = true, lastAttemptAt = System.currentTimeMillis()))
        }
    }

    suspend fun markLessonCompleted(lessonId: String) {
        val existing = lessonProgressDao.getProgress(lessonId)
        val now = System.currentTimeMillis()
        if (existing == null) {
            lessonProgressDao.insertOrUpdate(
                LessonProgressEntity(lessonId = lessonId, isCompleted = true, isStarted = true, completedAt = now)
            )
        } else if (!existing.isCompleted) {
            lessonProgressDao.insertOrUpdate(existing.copy(isCompleted = true, completedAt = now))
            userDao.incrementLessonsCompleted()
            updateStreak()
        }
        addXp(50)
        updateDailyActivity(lessonsCompleted = 1, xpEarned = 50)
        checkAndAwardBadges()
    }

    suspend fun saveQuizResult(lessonId: String, score: Int, total: Int) {
        val existing = lessonProgressDao.getProgress(lessonId)
        if (existing == null) {
            lessonProgressDao.insertOrUpdate(
                LessonProgressEntity(lessonId = lessonId, quizBestScore = score, quizAttempts = 1)
            )
        } else {
            val bestScore = maxOf(existing.quizBestScore, score)
            lessonProgressDao.insertOrUpdate(existing.copy(quizBestScore = bestScore, quizAttempts = existing.quizAttempts + 1))
        }
        val xp = when {
            score == total -> 100
            score >= total * 0.8 -> 75
            score >= total * 0.6 -> 50
            else -> 25
        }
        addXp(xp)
        updateDailyActivity(quizzesTaken = 1, xpEarned = xp)
    }

    // ==================== Daily Activity ====================
    suspend fun updateDailyActivity(
        studyMinutes: Int = 0,
        lessonsCompleted: Int = 0,
        xpEarned: Int = 0,
        quizzesTaken: Int = 0
    ) {
        val today = dateFormat.format(Date())
        val existing = dailyActivityDao.getActivity(today)
        if (existing == null) {
            dailyActivityDao.insertOrUpdate(
                DailyActivityEntity(date = today, studyMinutes = studyMinutes,
                    lessonsCompleted = lessonsCompleted, xpEarned = xpEarned, quizzesTaken = quizzesTaken)
            )
        } else {
            dailyActivityDao.insertOrUpdate(
                existing.copy(
                    studyMinutes = existing.studyMinutes + studyMinutes,
                    lessonsCompleted = existing.lessonsCompleted + lessonsCompleted,
                    xpEarned = existing.xpEarned + xpEarned,
                    quizzesTaken = existing.quizzesTaken + quizzesTaken
                )
            )
        }
    }

    fun getRecentActivity(): Flow<List<DailyActivityEntity>> = dailyActivityDao.getRecentActivity()

    // ==================== Badges ====================
    fun getAllBadges(): Flow<List<BadgeEntity>> = badgeDao.getAllBadges()

    suspend fun awardBadge(badgeId: String) {
        if (badgeDao.hasBadge(badgeId) == 0) {
            badgeDao.insertBadge(BadgeEntity(badgeId = badgeId))
        }
    }

    suspend fun checkAndAwardBadges() {
        val user = userDao.getUserOnce() ?: return
        val completedLessons = lessonProgressDao.getCompletedLessons()
        val allBadges = badgeDao.getAllBadgesOnce().map { it.badgeId }

        if (completedLessons.isNotEmpty() && "first_lesson" !in allBadges)
            awardBadge("first_lesson")
        if (completedLessons.size >= 5 && "five_lessons" !in allBadges)
            awardBadge("five_lessons")
        if (completedLessons.size >= 10 && "ten_lessons" !in allBadges)
            awardBadge("ten_lessons")
        if (completedLessons.size >= 25 && "twenty_five_lessons" !in allBadges)
            awardBadge("twenty_five_lessons")
        if (completedLessons.size >= LessonContent.getAllLessons().size && "all_lessons" !in allBadges)
            awardBadge("all_lessons")
        if (user.streak >= 7 && "week_streak" !in allBadges)
            awardBadge("week_streak")
        if (user.streak >= 30 && "month_streak" !in allBadges)
            awardBadge("month_streak")
        if (user.xp >= 1000 && "xp_1000" !in allBadges)
            awardBadge("xp_1000")
        if (user.xp >= 5000 && "xp_5000" !in allBadges)
            awardBadge("xp_5000")
        if (user.level >= 5 && "level_5" !in allBadges)
            awardBadge("level_5")
        if (user.totalQuizzesPassed >= 10 && "quiz_master" !in allBadges)
            awardBadge("quiz_master")
    }

    // ==================== Session Logging ====================
    suspend fun logSession(lessonId: String, startTime: Long, endTime: Long) {
        val duration = (endTime - startTime) / 1000
        sessionLogDao.insertSession(
            SessionLogEntity(lessonId = lessonId, startTime = startTime, endTime = endTime, durationSeconds = duration)
        )
        userDao.addStudyTime(duration)
        val minutes = (duration / 60).toInt()
        if (minutes > 0) updateDailyActivity(studyMinutes = minutes)
    }
}
