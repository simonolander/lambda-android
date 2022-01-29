package org.simonolander.lambda.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import org.simonolander.lambda.content.exercise.basic.*
import org.simonolander.lambda.content.exercise.booleans.*
import org.simonolander.lambda.content.exercise.numbers.*
import org.simonolander.lambda.content.exercise.pairs.firstExercise
import org.simonolander.lambda.content.exercise.pairs.secondExercise
import org.simonolander.lambda.engine.normalize
import org.simonolander.lambda.engine.parse
import org.simonolander.lambda.engine.shouldBeAlphaEquivalentTo

class ExerciseTest : FunSpec({
    val chapters = Chapter.values().toList()
    context("every exercise has a solution") {
        listOf(
            applicatorExercise to "λf a. f a",
            cardinalExercise to "λf a b. f b a",
            constantExercise to "λc. a",
            identityExercise to "λa. a",
            kestrelExercise to "λa b. a",
            kiteExercise to "λa b. b",
            andExercise to "λa b. a b a",
            falseExercise to "λa b. b",
            ifThenElseExercise to "λp a b. p a b",
            notExercise to "λp a b. p b a",
            orExercise to "λa b. a a b",
            trueExercise to "λa b. a",
            xorExercise to "λa b. a (not b) b",
            additionExercise to "λ a b. a succ b",
            equalsExercise to "λ a b. and (leq a b) (leq b a)",
            exponentiationExercise to "λ a b. b a",
            zeroExercise to "λn. n (const false) true",
            lessThanOrEqualExercise to "λ a b. zero (sub a b)",
            multiplicationExercise to "λa b. a (add b) 0",
            evenExercise to "λn. n not true",
            predecessorExercise to "λn f x. n (λg h. h (g f)) (const x) id",
            subtractionExercise to "λa b. b pred a",
            successorExercise to "λn f x. f (n f x)",
            firstExercise to "flip apply true",
            secondExercise to "flip apply false",
        ).forEach { (exercise, solution) ->
            test(exercise.name) {
                val library = exercise.library + (exercise.functionName to parse(solution))
                exercise.testCases.forAll { testCase ->
                    val maxDepth = 10000
                    val expected = normalize(testCase.output, library, maxDepth)!!
                    val actual = normalize(testCase.input, library, maxDepth)!!
                    actual shouldBeAlphaEquivalentTo expected
                }
            }
        }
    }

    test("findById") {
        chapters.forAll {
            Chapter.findById(it.id) shouldBe it
        }
    }
})
