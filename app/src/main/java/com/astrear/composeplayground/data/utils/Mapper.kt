package com.astrear.composeplayground.data.utils

interface Mapper<I, O> {
    fun map(input: I): O
}

// Non-nullable to Non-nullable
interface IListMapper<I, O> : Mapper<List<I>, List<O>>

class ListMapper<I, O>(
    private val mapper: Mapper<I, O>
) : IListMapper<I, O> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}