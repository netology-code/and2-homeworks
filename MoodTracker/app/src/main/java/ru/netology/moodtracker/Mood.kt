package ru.netology.moodtracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.netology.moodtracker.ui.theme.LocalMoodColors

enum class Mood {
    GOOD,
    NEUTRAL,
    BAD,
    ;

    val title: String
        get() = when (this) {
            GOOD -> "Хорошее"
            NEUTRAL -> "Нейтральное"
            BAD -> "Плохое"
        }


    val color: Color
        @Composable
        get() = when (this) {
            GOOD -> LocalMoodColors.current.good
            NEUTRAL -> LocalMoodColors.current.neutral
            BAD -> LocalMoodColors.current.bad
        }
}
