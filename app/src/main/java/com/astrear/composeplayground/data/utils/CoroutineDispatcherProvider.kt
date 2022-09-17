package com.astrear.composeplayground.data.utils

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    fun defaultDispatcher(): CoroutineDispatcher
    fun ioDispatcher(): CoroutineDispatcher
}