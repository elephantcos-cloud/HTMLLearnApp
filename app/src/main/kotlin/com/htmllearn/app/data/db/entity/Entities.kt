package com.htmllearn.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "শিক্ষার্থী",
    val xp: Int = 0,
    val level: Int = 1,
    val streak: Int = 0,
    val maxStreak: Int = 0,
    val lastStudyDate: Long = 0L,
    val studyGoalMinutes: Int = 30,
    val totalStudyTimeSeconds: Long = 0L,
    val totalLessonsCompleted: Int = 0,
    val totalQuizzesPassed: Int = 0,
    val joinDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: String,
    val isCompleted: Boolean = false,
    val isStarted: Boolean = false,
    val quizBestScore: Int = 0,
    val quizAttempts: Int = 0,
    val studyTimeSeconds: Long = 0L,
    val completedAt: Long = 0L,
    val lastAttemptAt: Long = 0L
)

@Entity(tableName = "daily_activity")
data class DailyActivityEntity(
    @PrimaryKey val date: String,
    val studyMinutes: Int = 0,
    val lessonsCompleted: Int = 0,
    val xpEarned: Int = 0,
    val quizzesTaken: Int = 0
)

@Entity(tableName = "badge")
data class BadgeEntity(
    @PrimaryKey val badgeId: String,
    val earnedDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "session_log")
data class SessionLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lessonId: String,
    val startTime: Long,
    val endTime: Long,
    val durationSeconds: Long
)
