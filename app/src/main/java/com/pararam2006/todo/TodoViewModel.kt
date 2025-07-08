package com.pararam2006.todo

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TodoViewModel: ViewModel() {
    var todoList = mutableStateListOf<Todo>()
        private set

    var input by mutableStateOf("")
        private set

    fun addTodo() {
        if (input.trim() != "") {
            val newTodo = Todo(text = input.trim())
            todoList.add(newTodo)
            input = ""
        }
    }

    fun changeTodoStatus(id: String, newState: Boolean) {
        val index = todoList.indexOfFirst { it.id == id }
        if (index != -1) {
            todoList[index] = todoList[index].copy(isCompleted = newState)
        }
    }

    fun deleteTodo(id: String) {
        todoList.removeIf { it.id == id }
    }

    fun saveTodos(context: Activity) {
        val prefs = context.getPreferences(Context.MODE_PRIVATE)
        val list = todoList.toList()
        val jsonTodoList = Json.encodeToString(list)
        println("Сохраняем Json: $jsonTodoList")
        prefs.edit().putString("todoList", jsonTodoList).apply()
    }

    fun changeInput(newText: String) {
        input = newText
    }

    fun loadTodos(context: Activity) {
        val prefs = context.getPreferences(Context.MODE_PRIVATE)
        val prefsJson = prefs.getString("todoList", null)
        println("Загруженный Json: $prefsJson")
        todoList.clear() //Очистка перед загрузкой нового списка

        if (prefsJson == null) {
            //Если ничего нет, добавляем дефолтное значение
            todoList.add(Todo("Создайте задачу, написав что-то снизу и нажав кнопку:)"))
        } else {
            //Если там что-то, есть парсим и подгружаем в todoList
            val savedTodos = Json.decodeFromString<Collection<Todo>>(prefsJson)
            todoList.addAll(savedTodos)
        }
    }
}