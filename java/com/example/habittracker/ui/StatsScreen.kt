package com.example.habittracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittracker.HabitViewModel
import com.example.habittracker.data.HabitLog

@Composable
fun StatsScreen(
    viewModel: HabitViewModel,
    onBack: () -> Unit
) {
    val logs = viewModel.allLogs.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Отчёты", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onBack) {
                Text("Назад")
            }
        }

        Text(
            "Всего записей: ${logs.value.size}",
            style = MaterialTheme.typography.bodyMedium
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(logs.value) { log ->
                LogItem(log)
            }
        }
    }
}

@Composable
fun LogItem(log: HabitLog) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("habitId: ${log.habitId}")
            Text("value: ${log.value}")
            Text("time: ${log.timestamp}")
        }
    }
}
