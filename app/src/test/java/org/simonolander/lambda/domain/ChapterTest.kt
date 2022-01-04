package org.simonolander.lambda.domain

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.shouldBe

class ChapterTest : FunSpec({
    val chapters = Chapter.values().toList()
    test("chapters should have unique ids") {
        chapters.forAll { chapter ->
            withClue("There should only be one chapter with id $chapter") {
                chapters shouldHaveSingleElement { it.id == chapter.id }
            }
        }
    }

    test("findById") {
        chapters.forAll {
            Chapter.findById(it.id) shouldBe it
        }
    }
})
