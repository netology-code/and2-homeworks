package ru.netology.moodtracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(TimeTypeConverters::class, MoodTypeConverters::class)
@Database(
    entities = [MoodEntity::class],
    version = 1,
)
abstract class MoodDb : RoomDatabase() {
    abstract val moodDao: MoodDao

    companion object {
        @Volatile
        private var INSTANCE: MoodDb? = null

        fun getInstance(context: Context): MoodDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context, MoodDb::class.java, "app_db")
                    .build()
                    .also { INSTANCE = it }
            }
    }
}