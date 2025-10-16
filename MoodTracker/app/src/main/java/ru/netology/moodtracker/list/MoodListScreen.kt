package ru.netology.moodtracker.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.netology.moodtracker.Mood
import ru.netology.moodtracker.ui.theme.MoodTrackerTheme

@Composable
fun MoodListScreen(modifier: Modifier = Modifier) {
    val viewModel: MoodListViewModel = viewModel()
    val items by viewModel.getItems().collectAsState(emptyList())
    MoodListScreenContent(moods = items, modifier = modifier)
}

@Composable
private fun MoodListScreenContent(moods: List<MoodListItem>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(moods) {
            Text(
                text = it.dateFormatted,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Mood.GOOD.color)) {
                        append("${it.moods[Mood.GOOD]}")
                    }
                    append(" хороших, ")
                    withStyle(SpanStyle(color = Mood.NEUTRAL.color)) {
                        append("${it.moods[Mood.NEUTRAL]}")
                    }
                    append(" нейтральных, ")
                    withStyle(SpanStyle(color = Mood.BAD.color)) {
                        append("${it.moods[Mood.BAD]}")
                    }
                    append(" плохих")
                },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MoodListScreenPreview() {
    MoodTrackerTheme {
        Scaffold {
            MoodListScreenContent(
                modifier = Modifier.padding(it),
                moods = listOf(
                    MoodListItem(
                        "Вторник 14.10.25",
                        moods = mapOf(
                            Mood.GOOD to 10,
                            Mood.NEUTRAL to 3,
                            Mood.BAD to 5,
                        )
                    ),
                    MoodListItem(
                        "Понедельник 13.10.25",
                        moods = mapOf(
                            Mood.GOOD to 0,
                            Mood.NEUTRAL to 50,
                            Mood.BAD to 3,
                        )
                    ),
                )
            )
        }

    }
}