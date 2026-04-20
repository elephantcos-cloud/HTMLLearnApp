package com.htmllearn.app.notification

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.htmllearn.app.MainActivity
import com.htmllearn.app.R
import java.util.*
import java.util.concurrent.TimeUnit

object NotificationHelper {

    const val CHANNEL_STUDY_REMINDER = "study_reminder"
    const val CHANNEL_STREAK = "streak_warning"
    const val CHANNEL_ACHIEVEMENT = "achievement"

    private val MORNING_MESSAGES = listOf(
        "সুপ্রভাত! আজকের HTML পাঠ শুরু করার সেরা সময় এখনই।",
        "নতুন দিন, নতুন lesson! HTML শেখা চালিয়ে যাও।",
        "একটু একটু করে প্রতিদিন পড়লেই বড় developer হওয়া যায়।",
        "সকালের পড়া সোনার চেয়ে দামি! আজকের lesson শুরু করো।",
        "প্রতিদিন একটি lesson — ৩ মাসে HTML expert!"
    )

    private val AFTERNOON_MESSAGES = listOf(
        "দুপুরের বিরতিতে একটু HTML শিখে নাও?",
        "৫ মিনিট সময় আছে? একটি lesson দেখে নাও!",
        "আজকের লক্ষ্য পূরণ হয়েছে? না হলে এখনই সময়।",
        "HTML শেখায় মনোযোগ দাও — সফলতা আসবেই।",
        "আজ কয়টি lesson শেষ করলে? আরো একটি করো!"
    )

    private val EVENING_MESSAGES = listOf(
        "সন্ধ্যায় পড়ার অভ্যাস করো — ঘুমানোর আগে একটি lesson।",
        "আজকের streak ধরে রেখেছো তো? এখনই সময়!",
        "রাতের শেষ lesson শেষ করো — কাল আরো এগিয়ে যাবে।",
        "ঘুমানোর আগে একটু পড়লে মনে থাকে বেশি।",
        "আজকের লক্ষ্য পূরণ না হলে এখনই সুযোগ!"
    )

    private val STREAK_WARNING_MESSAGES = listOf(
        "সাবধান! আজ না পড়লে তোমার streak ভেঙে যাবে!",
        "তোমার streak বিপদে আছে! এখনই একটি lesson করো।",
        "Streak হারানোর আগে পড়তে বসো — মাত্র ৫ মিনিট!",
        "আজকের দিন শেষ হওয়ার আগে streak বাঁচাও!",
        "Streak রক্ষা করো — এতদিনের পরিশ্রম নষ্ট করো না!"
    )

    private val REMINDER_MESSAGES = listOf(
        "তুমি HTML শিখছো — থামলেই পিছিয়ে পড়বে!",
        "আজ কতটুকু শিখেছো? লক্ষ্যের কাছাকাছি যাও!",
        "প্রতিদিনের অভ্যাসই তোমাকে developer বানাবে।",
        "HTML শেখার সঠিক সময় এখন। দেরি না করে শুরু করো!",
        "তোমার লক্ষ্য মনে আছে? HTML শিখে সফল হওয়া!"
    )

    fun scheduleInitialReminders(context: Context) {
        scheduleMorningReminder(context, 9, 0)
        scheduleEveningReminder(context, 20, 0)
        scheduleStreakWarning(context)
        scheduleGoalCheckWorker(context)
    }

    fun scheduleMorningReminder(context: Context, hour: Int, minute: Int) {
        val workRequest = PeriodicWorkRequestBuilder<MorningReminderWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(calculateInitialDelay(hour, minute), TimeUnit.MILLISECONDS)
            .addTag("morning_reminder")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "morning_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    fun scheduleEveningReminder(context: Context, hour: Int, minute: Int) {
        val workRequest = PeriodicWorkRequestBuilder<EveningReminderWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(calculateInitialDelay(hour, minute), TimeUnit.MILLISECONDS)
            .addTag("evening_reminder")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "evening_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    fun scheduleStreakWarning(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<StreakWarningWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(calculateInitialDelay(22, 0), TimeUnit.MILLISECONDS)
            .addTag("streak_warning")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "streak_warning",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun scheduleGoalCheckWorker(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<GoalCheckWorker>(
            3, TimeUnit.HOURS
        )
            .addTag("goal_check")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "goal_check",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun calculateInitialDelay(hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (target.before(now)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }
        return target.timeInMillis - now.timeInMillis
    }

    fun showStudyReminder(context: Context, type: Int = 0) {
        val messages = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 5..11 -> MORNING_MESSAGES
            in 12..16 -> AFTERNOON_MESSAGES
            else -> EVENING_MESSAGES
        }
        val message = messages[type % messages.size]
        showNotification(context, CHANNEL_STUDY_REMINDER, 1001, "পড়ার সময়!", message)
    }

    fun showStreakWarning(context: Context, streak: Int) {
        if (streak > 0) {
            val message = STREAK_WARNING_MESSAGES.random()
            showNotification(context, CHANNEL_STREAK, 1002,
                "Streak বিপদে! ($streak দিন)", message, priority = NotificationCompat.PRIORITY_MAX)
        }
    }

    fun showGoalNotComplete(context: Context, doneMinutes: Int, goalMinutes: Int) {
        val remaining = goalMinutes - doneMinutes
        val message = "তুমি আজ ${doneMinutes} মিনিট পড়েছো। আরো $remaining মিনিট বাকি!"
        showNotification(context, CHANNEL_STUDY_REMINDER, 1003, "লক্ষ্য পূরণ হয়নি!", message)
    }

    fun showReminderMessage(context: Context, index: Int) {
        val message = REMINDER_MESSAGES[index % REMINDER_MESSAGES.size]
        showNotification(context, CHANNEL_STUDY_REMINDER, 1004, "HTML শেখার সময়!", message)
    }

    fun showAchievement(context: Context, title: String, description: String) {
        showNotification(context, CHANNEL_ACHIEVEMENT, 2000, "নতুন অর্জন! $title", description)
    }

    fun showLevelUp(context: Context, level: Int, levelName: String) {
        showNotification(context, CHANNEL_ACHIEVEMENT, 2001,
            "Level Up! Level $level", "অভিনন্দন! তুমি $levelName হয়েছো!")
    }

    private fun showNotification(
        context: Context,
        channelId: String,
        notifId: Int,
        title: String,
        message: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notifId, notification)
    }

    fun cancelAll(context: Context) {
        WorkManager.getInstance(context).cancelAllWork()
    }
}
