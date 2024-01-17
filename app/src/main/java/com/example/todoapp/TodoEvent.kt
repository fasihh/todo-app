package com.example.todoapp

sealed interface TodoEvent {
    object AddTodo: TodoEvent
    data class UpdateTodo(val updateType: UpdateType, val todo: TodoEntity?): TodoEvent
    data class DeleteTodo(val todo: TodoEntity): TodoEvent
    data class SetTodoContent(val title: String, val desc: String): TodoEvent
    object ShowAddDialog: TodoEvent
    object CloseAddDialog: TodoEvent
    object ShowEditDialog: TodoEvent
    object CloseEditDialog: TodoEvent
    object ShowDeleteDialog: TodoEvent
    object CloseDeleteDialog: TodoEvent
    data class SetDeleteTodo(val todo: TodoEntity?): TodoEvent
    data class SetEditTodo(val todo: TodoEntity): TodoEvent
}
