package com.k00ntz.aoc.utils

class Ring<T : Any>(private val list: List<T>, private val size: Int = list.size, private var start: Int = 0) :
    Iterable<T> {
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        override fun hasNext(): Boolean = true

        override fun next(): T = list[start % size].also { start++ }

    }
}


class MapRing<T>(val mp: MutableMap<T, T>) {
    constructor(l: List<T>) : this(
        l.zipWithNext().associate { it }.plus(l.last() to l.first()).toMutableMap()
    )

    fun toList(start: T): List<T> {
        var s = start
        val lst = mutableListOf(s)
        while (lst.size < mp.size) {
            s = mp[s]!!
            lst.add(s)
        }
        return lst
    }

    fun removeAfter(t: T, i: Int = 3): List<T> {
        val r = mutableListOf(mp[t]!!)
        repeat(i - 1) {
            r.add(mp[r.last()]!!)
        }
        mp[t] = mp[r.last()]!!
        return r
    }

    fun insertAfter(t: T, lst: List<T>) {
        val tNext = mp[t]!!
        mp[t] = lst.first()
        mp[lst.last()] = tNext
    }

    operator fun get(t: T): T? =
        mp[t]
}