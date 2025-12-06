package com.pararam2006.todo.data

import com.pararam2006.todo.domain.model.TodoDto
import com.pararam2006.todo.domain.repository.TodoRepository

class FakeTodoRepository : TodoRepository {

    private val _todoList = mutableListOf<TodoDto>()

    override fun getTodos(): List<TodoDto> = _todoList.toList()

    override fun addTodo(text: String, id: String) {
        val trimmed = text.trim()
        if (trimmed.isNotEmpty()) {
            _todoList.add(
                TodoDto(
                    text = trimmed,
                    id = id
                )
            )
        }
    }

    override fun deleteTodo(id: String) {
        _todoList.removeIf { it.id == id }
    }

    override fun changeTodoStatus(id: String, newState: Boolean) {
        val index = _todoList.indexOfFirst { it.id == id }
        if (index != -1) {
            _todoList[index] = _todoList[index].copy(isCompleted = newState)
        }
    }

    override fun editTodo(id: String, newText: String) {
        val index = _todoList.indexOfFirst { it.id == id }
        if (index != -1) {
            _todoList[index] = _todoList[index].copy(text = newText)
        }
    }

    override fun saveTodos() {
        // ничего не делаем — нам не нужны SharedPreferences в unit-тестах
    }
}