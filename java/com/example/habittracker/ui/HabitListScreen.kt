package com.example.habittracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittracker.HabitViewModel
import com.example.habittracker.HabitWithTodayProgress

@Composable
fun HabitListScreen(
    paddingValues: PaddingValues,
    viewModel: HabitViewModel,
    onAddClicked: () -> Unit,
    onOpenStats: () -> Unit
) {
    val habitList = viewModel.habitsWithToday.collectAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Сегодняшние привычки", style = MaterialTheme.typography.titleLarge)
            Row {
                OutlinedButton(onClick = onOpenStats) {
                    Text("Отчёты")
                }
                Spacer(modifier = Modifier.width(8.dp))
                FilledTonalButton(onClick = onAddClicked) {
                    Text("Добавить")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (habitList.value.isEmpty()) {
            Text("Пока нет привычек. Нажми «Добавить», чтобы создать первую.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(habitList.value) { item ->
                    HabitItem(
                        habitWithToday = item,
                        onLog = { viewModel.logHabit(item.habit) }
                    )
                }
            }
        }
    }
}

@Composable
fun HabitItem(
    habitWithToday: HabitWithTodayProgress,
    onLog: () -> Unit
) {
    val habit = habitWithToday.habit
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(habit.name, style = MaterialTheme.typography.titleMedium)
                habit.category?.let {
                    Text(it, style = MaterialTheme.typography.bodySmall)
                }
                Text(
                    "Сегодня: ${habitWithToday.todayCount}" +
                            (habit.targetValue?.let { " / $it" } ?: ""),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = onLog) {
                Text("Отметить")
            }
        }
    }
}
