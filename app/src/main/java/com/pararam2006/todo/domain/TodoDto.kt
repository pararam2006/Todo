package com.pararam2006.todo.domain

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TodoDto(
    val text: String,
    val isCompleted: Boolean = false,
    val id: String = UUID.randomUUID().toString()
)