package com.todolist.withjetpackcompose.module

import android.app.Application
import androidx.room.Room
import com.todolist.withjetpackcompose.data.database.TodoDatabase
import com.todolist.withjetpackcompose.data.repository.TodoRepositoryImplementation
import com.todolist.withjetpackcompose.domain.repository.TodoRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(app, TodoDatabase::class.java, "todoDatabase.db").build()
    }

    @Provides
    @Singleton
    fun provideTodoRepositoryImplementation(todoDatabase: TodoDatabase): TodoRepositoryInterface {
        return TodoRepositoryImplementation(todoDatabase.todoDao)
    }
}