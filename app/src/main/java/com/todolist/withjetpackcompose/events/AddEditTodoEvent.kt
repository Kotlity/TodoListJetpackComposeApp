package com.todolist.withjetpackcompose.events

sealed class AddEditTodoEvent {
    data class OnTodoNameChange(val todoName: String): AddEditTodoEvent()
    data class OnTodoDescriptionChange(val todoDescription: String): AddEditTodoEvent()
    object OnSaveTodoClick: AddEditTodoEvent()
}