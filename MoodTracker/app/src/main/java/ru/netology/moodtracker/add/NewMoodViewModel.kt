package ru.netology.moodtracker.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.netology.moodtracker.Mood
import ru.netology.moodtracker.db.MoodDb
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NewMoodViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = MoodDb.Companion.getInstance(application).moodDao

    private val dateFormat = DateTimeFormatter.ofPattern("HH:mm")

    private val _moodSavedEvent = MutableStateFlow<MoodSavedEvent?>(null)
    val moodSaved = _moodSavedEvent.asStateFlow()

    fun addMood(mood: Mood) {
        viewModelScope.launch {
            val savedMood = dao.add(mood)
            _moodSavedEvent.value = MoodSavedEvent(mood = savedMood.mood, formatDate(savedMood.date))
        }
    }

    fun hideMoodSavedEvent() {
        _moodSavedEvent.value = null
    }

    private fun formatDate(date: Instant) = dateFormat.format(date.atZone(ZoneId.systemDefault()))

    data class MoodSavedEvent(
        val mood: Mood,
        val at: String,
    )
}