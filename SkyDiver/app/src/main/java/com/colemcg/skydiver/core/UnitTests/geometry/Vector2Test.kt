package com.colemcg.skydiver.core.UnitTests.geometry

import com.colemcg.skydiver.core.geometry.Vector2
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.sqrt

class Vector2Test {

    @Test
    fun `plus adds vectors correctly`() {
        val a = Vector2(3f, 4f)
        val b = Vector2(1f, 2f)
        val result = a + b
        assertEquals(4f, result.x, 0.0001f)
        assertEquals(6f, result.y, 0.0001f)
    }

    @Test
    fun `minus subtracts vectors correctly`() {
        val a = Vector2(5f, 6f)
        val b = Vector2(2f, 3f)
        val result = a - b
        assertEquals(3f, result.x, 0.0001f)
        assertEquals(3f, result.y, 0.0001f)
    }

    @Test
    fun `times scales vector correctly`() {
        val v = Vector2(2f, 4f)
        val result = v * 2f
        assertEquals(4f, result.x, 0.0001f)
        assertEquals(8f, result.y, 0.0001f)
    }

    @Test
    fun `div divides vector correctly`() {
        val v = Vector2(10f, 5f)
        val result = v / 5f
        assertEquals(2f, result.x, 0.0001f)
        assertEquals(1f, result.y, 0.0001f)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `div throws on divide by zero`() {
        val v = Vector2(1f, 1f)
        v / 0f
    }

    @Test
    fun `length computes magnitude correctly`() {
        val v = Vector2(3f, 4f)
        val expected = 5f // √(3² + 4²)
        assertEquals(expected, v.length(), 0.0001f)
    }

    @Test
    fun `normalize returns unit vector`() {
        val v = Vector2(0f, 10f)
        val n = v.normalize()
        assertEquals(0f, n.x, 0.0001f)
        assertEquals(1f, n.y, 0.0001f)
    }

    @Test
    fun `normalize of zero vector returns zero vector`() {
        val v = Vector2(0f, 0f)
        val n = v.normalize()
        assertEquals(0f, n.x, 0.0001f)
        assertEquals(0f, n.y, 0.0001f)
    }

    @Test
    fun `copy returns new vector with same values`() {
        val v = Vector2(5f, 7f)
        val copy = v.copy()
        assertEquals(v.x, copy.x, 0.0001f)
        assertEquals(v.y, copy.y, 0.0001f)
        assertNotSame(v, copy)
    }

    @Test
    fun `set changes values in place`() {
        val v = Vector2()
        v.set(8f, 9f)
        assertEquals(8f, v.x, 0.0001f)
        assertEquals(9f, v.y, 0.0001f)
    }

    @Test
    fun `isZero detects zero vector`() {
        val zero = Vector2(0f, 0f)
        assertTrue(zero.isZero())
    }

    @Test
    fun `isZero returns false for non-zero vector`() {
        val v = Vector2(0f, 0.1f)
        assertFalse(v.isZero())
    }

    @Test
    fun `toString prints correctly`() {
        val v = Vector2(1.5f, -2f)
        assertEquals("(1.5, -2.0)", v.toString())
    }

    @Test
    fun `distanceTo computes correct distance between vectors`() {
        val a = Vector2(3f, 4f)
        val b = Vector2(0f, 0f)
        val expected = 5f // sqrt(3^2 + 4^2)
        assertEquals(expected, a.distanceTo(b), 0.0001f)
    }

    @Test
    fun `distanceTo is symmetric`() {
        val a = Vector2(1f, 2f)
        val b = Vector2(4f, 6f)
        val ab = a.distanceTo(b)
        val ba = b.distanceTo(a)
        assertEquals(ab, ba, 0.0001f)
    }
}
