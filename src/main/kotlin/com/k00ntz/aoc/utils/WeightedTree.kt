package com.k00ntz.aoc.utils

class WeightedTree<T, W>(private val nodeMap: MutableMap<T, TreeNode<T, W>> = mutableMapOf()) {

    companion object {
        fun <T, W> buildFromMap(input: Map<T, Map<T, W>>): WeightedTree<T, W> {
            val wt = WeightedTree<T, W>()
            input.forEach { (bag, children) ->
                children.forEach { (b, n) ->
                    wt.addNode(b, n, bag)
                }
            }
            return wt
        }
    }

    fun addNode(value: T, weight: W, parent: T) {
        nodeMap.putIfAbsent(parent, TreeNode(parent))
        nodeMap.putIfAbsent(value, TreeNode(value))
        val p = nodeMap[parent]!!
        val v = nodeMap[value]!!
        p.children[value] = Pair(weight, v)
    }

    fun getParents(value: T): List<TreeNode<T, W>>? =
        nodeMap[value]?.parents

}

data class TreeNode<T, W>(
    val value: T,
    val parents: MutableList<TreeNode<T, W>> = mutableListOf(),
    val children: MutableMap<T, Pair<W, TreeNode<T, W>>> = mutableMapOf()
)