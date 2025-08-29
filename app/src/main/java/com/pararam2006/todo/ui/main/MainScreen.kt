package com.pararam2006.todo.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.pararam2006.todo.domain.TodoDto
import com.pararam2006.todo.ui.theme.ToDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    input: String,
    redactingInput: String,
    todoList: List<TodoDto>,
    onInputChange: (String) -> Unit,
    onAddTodo: (String) -> Unit,
    onChangeTodoStatus: (String, Boolean) -> Unit,
    onChangeRedactingInput: (String) -> Unit,
    onEditTodo: () -> Unit,
    onDeleteTodo: (String) -> Unit,
    onSaveTodo: () -> Unit,
    onSelectTodo: (String) -> Unit,
    onRevertEditing: () -> Unit,
    onShowDialog: () -> Unit,
    onHideDialog: () -> Unit,
    isDialogShowed: Boolean,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CenterAlignedTopAppBar(title = { Text("Список дел", fontSize = 32.sp) }) },
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.weight(1F, true))

                TextField(
                    value = input,
                    onValueChange = onInputChange,
                    label = { Text("Что хотите сделать?") },
                    singleLine = true,
                    modifier = Modifier
                        .width(300.dp)
                )

                Spacer(Modifier.width(10.dp))

                FloatingActionButton(
                    onClick = {
                        onAddTodo(input)
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                }

                Spacer(Modifier.weight(1F, true))
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                if (todoList.isEmpty()) {
                    Text(
                        text = "Пустовато...\nСамое время исправить!",
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.9F),
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
                                    color = if (item.isCompleted || item.text == "тут пусто...") {
                                        Color.Gray
                                    } else {
                                        LocalContentColor.current
                                    },
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            onSelectTodo(item.id)
                                            onShowDialog()
                                        }
                                )

                                Checkbox(
                                    modifier = Modifier.padding(start = 10.dp),
                                    checked = item.isCompleted,
                                    onCheckedChange = { newState ->
                                        onChangeTodoStatus(
                                            item.id,
                                            newState
                                        )
                                    }
                                )

                                IconButton(
                                    onClick = {
                                        onDeleteTodo(item.id)
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
                if (isDialogShowed) {
                    TodoEditingDialog(
                        text = redactingInput,
                        onInputChange = { newText ->
                            onChangeRedactingInput(newText)
                            onEditTodo()
                        },
                        onDismissPressed = {
                            onRevertEditing()
                            onHideDialog()
                        },
                        onConfirmPressed = {
                            onSaveTodo()
                            onHideDialog()
                        },
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditingDialog(
    text: String,
    onInputChange: (String) -> Unit,
    onDismissPressed: () -> Unit,
    onConfirmPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismissPressed,
        properties = DialogProperties(),
        modifier = modifier.imePadding(),
        content = {
            Card(modifier = Modifier.fillMaxWidth(0.77f)) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Отредактируйте текст задачи", modifier = Modifier.padding(
                            top = 10.dp,
                            bottom = 10.dp
                        )
                    )

                    OutlinedTextField(
                        value = text,
                        onValueChange = { newValue -> onInputChange(newValue) },
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(
                            onClick = onDismissPressed,
                        ) {
                            Text("Отмена")
                        }

                        TextButton(
                            onClick = onConfirmPressed,
                        ) {
                            Text("Принять")
                        }
                    }
                }
            }
        },
    )
}

@Preview
@Composable
fun TodoEditingDialogPreview() {
    ToDoTheme {
        TodoEditingDialog(
            text = "",
            onInputChange = {},
            onDismissPressed = {},
            onConfirmPressed = {},
        )
    }
}

@Preview(
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Preview
@Composable
fun MainScreenPreview() {
    ToDoTheme {
        MainScreen(
            input = "",
            todoList = emptyList(),
            onInputChange = {},
            onAddTodo = {},
            onChangeTodoStatus = { _, _ -> },
            onDeleteTodo = {},
            onEditTodo = {},
            redactingInput = "",
            onChangeRedactingInput = {},
            onSaveTodo = {},
            onSelectTodo = {},
            onRevertEditing = {},
            onShowDialog = {},
            onHideDialog = {},
            isDialogShowed = false,
        )
    }
}