package org.simonolander.lambda.engine

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ReducerTest : FunSpec({

    test("reduceAll") { }

    context("reduceOnce") {
        test("expressions already in normal form should not be reducible") {
            listOf(
                "x",
                "λx.x",
                "a b",
            ).forAll {
                reduceOnce(parse(it)) shouldBe null
            }
        }
    }
})
