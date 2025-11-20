package com.pararam2006.todo.ui.main.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.pararam2006.todo.R
import com.pararam2006.todo.ui.theme.ToDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCreationDialog(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDismissPressed: () -> Unit,
    onConfirmPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismissPressed,
        properties = DialogProperties(),
        content = {
            Card {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.77f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Создать новую задачу",
                            modifier = Modifier.padding(
                                top = dimensionResource(R.dimen.padding10),
                                bottom = dimensionResource(R.dimen.padding10),
                            )
                        )

                        OutlinedTextField(
                            value = title,
                            onValueChange = onTitleChange,
                            label = { Text("Название задачи *") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = description,
                            onValueChange = onDescriptionChange,
                            label = { Text("Описание (необязательно)") },
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimensionResource(R.dimen.padding8))
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                onClick = onDismissPressed,
                                modifier = Modifier.padding(
                                    start = dimensionResource(R.dimen.padding8),
                                    end = dimensionResource(R.dimen.padding8)
                                )
                            ) { Text("Отмена") }
                            TextButton(
                                onClick = onConfirmPressed,
                                modifier = Modifier.padding(
                                    start = dimensionResource(R.dimen.padding8),
                                    end = dimensionResource(R.dimen.padding8)
                                )
                            ) { Text("Создать") }
                        }
                    }
                }
            }
        },
        modifier = modifier.imePadding()
    )
}

@Preview
@Composable
fun TodoCreationDialogPreview() {
    ToDoTheme {
        TodoCreationDialog(
            title = "",
            description = "",
            onTitleChange = {},
            onDescriptionChange = {},
            onDismissPressed = {},
            onConfirmPressed = {},
        )
    }
}
