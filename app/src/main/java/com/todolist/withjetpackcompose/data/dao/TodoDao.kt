package com.todolist.withjetpackcompose.data.dao

import androidx.room.*
import com.todolist.withjetpackcompose.data.entity.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo")
    suspend fun deleteAllTodos()

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?

    @Query("SELECT * FROM todo")
    fun getAllTodos(): Flow<List<Todo>>
}