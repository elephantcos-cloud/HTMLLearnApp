package com.htmllearn.app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.htmllearn.app.data.db.AppDatabase
import com.htmllearn.app.data.preferences.UserPreferences
import com.htmllearn.app.data.preferences.dataStore
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.*

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            NotificationHelper.scheduleInitialReminders(context)
        }
    }
}

class MorningReminderWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val prefs = UserPreferences(context)
        val enabled = prefs.notificationEnabled.first()
        if (!enabled) return Result.success()

        val index = prefs.incrementNotifIndex(5)
        NotificationHelper.showStudyReminder(context, index)
        return Result.success()
    }
}

class EveningReminderWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val prefs = UserPreferences(context)
        val enabled = prefs.notificationEnabled.first()
        if (!enabled) return Result.success()

        val index = prefs.incrementNotifIndex(5)
        NotificationHelper.showReminderMessage(context, index)
        return Result.success()
    }
}

class StreakWarningWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val prefs = UserPreferences(context)
        val enabled = prefs.notificationEnabled.first()
        if (!enabled) return Result.success()

        val db = AppDatabase.getDatabase(context)
        val user = db.userDao().getUserOnce() ?: return Result.success()

        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val lastDate = if (user.lastStudyDate > 0)
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(user.lastStudyDate))
        else ""

        if (lastDate != today && user.streak > 0) {
            NotificationHelper.showStreakWarning(context, user.streak)
        }
        return Result.success()
    }
}

class GoalCheckWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val prefs = UserPreferences(context)
        val enabled = prefs.notificationEnabled.first()
        if (!enabled) return Result.success()

        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (hour < 14) return Result.success()

        val db = AppDatabase.getDatabase(context)
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val activity = db.dailyActivityDao().getActivity(today)
        val goalMinutes = prefs.studyGoalMinutes.first()
        val doneMinutes = activity?.studyMinutes ?: 0

        if (doneMinutes < goalMinutes) {
            NotificationHelper.showGoalNotComplete(context, doneMinutes, goalMinutes)
        }
        return Result.success()
    }
}
