package ru.netology.moodtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.netology.moodtracker.add.MoodScreen
import ru.netology.moodtracker.list.MoodListScreen
import ru.netology.moodtracker.ui.theme.MoodTrackerTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.FloatingActionButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var listEnabled by remember { mutableStateOf(false) }
            MoodTrackerTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { listEnabled = !listEnabled }) {
                            Icon(
                                if (listEnabled) {
                                    Icons.Default.Create
                                } else {
                                    Icons.AutoMirrored.Filled.List
                                },
                                if (listEnabled) {
                                    "Создать настроение"
                                } else {
                                    "К списку настроений"
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    if (listEnabled) {
                        MoodListScreen(
                            modifier = Modifier.padding(innerPadding),
                        )
                    } else {
                        MoodScreen(
                            modifier = Modifier.padding(innerPadding),
                            snackbarHostState = snackbarHostState,
                        )
                    }
                }
            }
        }
    }
}
