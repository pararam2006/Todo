package com.pararam2006.todo.domain.repository

import com.pararam2006.todo.domain.model.TodoDto
import java.util.UUID

interface TodoRepository {
    fun getTodos(): List<TodoDto>
    fun addTodo(text: String, id: String = UUID.randomUUID().toString())
    fun deleteTodo(id: String)
    fun editTodo(id: String, newText: String)
    fun changeTodoStatus(id: String, newState: Boolean)
    fun saveTodos()
}