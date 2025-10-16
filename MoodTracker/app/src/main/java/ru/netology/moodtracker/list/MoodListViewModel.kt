package ru.netology.moodtracker.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.netology.moodtracker.Mood
import ru.netology.moodtracker.db.MoodDb
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MoodListViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = MoodDb.getInstance(application).moodDao
    private val formatter = DateTimeFormatter.ofPattern("EEEE dd.MM.yy")

    fun getItems(): Flow<List<MoodListItem>> {
        val now = Instant.now()
        val zoneId = ZoneId.systemDefault()
        val today = now.atZone(zoneId).toLocalDate()

        val startOfWeek = today.minus(7, ChronoUnit.DAYS)

        val daysOfWeek = startOfWeek.plus(1, ChronoUnit.DAYS)
            .datesUntil(today.plus(1, ChronoUnit.DAYS))
            .toList()

        return dao.getMoods(
            from = startOfWeek.atStartOfDay().atZone(zoneId).toInstant(),
            to = now,
        ).map { moods ->
            daysOfWeek.associateWith { date ->
                val dayMoods =
                    moods.filter {
                        it.date.atZone(zoneId).dayOfWeek == date.dayOfWeek
                    }
                        .groupBy { it.mood }
                        .mapValues { it.value.count() }

                Mood.entries.associateWith { mood -> dayMoods.getOrDefault(mood, 0) }
            }
                .map { (date, moods) ->
                    MoodListItem(
                        dateFormatted = formatter.format(date),
                        moods = moods,
                    )
                }
                .reversed()
        }
    }
}
