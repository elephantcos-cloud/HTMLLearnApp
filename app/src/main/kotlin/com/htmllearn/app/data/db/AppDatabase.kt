package com.htmllearn.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.htmllearn.app.data.db.dao.*
import com.htmllearn.app.data.db.entity.*

@Database(
    entities = [
        UserEntity::class,
        LessonProgressEntity::class,
        DailyActivityEntity::class,
        BadgeEntity::class,
        SessionLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun lessonProgressDao(): LessonProgressDao
    abstract fun dailyActivityDao(): DailyActivityDao
    abstract fun badgeDao(): BadgeDao
    abstract fun sessionLogDao(): SessionLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "html_learn_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
