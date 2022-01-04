package com.todolist.withjetpackcompose.presentation.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todolist.withjetpackcompose.presentation.viewmodels.TodoListViewModel
import com.todolist.withjetpackcompose.events.TodoListEvent
import com.todolist.withjetpackcompose.presentation.composable.TodoItem
import com.todolist.withjetpackcompose.util.UiEvent
import kotlinx.coroutines.flow.collect


@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    todoListViewModel: TodoListViewModel = hiltViewModel()
) {
    val getAllTodos = todoListViewModel.getAllTodos.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        todoListViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(message = event.message, actionLabel = event.action)

                    if (result == SnackbarResult.ActionPerformed) {
                        todoListViewModel.todoListEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                todoListViewModel.todoListEvent(TodoListEvent.OnAddTodoClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add todo"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(getAllTodos.value) { todo ->
                TodoItem(todo = todo,
                    todoEvent = todoListViewModel::todoListEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            todoListViewModel.todoListEvent(TodoListEvent.OnTodoClick(todo))
                        }
                )
            }      
        }
    }
    if (getAllTodos.value.size > 1) {
        Button(onClick = {
            todoListViewModel.todoListEvent(TodoListEvent.OnDeleteAllTodosClick)
            },
            modifier = Modifier.offset(x = 125.dp, y = 600.dp),
            border = BorderStroke(width = 2.dp, Color.Red),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green, contentColor = Color.Blue)
        ) {
            Text(text = "Delete all todos")
        }
    }
}