package org.simonolander.lambda.engine

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ReducerTest : FunSpec({

    context("reduceAll") { }

    context("reduceOnce") {
        test("expressions already in normal form should not be reducible") {
            listOf(
                "x",
                "λx.x",
                "a b",
                "λf. f f",
            ).forAll {
                reduceOnce(parse(it)) shouldBe null
            }
        }

        test("η-reduction (eta)") {
            listOf(
                "λf. x f" to "x",
                "λf. (λx.x y) f" to "λx.x y",
                "λc. a b c" to "a b",
            ).forAll {
                val (code, expected) = it
                val before = parse(code)
                val after = parse(expected)
                reduceOnce(before) shouldBe EtaReduction(before, after)
            }
        }

        test("β-reduction (beta)") {
            listOf(
                "(λx.x)x" to "x",
                "(λx.x)y" to "y",
                "(λx.z)y" to "z",
                "(λx.z)z" to "z",
                "(λx.x)(λx.x)" to "λx.x",
                "(λx.xx)(λx.xx)" to "xx",
                "(λx.x x)(λx.x x)" to "(λx.x x)(λx.x x)",
                "(λa b. b λc.a)(λx.z)" to "λb. b λc x.z",
            ).forAll {
                val (code, expected) = it
                val before = parse(code)
                val after = parse(expected)
                reduceOnce(before) shouldBe BetaReduction(before, after)
            }
        }
    }
})
