package com.mv.acessif.data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val defaultDispatcher: CoroutineDispatcher
    val mainDispatcher: CoroutineDispatcher
    val ioDispatcher: CoroutineDispatcher
}

class DefaultDispatcherProvider(
    override val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    override val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    override val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DispatcherProvider
