package ru.netology.moodtracker.list

import ru.netology.moodtracker.Mood

typealias MoodCount = Int

data class MoodListItem(
    val dateFormatted: String,
    val moods: Map<Mood, MoodCount> = emptyMap(),
)
