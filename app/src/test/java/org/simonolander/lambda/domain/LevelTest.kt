package org.simonolander.lambda.domain

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

    test("nextLevel should return next level if exists") {
        levels.zipWithNext()
            .forAll { (l1, l2) ->
                Level.nextLevel(l1.id) shouldBe l2
            }
    }

    test("nextLevel should return null if last level") {
        Level.nextLevel(levels.last().id) shouldBe null
    }

    test("nextLevel should return null if level doesn't exist") {
        val doesNotExist = LevelId(levels.joinToString("-") { it.id.value })
        Level.findById(doesNotExist) shouldBe null // Sanity check
        Level.nextLevel(doesNotExist) shouldBe null
    }
})
