package com.example.todoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoViewModel(
    private val dao: TodoDao
): ViewModel() {
    private val todoList = dao.getTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(TodoState())
    val state = combine(_state, todoList) { state, todoList ->
        state.copy(
            todoList = todoList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TodoState())

    fun onEvent(event: TodoEvent) {
        when(event) {
            TodoEvent.AddTodo -> createTodo()
            is TodoEvent.UpdateTodo -> {
                if (event.todo == null) {
                    toggleEditDialog(false)
                    return
                }
                when(event.updateType) {
                    UpdateType.TASK_DONE -> toggleTodo(event.todo)
                    UpdateType.TASK_EDIT -> editTodo(event.todo)
                }
            }
            is TodoEvent.SetEditTodo -> setEditTodo(event.todo)
            is TodoEvent.SetTodoContent -> setContent(event.title, event.desc)
            is TodoEvent.SetDeleteTodo -> setDeleteTodo(event.todo)
            is TodoEvent.DeleteTodo -> deleteTodo(event.todo)
            TodoEvent.CloseAddDialog ->  toggleAddDialog(false)
            TodoEvent.ShowAddDialog -> toggleAddDialog(true)
            TodoEvent.ShowEditDialog -> toggleEditDialog(true)
            TodoEvent.CloseEditDialog -> toggleEditDialog(false)
            TodoEvent.ShowDeleteDialog -> toggleDeleteDialog(true)
            TodoEvent.CloseDeleteDialog -> toggleDeleteDialog(false)
        }
    }

    private fun setDeleteTodo(todo: TodoEntity?) {
        _state.update { it.copy(
            todoDelete = todo
        ) }
    }

    private fun toggleDeleteDialog(flag: Boolean) {
        _state.update { it.copy(
            deletingTodo = flag
        ) }
    }

    private fun setContent(title: String, desc: String) {
        val title = if(title.length > MAX_TITLE_LENGTH) title.substring(0, MAX_TITLE_LENGTH) else title
        val desc = if(desc.length > MAX_DESC_LENGTH) desc.substring(0, MAX_DESC_LENGTH) else desc

        _state.update { it.copy (
            todoTitle = title,
            todoDesc = desc
        ) }
    }

    private fun setEditTodo(todo: TodoEntity?) {
        _state.update { it.copy(
            todoEdit = todo
        ) }
    }

    private fun editTodo(todo: TodoEntity) {
        val title = state.value.todoTitle
        val desc = state.value.todoDesc

        if (title.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            dao.updateTodo(
                todo.copy(
                    todoTitle = title,
                    todoDesc = desc
                )
            )
        }

        _state.update { it.copy(
            todoTitle = "",
            todoDesc = "",
            editingTodo = false,
            todoEdit = null
        ) }
    }

    private fun createTodo() {
        val title = state.value.todoTitle
        val desc = state.value.todoDesc

        if (title.isBlank()) return

        val todo = TodoEntity(
            todoTitle = title,
            todoDesc = desc
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insertTodo(todo)
        }

        _state.update { it.copy(
            todoTitle = "",
            todoDesc = "",
            addingTodo = false
        ) }
    }

    private fun toggleTodo(todo: TodoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateTodo(todo.copy(todoDone = !todo.todoDone))
        }
    }

    private fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteTodo(todo)
        }
    }

    private fun toggleAddDialog(flag: Boolean) {
        _state.update { it.copy(
            addingTodo = flag
        ) }
    }

    private fun toggleEditDialog(flag: Boolean) {
        _state.update { it.copy(
          editingTodo = flag
        ) }
    }

    companion object {
        const val MAX_TITLE_LENGTH = 10
        const val MAX_DESC_LENGTH = 25
    }
}