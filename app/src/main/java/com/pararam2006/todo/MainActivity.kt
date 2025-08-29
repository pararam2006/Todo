package com.pararam2006.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pararam2006.todo.ui.main.MainScreen
import com.pararam2006.todo.ui.main.MainScreenViewModel
import com.pararam2006.todo.ui.theme.ToDoTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                val todoViewModel: MainScreenViewModel = koinViewModel()

                MainScreen(
                    input = todoViewModel.input,
                    todoList = todoViewModel.todoList,
                    onInputChange = todoViewModel::changeInput,
                    onAddTodo = todoViewModel::addTodo,
                    onChangeTodoStatus = todoViewModel::changeTodoStatus,
                    onDeleteTodo = todoViewModel::deleteTodo,
                    onEditTodo = todoViewModel::editTodo,
                    onSaveTodo = todoViewModel::saveTodos,
                    redactingInput = todoViewModel.redactingInput,
                    onSelectTodo = todoViewModel::selectTodo,
                    onChangeRedactingInput = todoViewModel::changeRedactingInput,
                    onRevertEditing = todoViewModel::revertEditing,
                    onShowDialog = todoViewModel::showDialog,
                    onHideDialog = todoViewModel::hideDialog,
                    isDialogShowed = todoViewModel.isDialogShowed,
                )
            }
        }
    }
}