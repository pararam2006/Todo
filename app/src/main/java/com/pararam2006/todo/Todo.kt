package com.pararam2006.todo

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Todo(
    val text: String,
    val isCompleted: Boolean = false,
    val id: String = UUID.randomUUID().toString()
)
