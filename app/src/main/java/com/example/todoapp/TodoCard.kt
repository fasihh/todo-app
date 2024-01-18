package com.example.todoapp

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.zIndex
import com.example.todoapp.ui.theme.Green
import com.example.todoapp.ui.theme.Red
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TodoCard(
    todo: TodoEntity,
    check: () -> Unit,
    close: () -> Unit,
    edit: () -> Unit
) {
    val tweenTime = 200
    val cardColor by animateColorAsState(
        targetValue = if (todo.todoDone) Color(0xFF17181D) else Color(0xFF292C35),
        animationSpec = tween(tweenTime), label = ""
    )
    val textColor by animateColorAsState(
        targetValue = if (todo.todoDone) Color(0xFFCCCCCC) else Color(0xFFFFFFFF),
        animationSpec = tween(tweenTime), label = ""
    )
    val iconBackgroundColor by animateColorAsState(
        targetValue = if (todo.todoDone) Color(0xFF24262C) else Color(0xFF3A3B41),
        animationSpec = tween(tweenTime), label = ""
    )
    val iconColor by animateColorAsState(
        targetValue = if (todo.todoDone) Color(0xFF9E8E69) else Color(0xFFFFD369),
        animationSpec = tween(tweenTime), label = ""
    )

    val (isVisible, setVisible) = remember{
        mutableStateOf(false)
    }
    val (width, setWidth) = remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    val checkAction = SwipeAction(
        icon = {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .width(50.dp)
            )
        },
        background = Green,
        onSwipe = { check() }
    )
    val closeAction = SwipeAction(
        icon = {
            Icon(
                Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .width(50.dp)
            )
        },
        background = Red,
        onSwipe = { close() }
    )

    SwipeableActionsBox(
        swipeThreshold = 120.dp,
        startActions = listOf(checkAction),
        endActions = listOf(closeAction)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    setWidth(with(density) { it.width.toDp() })
                }
                .shadow(10.dp)
                .zIndex(1f)
                .background(cardColor)
                .clickable {
                    check()
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { check() },
                        modifier = Modifier
                            .shadow(2.dp, shape = CircleShape)
                            .clip(CircleShape)
                            .background(iconBackgroundColor)
                            .size(width = 35.dp, height = 35.dp)
                    ) {
                        Crossfade(
                            targetState = todo.todoDone,
                            animationSpec = tween(300), label = ""
                        ) { todoDone ->
                            Icon(
                                if (todoDone) Icons.Default.Refresh else Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(22.dp),
                                tint = iconColor
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 8.dp, end = 10.dp)
                    ) {
                        Text(
                            text = todo.todoTitle,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Text(
                            text = todo.todoDesc,
                            fontWeight = FontWeight.Light,
                            color = textColor
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    IconButton(
                        onClick = {
                            setVisible(true)
                        }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = iconColor
                        )
                    }
                }
            }
        }
        DropdownMenu(
            expanded = isVisible,
            onDismissRequest = { setVisible(false) },
            offset = DpOffset(x = width, y = 0.dp)
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "Edit")
                },
                onClick = {
                    setVisible(false)
                    edit()
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Delete")
                },
                onClick = {
                    setVisible(false)
                    close()
                }
            )
        }
    }
}