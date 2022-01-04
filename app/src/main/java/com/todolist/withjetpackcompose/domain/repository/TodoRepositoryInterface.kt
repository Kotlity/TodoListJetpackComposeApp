package com.todolist.withjetpackcompose.domain.repository

import com.todolist.withjetpackcompose.data.entity.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepositoryInterface {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun deleteAllTodos()

    suspend fun getTodoById(id: Int): Todo?

    fun getAllTodos(): Flow<List<Todo>>
}