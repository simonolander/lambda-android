package org.simonolander.lambda.engine

import io.kotest.assertions.fail
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ReducerTest : FunSpec({

    context("reduceAll") {
        test("expressions in normal form should not reduce further") {
            listOf(
                "x",
                "λx.x",
                "a b",
                "λf. f f",
            ).forAll { expression ->
                reduceAll(parse(expression)).none()
            }
        }

        test("expressions with a normal form should reduce") {
            listOf(
                "λf. x f" to "x",
                "λf. (λx.x y) f" to "λx.x y",
                "λc. a b c" to "a b",
                "(λx.x) y" to "y",
                "(λx.x) λx.x" to "λx.x",
                "(λx.x) (λx.x) a" to "a",
                "((λx.x)λx.x) b" to "b",
                "(λf x. f (f x)) (λ v. a v) q" to "a (a q)",
            ).forAll { (expression, expected) ->
                withClue("`$expression` should reduce to `$expected`") {
                    val actual = reduceAll(parse(expression))
                        .take(100)
                        .last().after
                    actual shouldBe parse(expected)
                }
            }
        }

        context("booleans") {
            val library = mapOf(
                "true" to parse("λa b. a"),
                "false" to parse("λa b. b"),
                "not" to parse("λa. a false true"),
                "and" to parse("λa b. a b a"),
                "or" to parse("λa b. a a b"),
                "xor" to parse("λa b. and (or a b) (not (and a b))"),
                "eq" to parse("λa b. not (xor a b)"),
            )
            listOf(
                "true t f" to "t",
                "false t f" to "f",
                "true t" to "λb.t",
                "false t" to "λb.b",
                "not true" to "false",
                "not false" to "true",
                "not (not true)" to "true",
                "not (not false)" to "false",
                "and true true" to "true",
                "and true false" to "false",
                "and false true" to "false",
                "and false false" to "false",
                "or true true" to "true",
                "or true false" to "true",
                "or false true" to "true",
                "or false false" to "false",
                "xor true true" to "false",
                "xor true false" to "true",
                "xor false true" to "true",
                "xor false false" to "false",
                "eq true true" to "true",
                "eq true false" to "false",
                "eq false true" to "false",
                "eq false false" to "true",
            ).forAll { (expression, expectedString) ->
                withClue("`$expression` should reduce to `$expectedString`") {
                    val maxDepth = 100
                    val actual = normalize(parse(expression), library, maxDepth)
                    val expected = normalize(parse(expectedString), library, maxDepth)
                    actual.shouldNotBeNull()
                    actual shouldBe expected
                }
            }
        }

        context("church numerals") {
            val library = mapOf(
                "succ" to parse("λa f x. f (a f x)"),
                "add" to parse("λa b f x. a f (b f x)"),
                "multiply" to parse("λa b f. a (b f)"),
                "pow" to parse("λa b. b a"),
                "0" to parse("λf x. x"),
                "1" to parse("λf x. f x"),
                "2" to parse("λf x. f(f x)"),
                "3" to parse("λf x. f(f(f x))"),
                "4" to parse("λf x. f(f(f(f x)))"),
                "16" to parse("λf x. f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f x)))))))))))))))"),
                "27" to parse("λf x. f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f(f x))))))))))))))))))))))))))"),
            )
            listOf(
                "succ 0" to "1",
                "succ 1" to "2",
                "succ 2" to "3",
                "add 0 0" to "0",
                "add 0 1" to "1",
                "add 0 2" to "2",
                "add 1 0" to "1",
                "add 1 1" to "2",
                "add 1 2" to "3",
                "add 2 0" to "2",
                "add 2 1" to "3",
                "add 2 2" to "4",
                "multiply 0 0" to "0",
                "multiply 0 1" to "0",
                "multiply 0 2" to "0",
                "multiply 1 0" to "0",
                "multiply 1 1" to "1",
                "multiply 1 2" to "2",
                "multiply 2 0" to "0",
                "multiply 2 1" to "2",
                "multiply 2 2" to "4",
                "pow 0 0" to "1",
                "pow 0 3" to "0",
                "pow 1 0" to "1",
                "pow 1 1" to "1",
                "pow 1 4" to "1",
                "pow 2 0" to "1",
                "pow 2 1" to "2",
                "pow 2 4" to "16",
                "pow 3 0" to "1",
                "pow 3 1" to "3",
                "pow 3 3" to "27",
                "pow 4 1" to "4",
                "pow 4 2" to "16",
            ).forAll { (expression, expectedString) ->
                withClue("`$expression` should reduce to `$expectedString`") {
                    val maxDepth = 1000
                    val actual = normalize(parse(expression), library, maxDepth)
                    val expected = normalize(parse(expectedString), library, maxDepth)
                    actual.shouldNotBeNull()
                    expected.shouldNotBeNull()
                    actual.alphaEquals(expected) shouldBe true
                }
            }
        }
    }

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
                "(λa.a)(a b c)" to "a b c",
                "(λx.x)(λx.x)" to "λx.x",
                "(λx.xx)(λx.xx)" to "xx",
                "(λx.x x)(λx.x x)" to "(λx.x x)(λx.x x)",
                "(λa b. b λc.a)(λx.z)" to "λb. b λc x.z",
                "(λx.x x x)(λx.x x x)" to "(λx.x x x)(λx.x x x)(λx.x x x)",
            ).forAll {
                val (code, expected) = it
                val before = parse(code)
                val after = parse(expected)
                reduceOnce(before) shouldBe BetaReduction(before, after)
            }
        }

        test("α-renaming (alpha)") {
            val z = ('a'..'z').joinToString(" ")
            val aa = "$z aa"
            val ab = "$aa ab"
            listOf(
                "(λ x y. x) y" to "(λ x a. x) y",
                "(λ x y. x) ($z)" to "(λ x aa. x) ($z)",
                "(λ x y. x) ($aa)" to "(λ x ab. x) ($aa)",
                "(λ x y. x) ($ab)" to "(λ x ac. x) ($ab)",
                "(λ x. x λ y. x) y" to "(λ x. x λ a. x) y",
                "(λ x. (λ b. x) λ y. x) y" to "(λ x. (λ b. x) λ a. x) y",
            ).forAll { (code, expected) ->
                val before = parse(code)
                val after = parse(expected)
                val reduction = reduceOnce(before)
                    ?: fail("`$before` should have reduced, but was null")
                reduction.before shouldBe before
                reduction.after shouldBe after
                reduction.shouldBeInstanceOf<ApplicationFunctionReduction>()
                reduction.root.shouldBeInstanceOf<AlphaRenaming>()
            }
        }

        test("library substitution") {
            val library = mapOf(
                "true" to parse("λa b. a"),
                "false" to parse("λa b. b"),
                "not" to parse("λa. a false true"),
            )
            listOf(
                "true" to "λa b. a",
                "false" to "λa b. b",
                "not" to "λa. a false true",
                "true t f" to "(λa b. a) t f",
                "not true" to "(λa. a false true) true",
            ).forAll {
                val initial = parse(it.first)
                val expected = parse(it.second)
                val reduction = reduceOnce(initial, library)

                reduction.shouldNotBeNull()
                reduction.root.shouldBeInstanceOf<LibraryReduction>()
                reduction.after shouldBe expected
            }
        }
    }
})
