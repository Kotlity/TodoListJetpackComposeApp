package com.todolist.withjetpackcompose.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val name: String,
    val description: String?,
    @PrimaryKey
    val id: Int? = null
)
