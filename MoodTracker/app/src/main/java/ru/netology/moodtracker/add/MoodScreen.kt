package ru.netology.moodtracker.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import ru.netology.moodtracker.Mood
import ru.netology.moodtracker.ui.theme.MoodTrackerTheme

@Composable
fun MoodScreen(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    val viewModel: NewMoodViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.moodSaved.filterNotNull().collectLatest {
            snackbarHostState.showSnackbar("${it.mood.title} настроение в ${it.at}")
            viewModel.hideMoodSavedEvent()
        }
    }

    MoodScreenContent(onMoodClicked = viewModel::addMood, modifier = modifier)
}

@Composable
fun MoodScreenContent(onMoodClicked: (Mood) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Выберите настроение", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            @Composable
            fun MoodButton(mood: Mood) {
                TextButton(onClick = { onMoodClicked(mood) }) {
                    Text(mood.title, color = mood.color)
                }
            }

            Mood.entries.forEach { MoodButton(it) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoodScreenPreview() {
    MoodTrackerTheme {
        MoodScreenContent(onMoodClicked = {})
    }
}