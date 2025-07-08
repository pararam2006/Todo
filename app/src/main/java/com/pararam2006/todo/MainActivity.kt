package com.pararam2006.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.pararam2006.todo.ui.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    private val _todoViewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        _todoViewModel.loadTodos(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                MainScreen(
                    input = _todoViewModel.input,
                    todoList = _todoViewModel.todoList,
                    onInputChange = _todoViewModel::changeInput,
                    onAddTodo = _todoViewModel::addTodo,
                    onChangeTodoStatus = _todoViewModel::changeTodoStatus,
                    onDeleteTodo = _todoViewModel::deleteTodo
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()

        _todoViewModel.saveTodos(this)
    }
}