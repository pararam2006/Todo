package com.pararam2006.todo.ui.main

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.pararam2006.todo.data.TodoRepositoryImpl
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainScreenViewModelIntegrationTest {

    private lateinit var context: Context
    private lateinit var viewModel: MainScreenViewModel

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .commit()

        val repository = TodoRepositoryImpl(context)
        viewModel = MainScreenViewModel(repository)
    }

    @Test
    fun `addTodo adds item and updates uiState`() {
        viewModel.onAddTodo("Integration task")

        val list = viewModel.uiState.todoList

        assertEquals(1, list.size)
        assertEquals("Integration task", list.first().text)
        assertFalse(list.first().isCompleted)
    }

    @Test
    fun `changeTodoStatus updates completion state`() {
        viewModel.onAddTodo("Task")
        val id = viewModel.uiState.todoList.first().id

        viewModel.onChangeTodoStatus(id, true)

        assertTrue(viewModel.uiState.todoList.first().isCompleted)
    }

    @Test
    fun `deleteTodo removes item from repository and uiState`() {
        viewModel.onAddTodo("Task to delete")
        val id = viewModel.uiState.todoList.first().id

        viewModel.onDeleteTodo(id)

        assertTrue(viewModel.uiState.todoList.isEmpty())
    }

    @Test
    fun `editTodo updates text and persists change`() {
        viewModel.onAddTodo("Old text")
        val id = viewModel.uiState.todoList.first().id

        viewModel.onSelectTodo(id)
        viewModel.onChangeRedactingInput("New text")
        viewModel.onEditTodo()

        val edited = viewModel.uiState.todoList.first()

        assertEquals("New text", edited.text)
        assertEquals("New text", viewModel.uiState.selectedText)
    }

    @Test
    fun `data persists in repository`() {
        viewModel.onAddTodo("Persistent task")

        val newViewModel = MainScreenViewModel(
            TodoRepositoryImpl(context)
        )

        assertEquals(1, newViewModel.uiState.todoList.size)
        assertEquals("Persistent task", newViewModel.uiState.todoList.first().text)
    }
}
