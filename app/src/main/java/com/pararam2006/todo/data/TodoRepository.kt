package com.pararam2006.todo.data

import android.content.Context
import androidx.core.content.edit
import com.pararam2006.todo.domain.TodoDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TodoRepository(context: Context) {

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    private val _todoList: MutableList<TodoDto> = mutableListOf()
    val todoList: List<TodoDto>
        get() = _todoList.toList()

    init {
        _todoList.addAll(loadTodosInternal())
    }

    private fun loadTodosInternal(): List<TodoDto> {
        val json = prefs.getString("todoList", null)
        return if (json.isNullOrEmpty()) {
            listOf(TodoDto("Первая задача - создать задачу ;)"))
        } else {
            Json.decodeFromString(json)
        }
    }

    fun loadTodos(): List<TodoDto> = _todoList.toList()

    fun addTodo(text: String) {
        val trimmed = text.trim()
        if (trimmed.isNotEmpty()) {
            _todoList.add(TodoDto(text = trimmed))
            saveTodos()
        }
    }

    fun deleteTodo(id: String) {
        _todoList.removeIf { it.id == id }
        saveTodos()
    }

    fun changeTodoStatus(id: String, newState: Boolean) {
        val index = _todoList.indexOfFirst { it.id == id }
        if (index != -1) {
            _todoList[index] = _todoList[index].copy(isCompleted = newState)
            saveTodos()
        }
    }

    fun editTodo(id: String, newText: String) {
        val index = _todoList.indexOfFirst { it.id == id }
        if (index != -1) {
            _todoList[index] = _todoList[index].copy(text = newText)
            saveTodos()
        }
    }

    fun saveTodos() {
        val json = Json.encodeToString(_todoList.toList())
        prefs.edit { putString("todoList", json) }
    }
}