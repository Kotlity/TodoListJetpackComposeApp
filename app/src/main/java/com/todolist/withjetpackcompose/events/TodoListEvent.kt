package com.todolist.withjetpackcompose.events

import com.todolist.withjetpackcompose.data.entity.Todo

sealed class TodoListEvent {
    data class OnDeleteTodoClick(val todo: Todo): TodoListEvent()
    object OnUndoDeleteClick: TodoListEvent()
    data class OnTodoClick(val todo: Todo): TodoListEvent()
    object OnAddTodoClick: TodoListEvent()
    object OnDeleteAllTodosClick: TodoListEvent()
}
