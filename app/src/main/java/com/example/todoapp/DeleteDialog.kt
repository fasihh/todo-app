package com.example.todoapp

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    state: TodoState,
    onEvent: (TodoEvent) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(TodoEvent.CloseDeleteDialog)
            onEvent(TodoEvent.SetDeleteTodo(null))
        },
        title = {
            Text("Delete todo item")
        },
        text = {
            Text("Item will be deleted permanently")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onEvent(TodoEvent.CloseDeleteDialog)
                    if (state.todoDelete != null) {
                        onEvent(TodoEvent.DeleteTodo(state.todoDelete))
                    }
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onEvent(TodoEvent.CloseDeleteDialog)
                    onEvent(TodoEvent.SetDeleteTodo(null))
                }
            ) {
                Text("Cancel")
            }
        }
    )
}