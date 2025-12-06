package com.pararam2006.todo.di

import com.pararam2006.todo.data.TodoRepositoryImpl
import com.pararam2006.todo.domain.repository.TodoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TodoRepository> { TodoRepositoryImpl(get()) }
}