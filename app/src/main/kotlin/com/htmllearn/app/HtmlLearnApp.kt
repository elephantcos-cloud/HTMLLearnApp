package com.htmllearn.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.htmllearn.app.notification.NotificationHelper

class HtmlLearnApp : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        instance = this
        createNotificationChannels()
        NotificationHelper.scheduleInitialReminders(this)
    }

    private fun createNotificationChannels() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val studyChannel = NotificationChannel(
            NotificationHelper.CHANNEL_STUDY_REMINDER,
            "পড়ার রিমাইন্ডার",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "প্রতিদিনের HTML পড়ার রিমাইন্ডার"
            enableVibration(true)
            enableLights(true)
        }

        val streakChannel = NotificationChannel(
            NotificationHelper.CHANNEL_STREAK,
            "Streak সতর্কতা",
            NotificationManager.IMPORTANCE_MAX
        ).apply {
            description = "Streak হারানোর আগে সতর্কতা"
            enableVibration(true)
        }

        val achievementChannel = NotificationChannel(
            NotificationHelper.CHANNEL_ACHIEVEMENT,
            "অর্জন",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "নতুন badge ও XP অর্জন"
        }

        manager.createNotificationChannels(listOf(studyChannel, streakChannel, achievementChannel))
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    companion object {
        lateinit var instance: HtmlLearnApp
            private set
    }
}
