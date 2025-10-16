@file:OptIn(ExperimentalTime::class)

package ru.netology.moodtracker.db

import androidx.room.TypeConverter
import ru.netology.moodtracker.Mood
import kotlin.time.ExperimentalTime
import java.time.Instant

class TimeTypeConverters {
    @TypeConverter
    fun fromMillisInstant(millis: Long): Instant = Instant.ofEpochMilli(millis)

    @TypeConverter
    fun instantToMillis(instant: Instant): Long = instant.toEpochMilli()
}

class MoodTypeConverters {
    @TypeConverter
    fun moodToOrdinal(mood: Mood): Int = mood.ordinal

    @TypeConverter
    fun ordinalToMood(ordinal: Int): Mood = Mood.entries[ordinal]
}