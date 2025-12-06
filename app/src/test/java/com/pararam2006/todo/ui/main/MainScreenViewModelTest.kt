package com.pararam2006.todo.ui.main

import com.pararam2006.todo.data.FakeTodoRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MainScreenViewModelTest {
    private lateinit var fakeRepository: FakeTodoRepository
    private lateinit var viewModel: MainScreenViewModel

    @Before
    fun setup() {
        fakeRepository = FakeTodoRepository()
        viewModel = MainScreenViewModel(fakeRepository)
        fakeRepository.addTodo("bebebe1", "1")
        fakeRepository.addTodo("bebebe2", "2")
    }

    @Test
    fun selectTodo() {
        viewModel.addTodo("select Todo test")
        println(viewModel.uiState.todoList)

        val index = viewModel.uiState.todoList.indexOfFirst { it.text == "select Todo test" }
        assertTrue("Task should be present", index >= 0)

        val id = viewModel.uiState.todoList[index].id

        viewModel.selectTodo(id)

        assertEquals(index, viewModel.uiState.selectedIndex)
        assertEquals("select Todo test", viewModel.uiState.selectedText)
    }

    @Test
    fun `revertEditing restore original text when todo selected`() {
        val originalText = "original"
        viewModel.addTodo(originalText)

        val id = viewModel.uiState.todoList.first { it.text == originalText }.id

        viewModel.selectTodo(id)
        viewModel.changeRedactingInput("edited")
        viewModel.revertEditing()

        assertEquals(originalText, viewModel.uiState.redactingInput)
    }


    @Test
    fun `revertEditing does nothing when no todo selected`() {
        val stateBefore = viewModel.uiState

        viewModel.revertEditing()

        assertEquals(stateBefore, viewModel.uiState)
    }

    @Test
    fun `addTodo correctly adds item to list`() {
        val oldSize = viewModel.uiState.todoList.size

        viewModel.changeInput("test123")
        viewModel.addTodo("test123")

        val newList = viewModel.uiState.todoList

        assertEquals(oldSize + 1, newList.size)

        val added = newList.last()
        assertEquals("test123", added.text)
        assertTrue(added.id.isNotBlank())

        assertEquals("", viewModel.uiState.input)
    }

    @Test
    fun `deleteTodo removes item from list`() {
        val text = "toDelete"
        viewModel.addTodo(text)

        val id = viewModel.uiState.todoList.first { it.text == text }.id

        viewModel.deleteTodo(id)

        val list = viewModel.uiState.todoList

        assertTrue(list.none { it.id == id })
    }

    @Test
    fun `editTodo updates repository and uiState when item selected and new text is valid`() {
        viewModel.addTodo("old text")

        val id = viewModel.uiState.todoList.first().id
        viewModel.selectTodo(id)

        viewModel.changeRedactingInput("new text")

        viewModel.editTodo()

        val editedTodo = viewModel.uiState.todoList.first()

        assertEquals("new text", editedTodo.text)
        assertEquals("new text", viewModel.uiState.selectedText)
    }
}