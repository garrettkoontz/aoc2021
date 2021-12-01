package com.k00ntz.aoc.utils

import kotlin.math.abs

class LinkedNode<T>(
    val value: T,
    var prevNode: LinkedNode<T>? = null,
    var nextNode: LinkedNode<T>? = null
) : Iterable<LinkedNode<T>> {

    fun nodeAt(distance: Int): LinkedNode<T> {
        return when {
            distance == 1 -> nextNode ?: throw ArrayIndexOutOfBoundsException(1)
            distance == -1 -> prevNode ?: throw ArrayIndexOutOfBoundsException(-1)
            distance < 0 -> {
                (0 until abs(distance)).fold(this) { acc, _ ->
                    acc.prevNode ?: throw ArrayIndexOutOfBoundsException(
                        distance
                    )
                }
            }
            distance > 0 -> {
                (0 until distance).fold(this) { acc, _ ->
                    acc.nextNode ?: throw ArrayIndexOutOfBoundsException(distance)
                }
            }
            else -> this
        }
    }

    fun removeAt(distance: Int): Pair<LinkedNode<T>, LinkedNode<T>?> {
        val removedNode = nodeAt(distance)
        removedNode.prevNode?.nextNode = removedNode.nextNode
        removedNode.nextNode?.prevNode = removedNode.prevNode
        return Pair(removedNode, removedNode.nextNode)
    }

    fun add(i: T): LinkedNode<T> {
        val ln = LinkedNode(i)
        this.nextNode = ln
        this.nextNode?.prevNode = this
        return ln
    }

    fun toList(): List<T> {
        var node = this
        val lst = mutableListOf(this.value)
        while (node.nextNode != null) {
            node = node.nextNode!!
            lst.add(node.value)
        }
        return lst
    }

    override fun iterator(): Iterator<LinkedNode<T>> {
        val thing = this
        return object : Iterator<LinkedNode<T>> {
            var nd: LinkedNode<T>? = thing

            override fun hasNext(): Boolean =
                nd != null

            override fun next(): LinkedNode<T> =
                nd.also {
                    nd = nd?.nextNode
                } ?: throw RuntimeException("No next node")

        }
    }

    override fun toString(): String =
        "value: $value, previous: ${prevNode?.value}, next: ${nextNode?.value}"


}

class SinglyLinkedNode<T>(val value: T, var next: SinglyLinkedNode<T>? = null)