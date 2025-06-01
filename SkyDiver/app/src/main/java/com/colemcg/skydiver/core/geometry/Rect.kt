package com.colemcg.skydiver.core.geometry

/**
 * This class is a rectangle used specifically to act as a collider
 * it contains methods for checking if a point is inside the rectangle,
 * if two rectangles are intersecting, and for moving the rectangle
 * 
 * @param x The x coordinate of the top left corner of the rectangle
 * @param y The y coordinate of the top left corner of the rectangle
 * @param width The width of the rectangle
 * @param height The height of the rectangle
 * 
 * @author Cole McGregor
 */

 //this is the constructor for the Rect class
data class Rect(
    var x: Float,
    var y: Float,
    var width: Float,
    var height: Float
) {

    //this is a function that returns the right edge of the rectangle
    fun right(): Float = x + width
    //this is a function that returns the bottom edge of the rectangle
    fun bottom(): Float = y + height

    //this is a function that checks if a point is inside the rectangle
    fun contains(point: Vector2): Boolean {
        return point.x >= x && point.x <= right() && //if the  point is greater than the left edge and less than the right edge
               point.y >= y && point.y <= bottom() //if the point is greater than the top edge and less than the bottom edge
    }

    //this is a function that checks if two rectangles are intersecting
    //it covers all forms of intersection, except for edge-edge intersection
    fun intersects(other: Rect): Boolean {
        return x < other.right() && //if the left edge of the current rectangle is less than the right edge of the other rectangle
               right() > other.x && //if the right edge of the current rectangle is greater than the left edge of the other rectangle
               y < other.bottom() && //if the top edge of the current rectangle is less than the bottom edge of the other rectangle
               bottom() > other.y //if the bottom edge of the current rectangle is greater than the top edge of the other rectangle
    }

    //this is a function that moves the rectangle by a certain amount, used to quickly move the rectangle without having to change the position
    fun offset(dx: Float, dy: Float) {
        x += dx
        y += dy
    }

    //this is a function that returns the center of the rectangle
    fun center(): Vector2 {
        return Vector2(x + width / 2, y + height / 2)
    }

    //this is a function that returns a copy of the rectangle
    //this is used to create a new rectangle that is the same as the current one, but can be moved around without affecting the original
    //can be used for simulating motion`
    fun copy(): Rect {
        return Rect(x, y, width, height)
    }

    //this is a function that sets the position of the rectangle, used to quickly move the rectangle without having to change the position  
    fun setPosition(newX: Float, newY: Float) {
        this.x = newX
        this.y = newY
    }

    //this is a function that sets the size of the rectangle, in the event it needs to be changed
    fun setSize(newWidth: Float, newHeight: Float) {
        this.width = newWidth
        this.height = newHeight
    }

    //this is a function that returns a string representation of the rectangle, for debugging purposes
    override fun toString(): String {
        return "Rect(x=$x, y=$y, width=$width, height=$height)"
    }
}
