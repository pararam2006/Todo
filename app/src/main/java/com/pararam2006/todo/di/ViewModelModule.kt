package com.pararam2006.todo.di

import com.pararam2006.todo.ui.main.MainScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::MainScreenViewModel)
}