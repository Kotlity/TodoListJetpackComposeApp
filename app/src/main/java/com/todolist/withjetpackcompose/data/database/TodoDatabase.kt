package com.todolist.withjetpackcompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.todolist.withjetpackcompose.data.dao.TodoDao
import com.todolist.withjetpackcompose.data.entity.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {

    abstract val todoDao: TodoDao
}