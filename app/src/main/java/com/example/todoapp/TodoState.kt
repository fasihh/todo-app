package com.example.todoapp

data class TodoState(
    val todoList: List<TodoEntity> = emptyList(),
    val todoTitle: String = "",
    val todoDesc: String = "",
    val addingTodo: Boolean = false,
    val editingTodo: Boolean = false,
    val deletingTodo: Boolean = false,
    val confirmDelete: Boolean = false,
    val todoDelete: TodoEntity? = null,
    val todoEdit: TodoEntity? = null
)
