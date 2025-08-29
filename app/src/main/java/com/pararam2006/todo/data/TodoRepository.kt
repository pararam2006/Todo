package com.pararam2006.todo.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.edit
import com.pararam2006.todo.domain.TodoDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TodoRepository(
    context: Context
) {
    val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    var todoList = mutableStateListOf<TodoDto>()
        private set

    init {
        loadTodos()
    }

    fun changeTodoStatus(id: String, newState: Boolean) {
        val index = todoList.indexOfFirst { it.id == id }
        if (index != -1) {
            todoList[index] = todoList[index].copy(isCompleted = newState)
        }
        saveTodos()
    }

    fun addTodo(text: String) {
        if (text.trim() != "") {
            val newTodo = TodoDto(text = text.trim())
            todoList.add(newTodo)
        }
        saveTodos()
    }

    fun deleteTodo(id: String) {
        todoList.removeIf { it.id == id }
        saveTodos()
    }

    fun saveTodos() {
        val list = todoList.toList()
        val jsonTodoList = Json.Default.encodeToString(list)
        prefs?.edit { putString("todoList", jsonTodoList) }
    }

    fun loadTodos() {
        val prefsJson = prefs?.getString("todoList", "")
        todoList.clear()
        if (prefsJson == "") {
            todoList.add(TodoDto("Первая задача - создать задачу ;)"))
        } else {
            val savedTodos = Json.Default.decodeFromString<Collection<TodoDto>>(prefsJson ?: "")
            todoList.addAll(savedTodos)
        }
    }

    fun editTodo(id: String, newText: String) {
        val index = todoList.indexOfFirst { it.id == id }
        if (index != -1) {
            todoList[index] = todoList[index].copy(text = newText)
        }
        saveTodos()
    }

    fun editTodoWithoutSave(id: String, newText: String) {
        val index = todoList.indexOfFirst { it.id == id }
        if (index != -1) {
            todoList[index] = todoList[index].copy(text = newText)
        }
    }
}