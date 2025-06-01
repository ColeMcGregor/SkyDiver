package com.colemcg.skydiver.core.geometry

import kotlin.math.sqrt

/**
 * Vector2 is a 2D vector with x and y components
 * it is used to represent a point in 2D space or a direction and magnitude
 * 
 * @param x The x component of the vector.
 * @param y The y component of the vector.
 * 
 * @author Cole McGregor
 */

data class Vector2(var x: Float = 0f, var y: Float = 0f) {

    //vector addition
    operator fun plus(other: Vector2): Vector2 {
        return Vector2(this.x + other.x, this.y + other.y)
    }

    //vector subtraction
    operator fun minus(other: Vector2): Vector2 {
        return Vector2(this.x - other.x, this.y - other.y)
    }

    //scalar multiplication
    operator fun times(scalar: Float): Vector2 {
        return Vector2(this.x * scalar, this.y * scalar)
    }

    //scalar division
    operator fun div(scalar: Float): Vector2 {
        require(scalar != 0f) { "Division by zero" }
        return Vector2(this.x / scalar, this.y / scalar)
    }

    //length of the vector from the origin
    fun length(): Float {
        return sqrt(x * x + y * y)
    }

    //normalize the vector to a unit vector
    fun normalize(): Vector2 {
        val len = length()
        return if (len != 0f) this / len else Vector2(0f, 0f)
    }

    //copy the vector
    fun copy(): Vector2 {
        return Vector2(x, y)
    }

    //set the vector to a new value
    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    //distance between two vectors
    fun distanceTo(other: Vector2): Float {
        val dx = this.x - other.x
        val dy = this.y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    //convert the vector to a string
    override fun toString(): String {
        return "($x, $y)"
    }

    //check if the vector is zero
    fun isZero(): Boolean {
        return x == 0f && y == 0f
    }
}