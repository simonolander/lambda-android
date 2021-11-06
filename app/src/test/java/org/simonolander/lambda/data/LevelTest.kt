package org.simonolander.lambda.data

import org.junit.Assert.assertEquals
import org.junit.Test

class LevelTest {

    @Test
    fun uniqueIds() {
        val levels = Level.values()
        levels.forEach { level ->
            val count = levels.count { it.id == level.id }
            assertEquals("Multiple occurrences of ${level.id}", 1, count)
        }
    }

    @Test
    fun findById() {
        val levels = Level.values()
        levels.forEach { level ->
            assertEquals(level, Level.findById(level.id))
        }
    }
}
