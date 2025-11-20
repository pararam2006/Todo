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
                val uiState = todoViewModel.uiState
                MainScreen(
                    uiState = uiState,
                    actions = todoViewModel,
                )
            }
        }
    }
}