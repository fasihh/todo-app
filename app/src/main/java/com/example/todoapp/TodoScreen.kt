package com.example.todoapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.ui.theme.DarkerGrayTint
import com.example.todoapp.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TodoScreen(
    state: TodoState,
    onEvent: (TodoEvent) -> Unit
) {
    if (state.addingTodo || state.editingTodo) {
        TodoDialog(
            state = state,
            onEvent = onEvent
        )
    }

    if (state.deletingTodo) {
        DeleteDialog(
            state = state,
            onEvent= onEvent
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(TodoEvent.ShowAddDialog) },
                containerColor = DarkerGrayTint,
                contentColor = Yellow
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1E1E))
                .padding(innerPadding)
                .padding(top = 15.dp)
        ) {
            AnimatedVisibility(
                visible = state.todoList.isEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Text(
                    text = "Nothing here yet!",
                    color = Color(0xFFCCCCCC),
                    fontSize = 22.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(400)
                )
            }

            AnimatedVisibility(
                visible = state.todoList.isNotEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.todoList) {todo ->
                        TodoCard(
                            todo = todo,
                            check = { onEvent(TodoEvent.UpdateTodo(UpdateType.TASK_DONE, todo)) },
                            close = {
                                onEvent(TodoEvent.ShowDeleteDialog)
                                onEvent(TodoEvent.SetDeleteTodo(todo))
                            },
                            edit = {
                                onEvent(TodoEvent.ShowEditDialog)
                                onEvent(TodoEvent.SetEditTodo(todo))
                            }
                        )
                    }
                }
            }
        }
    }
}
