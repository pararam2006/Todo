package com.pararam2006.todo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(vm: TodoViewModel) {
    val todoList = vm.todoList
    val input = remember { vm.input }

    ToDoTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { CenterAlignedTopAppBar(title = { Text("Список задач", fontSize = 32.sp) }) },
            bottomBar = {
                BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                    Spacer(Modifier.weight(1F, true))
                    TextField(
                        value = input.value,
                        onValueChange = { newText -> input.value = newText },
                        placeholder = { Text("Напишите задачу") },
                        singleLine = true,
                        modifier = Modifier.width(300.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    FloatingActionButton(
                        onClick = {
                            vm.addTodo()
                        }
                    ) { Icon(imageVector = Icons.Filled.Add, contentDescription = "") }
                    Spacer(Modifier.weight(1F, true))
                }
            },
            content = {

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.9F)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        items(todoList) { item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()
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
                                            vm.changeTodoStatus(item.id, newState)
                                        }
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        vm.deleteTodo(item.id)
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape")
@Composable
fun HorizontalPreview() {
    MainScreen(vm = TodoViewModel())
}

@Preview
@Composable
fun VerticalPreview() {
    MainScreen(vm = TodoViewModel())
}