package com.htmllearn.app.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
        val MORNING_NOTIFICATION_HOUR = intPreferencesKey("morning_notif_hour")
        val MORNING_NOTIFICATION_MINUTE = intPreferencesKey("morning_notif_minute")
        val EVENING_NOTIFICATION_HOUR = intPreferencesKey("evening_notif_hour")
        val EVENING_NOTIFICATION_MINUTE = intPreferencesKey("evening_notif_minute")
        val STUDY_GOAL_MINUTES = intPreferencesKey("study_goal_minutes")
        val USER_NAME = stringPreferencesKey("user_name")
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val LAST_NOTIF_INDEX = intPreferencesKey("last_notif_index")
        val NOTIFICATION_PERMISSION_ASKED = booleanPreferencesKey("notif_permission_asked")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    val notificationEnabled: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[NOTIFICATION_ENABLED] ?: true }

    val morningHour: Flow<Int> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[MORNING_NOTIFICATION_HOUR] ?: 9 }

    val morningMinute: Flow<Int> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[MORNING_NOTIFICATION_MINUTE] ?: 0 }

    val eveningHour: Flow<Int> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[EVENING_NOTIFICATION_HOUR] ?: 20 }

    val eveningMinute: Flow<Int> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[EVENING_NOTIFICATION_MINUTE] ?: 0 }

    val studyGoalMinutes: Flow<Int> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[STUDY_GOAL_MINUTES] ?: 30 }

    val userName: Flow<String> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[USER_NAME] ?: "শিক্ষার্থী" }

    val onboardingDone: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[ONBOARDING_DONE] ?: false }

    val soundEnabled: Flow<Boolean> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { it[SOUND_ENABLED] ?: true }

    suspend fun setNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { it[NOTIFICATION_ENABLED] = enabled }
    }

    suspend fun setMorningTime(hour: Int, minute: Int) {
        context.dataStore.edit {
            it[MORNING_NOTIFICATION_HOUR] = hour
            it[MORNING_NOTIFICATION_MINUTE] = minute
        }
    }

    suspend fun setEveningTime(hour: Int, minute: Int) {
        context.dataStore.edit {
            it[EVENING_NOTIFICATION_HOUR] = hour
            it[EVENING_NOTIFICATION_MINUTE] = minute
        }
    }

    suspend fun setStudyGoal(minutes: Int) {
        context.dataStore.edit { it[STUDY_GOAL_MINUTES] = minutes }
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { it[USER_NAME] = name }
    }

    suspend fun setOnboardingDone() {
        context.dataStore.edit { it[ONBOARDING_DONE] = true }
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { it[SOUND_ENABLED] = enabled }
    }

    suspend fun incrementNotifIndex(max: Int): Int {
        var current = 0
        context.dataStore.edit {
            current = ((it[LAST_NOTIF_INDEX] ?: 0) + 1) % max
            it[LAST_NOTIF_INDEX] = current
        }
        return current
    }
}
