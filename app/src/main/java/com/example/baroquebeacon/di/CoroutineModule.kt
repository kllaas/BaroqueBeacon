package com.example.baroquebeacon.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    const val DISPATCHER_IO = "dispatcherIo"
    const val DISPATCHER_DEFAULT = "dispatcherDefault"
    const val DISPATCHER_MAIN = "dispatcherMain"

    @Provides
    @Singleton
    @Named(DISPATCHER_IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Named(DISPATCHER_DEFAULT)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    @Named(DISPATCHER_MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}