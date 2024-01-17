package com.example.todoapp

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.example.todoapp.ui.theme.Green
import com.example.todoapp.ui.theme.Red
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.TodoCard(
    todo: TodoEntity,
    check: () -> Unit,
    close: () -> Unit,
    edit: () -> Unit)
{
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
                .shadow(10.dp)
                .zIndex(1f)
                .background(cardColor)
                .animateItemPlacement(
                    animationSpec = tween(1000)
                )
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
                            if (todoDone) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(22.dp),
                                    tint = iconColor
                                )
                            } else {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(22.dp),
                                    tint = iconColor
                                )
                            }
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
                        onClick = { edit() },
                        modifier = Modifier
                            .shadow(2.dp, shape = CircleShape)
                            .clip(CircleShape)
                            .background(iconBackgroundColor)
                            .size(width = 35.dp, height = 35.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier
                                .width(22.dp),
                            tint = iconColor
                        )
                    }
                    IconButton(
                        onClick = { close() },
                        modifier = Modifier
                            .shadow(2.dp, shape = CircleShape)
                            .clip(CircleShape)
                            .background(iconBackgroundColor)
                            .size(width = 35.dp, height = 35.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .width(22.dp),
                            tint = iconColor
                        )
                    }
                }
            }
        }
    }
}