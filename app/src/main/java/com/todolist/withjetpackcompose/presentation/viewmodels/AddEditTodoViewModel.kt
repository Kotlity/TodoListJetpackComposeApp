package com.todolist.withjetpackcompose.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todolist.withjetpackcompose.data.entity.Todo
import com.todolist.withjetpackcompose.domain.repository.TodoRepositoryInterface
import com.todolist.withjetpackcompose.events.AddEditTodoEvent
import com.todolist.withjetpackcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(private val todoRepositoryInterface: TodoRepositoryInterface, savedStateHandle: SavedStateHandle): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var todoName by mutableStateOf("")
    private set

    var todoDescription by mutableStateOf("")
    private set

    var editTodo by mutableStateOf<Todo?>(null)

    init {
        val todoId = savedStateHandle.get<Int>("id")!!
        if (todoId != -1) {
            viewModelScope.launch {
                todoRepositoryInterface.getTodoById(todoId)?.let { editTodo ->
                    todoName = editTodo.name
                    todoDescription = editTodo.description ?: ""
                    this@AddEditTodoViewModel.editTodo = editTodo
                }
            }
        }
    }

    fun addEditTodoEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.OnTodoNameChange -> {
                viewModelScope.launch {
                    todoName = event.todoName
                }
            }
            is AddEditTodoEvent.OnTodoDescriptionChange -> {
                viewModelScope.launch {
                    todoDescription = event.todoDescription
                }
            }
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (todoName.isBlank()) {
                        _uiEvent.send(UiEvent.ShowSnackBar(message = "The title can't be empty !"))
                        return@launch
                    }
                    todoRepositoryInterface.insertTodo(Todo(name = todoName, description = todoDescription, id = editTodo?.id))
                    _uiEvent.send(UiEvent.PopBackStack)
                }
            }
        }
    }
}