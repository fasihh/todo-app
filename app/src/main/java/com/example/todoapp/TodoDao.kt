package com.example.todoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("SELECT * FROM TodoEntity ORDER BY todoDone ASC")
    fun getTodos(): Flow<List<TodoEntity>>

    @Update
    suspend fun updateTodo(vararg todo: TodoEntity)
}

