package com.todolist.withjetpackcompose.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.todolist.withjetpackcompose.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @SuppressLint("ComposableDestinationInComposeScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.todoListRoute) {
                composable(Routes.todoListRoute) {
                    TodoListScreen(onNavigate = {
                        navController.navigate(it.route)
                    })
                }
                composable(Routes.addEditTodoRoute + "?id={id}", arguments = listOf(navArgument(name = "id") {
                            type = NavType.IntType
                            defaultValue = -1
                            })
                        ) {
                            AddEditTodoScreen(onPopBackStack = {
                                navController.popBackStack()
                            })
                }
            }
        }
    }
}