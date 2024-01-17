package com.example.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    var todoTitle: String = "",
    var todoDesc: String = "",
    var todoDone: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
)
