package ru.netology.moodtracker.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.moodtracker.Mood
import java.time.Instant

@Entity("mood")
data class MoodEntity(
    @ColumnInfo(name = "mood")
    val mood: Mood,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long = 0,
    @ColumnInfo(name = "date")
    val date: Instant = Instant.now(),
)