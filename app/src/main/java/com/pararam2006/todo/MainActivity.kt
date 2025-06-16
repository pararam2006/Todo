package com.pararam2006.todo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pararam2006.todo.ui.theme.ToDoTheme
import kotlinx.serialization.Serializable
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Serializable
data class Todo(val text: String, val isCompleted: Boolean, val id: UUID = UUID.randomUUID())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val todoList = remember { mutableStateListOf<Todo>() }
    val input = remember { mutableStateOf("") }
    ToDoTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { CenterAlignedTopAppBar(title = { Text("Список задач", fontSize = 32.sp) }) },
            bottomBar = {
                BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                    Spacer(Modifier.weight(1F, true))
                    TextField(
                        value = input.value,
                        onValueChange = {newText -> input.value = newText},
                        placeholder = { Text("Напишите задачу") },
                        singleLine = true,
                        modifier = Modifier.width(300.dp)
                    )
                    Spacer(Modifier.weight(1F, true))
                    FloatingActionButton(
                        onClick = {
                            val newTodo = Todo(text = input.value, isCompleted = false)
                            todoList.add(newTodo)
                            input.value = ""
//                            Toast.makeText(context, newTodo.text, Toast.LENGTH_SHORT).show()
                        }
                    ) { Icon(imageVector = Icons.Filled.Add, contentDescription = "") }
                    Spacer(Modifier.weight(1F, true))
                }
            },
            
        ) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth(0.9F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    items(todoList) { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = item.text,
                                softWrap = true,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Clip,
                                textDecoration = if (item.isCompleted) {
                                    TextDecoration.LineThrough
                                } else {
                                    TextDecoration.None
                                },
                                color = if (item.isCompleted) {
                                    Color.Gray
                                } else {
                                    LocalContentColor.current
                                },
                                modifier = Modifier.weight(1F)

                            )
                            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                                Checkbox(
                                    modifier = Modifier.padding(start = 10.dp),
                                    checked = item.isCompleted,
                                    onCheckedChange = { newState ->
                                        val index = todoList.indexOfFirst { it.id == item.id }
                                        if (index != -1) {
                                            todoList[index] = item.copy(isCompleted = newState)
                                        }
                                    }
                                )
                            }
                            IconButton(
                                onClick = {
                                    todoList.removeIf { it.id == item.id }
                                },
                            ) { Icon(imageVector = Icons.Filled.Delete, contentDescription = "") }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainScreen()
}