package com.example.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.todoapp.ui.theme.DarkerGray
import com.example.todoapp.ui.theme.LighterGray
import com.example.todoapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDialog(
    state: TodoState,
    onEvent: (TodoEvent) -> Unit
) {
    Dialog(
        onDismissRequest = {
            if (state.editingTodo) {
                onEvent(TodoEvent.CloseEditDialog)
            } else {
                onEvent(TodoEvent.CloseAddDialog)
            }
        }
    ) {
        val (title, setTitle) = remember {
            mutableStateOf(if(state.editingTodo) (state.todoEdit?.todoTitle ?: "") else "")
        }
        val (desc, setDesc) = remember {
            mutableStateOf(if(state.editingTodo) (state.todoEdit?.todoDesc ?: "") else "")
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(LighterGray)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (state.editingTodo) "Edit Task" else "Add Task",
                modifier = Modifier
                    .fillMaxWidth(),
                color = White,
                fontSize = 12.sp
            )
            OutlinedTextField(
                value = title,
                onValueChange = {
                    setTitle(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Task")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    textColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = desc,
                onValueChange = {
                    setDesc(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Description")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    textColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = {
                    onEvent(TodoEvent.SetTodoContent(title, desc))

                    if (state.editingTodo) {
                        onEvent(TodoEvent.UpdateTodo(UpdateType.TASK_EDIT, state.todoEdit))
                    } else {
                        onEvent(TodoEvent.AddTodo)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.75f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White
                )
            ) {
                Text(
                    text = "Submit",
                    color = DarkerGray
                )
            }
        }
    }
}