package com.pararam2006.todo.ui.main

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pararam2006.todo.R
import com.pararam2006.todo.ui.main.widget.TodoEditingDialog
import com.pararam2006.todo.ui.theme.ToDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: MainScreenUiState,
    actions: MainScreenActions,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = stringResource(R.string.main_screen_title),
                    fontSize = 32.sp
                )
            })
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.weight(1F, true))

                TextField(
                    value = uiState.input,
                    onValueChange = actions::onInputChange,
                    label = { Text(text = stringResource(R.string.main_screen_input_label)) },
                    singleLine = true,
                    modifier = Modifier
                        .width(300.dp)
                )

                Spacer(Modifier.width(dimensionResource(R.dimen.padding8)))

                FloatingActionButton(
                    onClick = {
                        actions.onAddTodo(uiState.input)
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
                if (uiState.todoList.isEmpty()) {
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
                        items(uiState.todoList) { item ->
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
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            with(actions) {
                                                onSelectTodo(item.id)
                                                onShowDialog()
                                            }
                                        }
                                )

                                Checkbox(
                                    modifier = Modifier.padding(start = 10.dp),
                                    checked = item.isCompleted,
                                    onCheckedChange = { newState ->
                                        actions.onChangeTodoStatus(item.id, newState)
                                    }
                                )

                                IconButton(
                                    onClick = {
                                        actions.onDeleteTodo(item.id)
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
                if (uiState.isDialogShowed) {
                    TodoEditingDialog(
                        text = uiState.redactingInput,
                        onInputChange = actions::onChangeRedactingInput,
                        onDismissPressed = {
                            with(actions) {
                                onRevertEditing()
                                onHideDialog()
                            }
                        },
                        onConfirmPressed = {
                            with(actions) {
                                onEditTodo()
                                onHideDialog()
                            }
                        },
                    )
                }
            }
        }
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
            uiState = MainScreenUiState(),
            actions = object : MainScreenActions {
                override fun onInputChange(newText: String) {}
                override fun onAddTodo(text: String) {}
                override fun onChangeTodoStatus(id: String, newState: Boolean) {}
                override fun onChangeRedactingInput(newText: String) {}
                override fun onEditTodo() {}
                override fun onDeleteTodo(id: String) {}
                override fun onSaveTodos() {}
                override fun onSelectTodo(id: String) {}
                override fun onRevertEditing() {}
                override fun onShowDialog() {}
                override fun onHideDialog() {}
            }
        )
    }
}