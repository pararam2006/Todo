package com.pararam2006.todo.ui.main

import com.pararam2006.todo.domain.model.TodoDto

data class MainScreenUiState(
    val todoList: List<TodoDto> = emptyList(),
    val input: String = "",
    val isDialogShowed: Boolean = false,
    val redactingInput: String = "",
    val selectedText: String = "",
    val selectedIndex: Int = -1,
)
