package com.pararam2006.todo.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pararam2006.todo.data.TodoRepository

class MainScreenViewModel(
    private val todoRepository: TodoRepository
) : ViewModel(), MainScreenActions {
    var uiState by mutableStateOf(
        MainScreenUiState(
            todoList = todoRepository.loadTodos()
        )
    )
        private set

    fun changeInput(newText: String) {
        uiState = uiState.copy(
            input = newText
        )
    }

    fun changeRedactingInput(newText: String) {
        uiState = uiState.copy(
            redactingInput = newText
        )
    }

    fun selectTodo(id: String) {
        val selectedIndex = uiState.todoList.indexOfFirst { it.id == id }
        if (selectedIndex != -1) {
            val text = uiState.todoList[selectedIndex].text
            uiState = uiState.copy(
                selectedIndex = selectedIndex,
                redactingInput = text,
                selectedText = text,
            )
        }
    }

    fun revertEditing() {
        val selectedIndex = uiState.selectedIndex
        if (selectedIndex != -1) {
            uiState = uiState.copy(
                redactingInput = uiState.selectedText
            )
        }
    }

    fun showDialog() {
        uiState = uiState.copy(
            isDialogShowed = true
        )
    }

    fun hideDialog() {
        uiState = uiState.copy(
            isDialogShowed = false
        )
    }

    fun changeTodoStatus(id: String, newState: Boolean) {
        todoRepository.changeTodoStatus(id, newState)
        uiState = uiState.copy(todoList = todoRepository.todoList.toList())
    }

    fun addTodo(text: String) {
        todoRepository.addTodo(text)
        uiState = uiState.copy(
            todoList = todoRepository.todoList.toList(),
            input = ""
        )
    }

    fun deleteTodo(id: String) {
        todoRepository.deleteTodo(id)
        uiState = uiState.copy(todoList = todoRepository.todoList.toList())
    }

    fun saveTodos() {
        todoRepository.saveTodos()
    }

    fun editTodo() {
        if (uiState.selectedIndex != -1) {
            val selectedTodo = uiState.todoList[uiState.selectedIndex]
            val newText = uiState.redactingInput.trim()

            if (newText.isNotEmpty()) {
                todoRepository.editTodo(selectedTodo.id, newText)

                // Обновляем UI после редактирования
                uiState = uiState.copy(
                    todoList = todoRepository.todoList.toList(),
                    selectedText = newText
                )
            }
        }
    }

    override fun onInputChange(newText: String) = changeInput(newText)

    override fun onAddTodo(text: String) = addTodo(text)

    override fun onChangeTodoStatus(id: String, newState: Boolean) = changeTodoStatus(id, newState)

    override fun onChangeRedactingInput(newText: String) = changeRedactingInput(newText)

    override fun onEditTodo() = editTodo()

    override fun onDeleteTodo(id: String) = deleteTodo(id)

    override fun onSaveTodos() = saveTodos()

    override fun onSelectTodo(id: String) = selectTodo(id)

    override fun onRevertEditing() = revertEditing()

    override fun onShowDialog() = showDialog()

    override fun onHideDialog() = hideDialog()
}