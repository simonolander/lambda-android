package org.simonolander.lambda.data

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.shouldBe

class LevelTest : FunSpec({
    val levels = Level.values().toList()
    test("levels should have unique ids") {
        levels.forAll { level ->
            withClue("There should only be one level with id $level") {
                levels shouldHaveSingleElement { it.id == level.id }
            }
        }
    }

    test("findById") {
        levels.forAll { level ->
            Level.findById(level.id) shouldBe level
        }
    }
})
