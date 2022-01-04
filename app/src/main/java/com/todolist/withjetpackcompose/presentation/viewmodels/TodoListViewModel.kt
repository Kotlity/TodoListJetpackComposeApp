package com.todolist.withjetpackcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todolist.withjetpackcompose.data.entity.Todo
import com.todolist.withjetpackcompose.domain.repository.TodoRepositoryInterface
import com.todolist.withjetpackcompose.events.TodoListEvent
import com.todolist.withjetpackcompose.util.Routes
import com.todolist.withjetpackcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val todoRepositoryInterface: TodoRepositoryInterface): ViewModel() {

    val getAllTodos = todoRepositoryInterface.getAllTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var recentlyDeletedTodo: Todo? = null

    fun todoListEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnDeleteTodoClick -> {
                recentlyDeletedTodo = event.todo
                viewModelScope.launch {
                    todoRepositoryInterface.deleteTodo(event.todo)
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "todo ${event.todo.name} deleted", action = "undo"))
                }
            }
            is TodoListEvent.OnUndoDeleteClick -> {
                recentlyDeletedTodo?.let { todo ->
                    viewModelScope.launch {
                        todoRepositoryInterface.insertTodo(todo)
                    }
                }
            }
            is TodoListEvent.OnTodoClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Routes.addEditTodoRoute + "?id=${event.todo.id}"))
                }
            }
            is TodoListEvent.OnAddTodoClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Routes.addEditTodoRoute))
                }
            }
            is TodoListEvent.OnDeleteAllTodosClick -> {
                    viewModelScope.launch {
                        todoRepositoryInterface.deleteAllTodos()
                 }
            }
        }
    }
}