package com.pararam2006.todo.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pararam2006.todo.data.TodoRepository

class MainScreenViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todoList = todoRepository.todoList

    var isDialogShowed by mutableStateOf(false)
        private set

    var input by mutableStateOf("")
        private set

    var redactingInput by mutableStateOf("")
        private set

    var selectedText by mutableStateOf("")
        private set

    var selectedIndex by mutableIntStateOf(-1)
        private set

    var selectedId by mutableStateOf("")
        private set

    fun changeInput(newText: String) {
        input = newText
    }

    fun changeRedactingInput(newText: String) {
        redactingInput = newText
    }

    fun selectTodo(id: String) {
        selectedIndex = todoList.indexOfFirst { it.id == id }
        if (selectedIndex != -1) {
            val text = todoList[selectedIndex].text
            if (text != "тут пусто...") {
                redactingInput = text
                selectedText = text
            } else {
                redactingInput = ""
                selectedText = ""
            }

        }
    }

    fun revertEditing() {
        todoList[selectedIndex] = todoList[selectedIndex].copy(text = selectedText)
    }

    fun showDialog() {
        isDialogShowed = true
    }

    fun hideDialog() {
        isDialogShowed = false
    }

    fun changeTodoStatus(id: String, newState: Boolean) =
        todoRepository.changeTodoStatus(id, newState)

    fun addTodo(text: String) {
        todoRepository.addTodo(text)
        input = ""
    }

    fun deleteTodo(id: String) = todoRepository.deleteTodo(id)

    fun saveTodos() = todoRepository.saveTodos()

    fun editTodo() {
        selectedId = todoList[selectedIndex].id
        val newText = if (redactingInput == "") "тут пусто..." else redactingInput
        todoRepository.editTodoWithoutSave(selectedId, newText)
    }
}