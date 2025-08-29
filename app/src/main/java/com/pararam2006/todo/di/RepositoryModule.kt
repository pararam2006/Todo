package com.pararam2006.todo.di

import com.pararam2006.todo.data.TodoRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::TodoRepository)
}