package com.pararam2006.todo.di

import org.koin.dsl.module

val appModule = module {
    includes(
        viewModelModule,
        repositoryModule
    )
}