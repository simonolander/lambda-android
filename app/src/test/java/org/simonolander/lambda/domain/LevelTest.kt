package org.simonolander.lambda.domain

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.string
import io.kotest.property.forAll
import org.simonolander.lambda.content.Chapter
import org.simonolander.lambda.content.Level

class LevelTest : FunSpec({
    val levels = Level.values().toList()
    val chapters = Chapter.values().toList()
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

    test("isLastInChapter should return whether the given id is the last level in any chapter") {
        val lastLevelIds = chapters.mapNotNull { it.levels.lastOrNull()?.id }
        levels.map { it.id }
            .forAll { levelId ->
                Level.isLastInChapter(levelId) shouldBe (levelId in lastLevelIds)
            }
    }

    context("isLastInChapter of unknown level id should return false") {
        Arb.string()
            .map { LevelId(it) }
            .filter { Level.findById(it) == null }
            .forAll(1) { nonExistingLevelId ->
                !Level.isLastInChapter(nonExistingLevelId)
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
