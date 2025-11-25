package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.habittracker.ui.HabitEditScreen
import com.example.habittracker.ui.HabitListScreen
import com.example.habittracker.ui.StatsScreen
import com.example.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {

    private val viewModel: HabitViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabitTrackerTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Трекер привычек") }
                        )
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "habit_list"
                    ) {
                        composable("habit_list") {
                            HabitListScreen(
                                paddingValues = paddingValues,
                                viewModel = viewModel,
                                onAddClicked = { navController.navigate("habit_edit") },
                                onOpenStats = { navController.navigate("stats") }
                            )
                        }
                        composable(
                            route = "habit_edit?habitId={habitId}",
                            arguments = listOf(
                                navArgument("habitId") {
                                    type = NavType.LongType
                                    defaultValue = -1L
                                }
                            )
                        ) {
                            HabitEditScreen(
                                viewModel = viewModel,
                                onDone = { navController.popBackStack() }
                            )
                        }
                        composable("habit_edit") {
                            HabitEditScreen(
                                viewModel = viewModel,
                                onDone = { navController.popBackStack() }
                            )
                        }
                        composable("stats") {
                            StatsScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
