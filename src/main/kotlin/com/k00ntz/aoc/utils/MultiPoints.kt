package com.k00ntz.aoc.utils

import kotlin.math.abs

interface Neighborly<T>{
    fun neighbors(distance: Int = 1): List<T>
}

data class Point3(val x: Int, val y: Int, val z: Int) : Neighborly<Point3> {

    val label: String = "x: $x, y: $y, z: $z"

    companion object {
        private val r = "<x=(-?[0-9]+), y=(-?[0-9]+), z=(-?[0-9]+)>".toRegex()
        fun fromString(s: String): Point3 =
            r.matchEntire(s)?.destructured?.let { (x, y, z) ->
                Point3(x.toInt(), y.toInt(), z.toInt())
            } ?: throw RuntimeException("unable to parse $s to Point3")

    }

    operator fun plus(other: Point3): Point3 =
        Point3(this.x + other.x, this.y + other.y, this.z + other.z)

    fun energy(): Int =
        abs(x) + abs(y) + abs(z)

    override fun neighbors(distance: Int): List<Point3> =
        ((distance * -1)..distance).flatMap { x ->
            ((distance * -1)..distance).flatMap { y ->
                ((distance * -1)..distance).map { z ->
                    Point3(x, y, z) + this
                }
            }
        }.filter { it != this }

}


data class Point4(val x: Int, val y: Int, val z: Int, val w: Int): Neighborly<Point4> {

    companion object{
        fun fromPoint3(pt3: Point3, w: Int = 0): Point4 =
            Point4(pt3.x, pt3.y, pt3.z, w)
    }

    val label: String = "x: $x, y: $y, z: $z, w: $w"

    operator fun plus(other: Point4): Point4 =
        Point4(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w)

    override fun neighbors(distance: Int): List<Point4> =
        ((distance * -1)..distance).flatMap { x ->
            ((distance * -1)..distance).flatMap { y ->
                ((distance * -1)..distance).flatMap { z ->
                    ((distance * -1)..distance).map { w ->
                        Point4(x, y, z, w) + this
                    }
                }
            }
        }.filter { it != this }
}
