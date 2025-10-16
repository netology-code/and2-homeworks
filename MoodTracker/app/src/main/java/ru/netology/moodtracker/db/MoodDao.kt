package ru.netology.moodtracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.moodtracker.Mood
import java.time.Instant

@Dao
interface MoodDao {
    @Query("SELECT * FROM mood WHERE date BETWEEN :from AND :to")
    fun getMoods(from: Instant, to: Instant): Flow<List<MoodEntity>>

    @Query("SELECT * FROM mood WHERE id = :id")
    suspend fun getMoodById(id: Long): MoodEntity

    @Insert
    suspend fun save(moodEntity: MoodEntity): Long

    suspend fun add(mood: Mood): MoodEntity {
        val id = save(MoodEntity(mood))
        return getMoodById(id)
    }
}