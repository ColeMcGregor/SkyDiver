package geometry

import com.colemcg.skydiver.core.geometry.Rect
import com.colemcg.skydiver.core.geometry.Vector2
import org.junit.Assert.*
import org.junit.Test

class RectTest {

    @Test
    fun `contains returns true for point inside rect`() {
        val rect = Rect(0f, 0f, 10f, 10f)
        val point = Vector2(5f, 5f)
        assertTrue(rect.contains(point))
    }

    @Test
    fun `contains returns false for point outside rect`() {
        val rect = Rect(0f, 0f, 10f, 10f)
        val point = Vector2(11f, 5f)
        assertFalse(rect.contains(point))
    }

    @Test
    fun `intersects returns true for overlapping Rect`() {
        val a = Rect(0f, 0f, 10f, 10f)
        val b = Rect(5f, 5f, 10f, 10f)
        assertTrue(a.intersects(b))
    }

    @Test
    fun `intersects returns false for non-overlapping Rect`() {
        val a = Rect(0f, 0f, 10f, 10f)
        val b = Rect(20f, 20f, 5f, 5f)
        assertFalse(a.intersects(b))
    }

    @Test
    fun `intersects returns false for edge-touching Rect`() {
        val a = Rect(0f, 0f, 10f, 10f)
        val b = Rect(10f, 0f, 10f, 10f) // touches right edge
        assertFalse(a.intersects(b))
    }

    @Test
    fun `offset correctly shifts rect position`() {
        val rect = Rect(5f, 5f, 10f, 10f)
        rect.offset(3f, -2f)
        assertEquals(8f, rect.x, 0.0001f)
        assertEquals(3f, rect.y, 0.0001f)
    }
    @Test
    fun `center returns correct center point`() {
        val rect = Rect(0f, 0f, 10f, 4f)
        val center = rect.center()
        assertEquals(5f, center.x, 0.0001f)
        assertEquals(2f, center.y, 0.0001f)
    }

    @Test
    fun `copy creates an identical but separate instance`() {
        val original = Rect(1f, 2f, 3f, 4f)
        val copy = original.copy()
        assertNotSame(original, copy)
    }

    @Test
    fun `setPosition changes position correctly`() {
        val rect = Rect(1f, 1f, 10f, 10f)
        rect.setPosition(5f, 7f)
        assertEquals(5f, rect.x, 0.0001f)
        assertEquals(7f, rect.y, 0.0001f)
    }

    @Test
    fun `setSize changes dimensions correctly`() {
        val rect = Rect(0f, 0f, 1f, 1f)
        rect.setSize(9f, 8f)
        assertEquals(9f, rect.width, 0.0001f)
        assertEquals(8f, rect.height, 0.0001f)
    }

    @Test
    fun `toString provides readable format`() {
        val rect = Rect(1f, 2f, 3f, 4f)
        val str = rect.toString()
        assertTrue(str.contains("x=1.0"))
        assertTrue(str.contains("y=2.0"))
        assertTrue(str.contains("width=3.0"))
        assertTrue(str.contains("height=4.0"))
    }
}
