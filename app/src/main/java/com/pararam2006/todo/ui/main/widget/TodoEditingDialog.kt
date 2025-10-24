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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.pararam2006.todo.ui.theme.ToDoTheme

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