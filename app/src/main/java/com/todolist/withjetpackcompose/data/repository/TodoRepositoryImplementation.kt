package com.todolist.withjetpackcompose.data.repository

import com.todolist.withjetpackcompose.data.dao.TodoDao
import com.todolist.withjetpackcompose.data.entity.Todo
import com.todolist.withjetpackcompose.domain.repository.TodoRepositoryInterface
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImplementation(private val todoDao: TodoDao): TodoRepositoryInterface {

    override suspend fun insertTodo(todo: Todo) {
        return todoDao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        return todoDao.deleteTodo(todo)
    }

    override suspend fun deleteAllTodos() {
        return todoDao.deleteAllTodos()
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return todoDao.getTodoById(id)
    }

    override fun getAllTodos(): Flow<List<Todo>> {
        return todoDao.getAllTodos()
    }
}