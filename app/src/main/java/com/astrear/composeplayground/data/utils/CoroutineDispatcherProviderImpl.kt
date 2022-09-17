package com.astrear.composeplayground.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatcherProviderImpl : CoroutineDispatcherProvider {
    override fun defaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    override fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}