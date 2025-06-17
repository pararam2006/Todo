package com.pararam2006.todo

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TodoViewModel: ViewModel() {
    private val _todoList = mutableStateListOf<Todo>()
    val todoList: MutableList<Todo> get() = _todoList

    private val _input = mutableStateOf("")
    val input: MutableState<String> get() = _input

    fun addTodo() {
        if (_input.value.trim() != "") {
            val newTodo = Todo(text = _input.value.trim())
            _todoList.add(newTodo)
            _input.value = ""
        }
    }

    fun changeTodoStatus(id: String, newState: Boolean) {
        val index = _todoList.indexOfFirst { it.id == id }
        if (index != -1) {
            _todoList[index] = _todoList[index].copy(isCompleted = newState)
        }
    }

    fun deleteTodo(id: String) {
        _todoList.removeIf { it.id == id }
    }

    fun saveTodos(context: Activity) {
        val prefs = context.getPreferences(Context.MODE_PRIVATE)
        val list = _todoList.toList()
        val jsonTodoList = Json.encodeToString(list)
        println("Сохраняем Json: $jsonTodoList")
        prefs.edit().putString("todoList", jsonTodoList).apply()
    }

    fun loadTodos(context: Activity) {
        val prefs = context.getPreferences(Context.MODE_PRIVATE)
        val prefsJson = prefs.getString("todoList", null)
        println("Загруженный Json: $prefsJson")
        _todoList.clear() //Очистка перед загрузкой нового списка

        if (prefsJson == null) {
            //Если ничего нет, добавляем дефолтное значение
            _todoList.add(Todo("Создайте задачу, написав что-то снизу и нажав кнопку:)"))
        } else {
            //Если там что-то, есть парсим и подгружаем в _todoList
            val savedTodos = Json.decodeFromString<Collection<Todo>>(prefsJson)
            _todoList.addAll(savedTodos)
        }
    }
}