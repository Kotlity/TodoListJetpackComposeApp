package com.todolist.withjetpackcompose.util

sealed class UiEvent {
    data class ShowSnackBar(val message: String, val action: String? = null): UiEvent()
    data class Navigate(val route: String): UiEvent()
    object PopBackStack: UiEvent()
}
