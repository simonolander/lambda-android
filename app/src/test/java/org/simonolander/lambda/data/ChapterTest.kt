package org.simonolander.lambda.data

import org.junit.Assert.*
import org.junit.Test

class ChapterTest {

    @Test
    fun uniqueIds() {
        val chapters = Chapter.values()
        chapters.forEach { chapter ->
            val count = chapters.count { it.id == chapter.id }
            assertEquals("Multiple occurrences of ${chapter.id}", 1, count)
        }
    }

    @Test
    fun findById() {
        val chapters = Chapter.values()
        chapters.forEach { chapter ->
            assertEquals(chapter, Chapter.findById(chapter.id))
        }
    }
}
