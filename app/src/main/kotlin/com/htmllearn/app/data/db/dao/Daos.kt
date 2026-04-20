package com.htmllearn.app.data.db.dao

import androidx.room.*
import com.htmllearn.app.data.db.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = 1")
    fun getUser(): Flow<UserEntity?>

    @Query("SELECT * FROM user WHERE id = 1")
    suspend fun getUserOnce(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE user SET xp = xp + :amount WHERE id = 1")
    suspend fun addXp(amount: Int)

    @Query("UPDATE user SET streak = :streak, maxStreak = :maxStreak, lastStudyDate = :date WHERE id = 1")
    suspend fun updateStreak(streak: Int, maxStreak: Int, date: Long)

    @Query("UPDATE user SET totalStudyTimeSeconds = totalStudyTimeSeconds + :seconds WHERE id = 1")
    suspend fun addStudyTime(seconds: Long)

    @Query("UPDATE user SET totalLessonsCompleted = totalLessonsCompleted + 1 WHERE id = 1")
    suspend fun incrementLessonsCompleted()

    @Query("UPDATE user SET studyGoalMinutes = :minutes WHERE id = 1")
    suspend fun updateStudyGoal(minutes: Int)

    @Query("UPDATE user SET level = :level WHERE id = 1")
    suspend fun updateLevel(level: Int)
}

@Dao
interface LessonProgressDao {
    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId")
    suspend fun getProgress(lessonId: String): LessonProgressEntity?

    @Query("SELECT * FROM lesson_progress")
    fun getAllProgress(): Flow<List<LessonProgressEntity>>

    @Query("SELECT * FROM lesson_progress WHERE isCompleted = 1")
    suspend fun getCompletedLessons(): List<LessonProgressEntity>

    @Query("SELECT COUNT(*) FROM lesson_progress WHERE isCompleted = 1")
    fun getCompletedCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: LessonProgressEntity)

    @Query("UPDATE lesson_progress SET isCompleted = 1, completedAt = :time WHERE lessonId = :lessonId")
    suspend fun markCompleted(lessonId: String, time: Long = System.currentTimeMillis())

    @Query("UPDATE lesson_progress SET quizBestScore = :score, quizAttempts = quizAttempts + 1 WHERE lessonId = :lessonId")
    suspend fun updateQuizScore(lessonId: String, score: Int)
}

@Dao
interface DailyActivityDao {
    @Query("SELECT * FROM daily_activity WHERE date = :date")
    suspend fun getActivity(date: String): DailyActivityEntity?

    @Query("SELECT * FROM daily_activity ORDER BY date DESC LIMIT 90")
    fun getRecentActivity(): Flow<List<DailyActivityEntity>>

    @Query("SELECT * FROM daily_activity ORDER BY date DESC LIMIT 7")
    suspend fun getLast7Days(): List<DailyActivityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(activity: DailyActivityEntity)

    @Query("UPDATE daily_activity SET studyMinutes = studyMinutes + :minutes, xpEarned = xpEarned + :xp WHERE date = :date")
    suspend fun addStudy(date: String, minutes: Int, xp: Int)
}

@Dao
interface BadgeDao {
    @Query("SELECT * FROM badge ORDER BY earnedDate DESC")
    fun getAllBadges(): Flow<List<BadgeEntity>>

    @Query("SELECT * FROM badge")
    suspend fun getAllBadgesOnce(): List<BadgeEntity>

    @Query("SELECT COUNT(*) FROM badge WHERE badgeId = :badgeId")
    suspend fun hasBadge(badgeId: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBadge(badge: BadgeEntity)
}

@Dao
interface SessionLogDao {
    @Insert
    suspend fun insertSession(session: SessionLogEntity)

    @Query("SELECT SUM(durationSeconds) FROM session_log WHERE date(startTime/1000, 'unixepoch', 'localtime') = :date")
    suspend fun getTotalSecondsForDate(date: String): Long?
}
