package com.pararam2006.todo.ui.main

interface MainScreenActions {
    fun onInputChange(newText: String)
    fun onAddTodo(text: String)
    fun onChangeTodoStatus(id: String, newState: Boolean)
    fun onChangeRedactingInput(newText: String)
    fun onEditTodo()
    fun onDeleteTodo(id: String)
    fun onSaveTodos()
    fun onSelectTodo(id: String)
    fun onRevertEditing()
    fun onShowDialog()
    fun onHideDialog()
}
