package org.simonolander.lambda.engine

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class ParserTest : FunSpec({
    context("parse") {
        context("base cases") {
            test("identifier") {
                val actual = parse("x")
                val expected = Identifier("x")
                actual shouldBe expected
            }

            test("long identifier") {
                val actual = parse("house")
                val expected = Identifier("house")
                actual shouldBe expected
            }

            test("function backslash") {
                val actual = parse("\\x.x")
                val expected = Function("x", Identifier("x"))
                actual shouldBe expected
            }

            test("function lambda") {
                val actual = parse("λx.x")
                val expected = Function("x", Identifier("x"))
                actual shouldBe expected
            }

            test("multi parameter function") {
                val actual = parse("λa b c.b")
                val expected = Function(
                    "a",
                    Function(
                        "b",
                        Function(
                            "c",
                            Identifier("b")
                        )
                    )
                )
                actual shouldBe expected
            }

            test("application") {
                val actual = parse("a b")
                val expected = Application(
                    Identifier("a"),
                    Identifier("b"),
                )
                actual shouldBe expected
            }

            test("long application") {
                val actual = parse("a b c d e")
                val expected = Application(
                    Application(
                        Application(
                            Application(
                                Identifier("a"),
                                Identifier("b"),
                            ),
                            Identifier("c"),
                        ),
                        Identifier("d"),
                    ),
                    Identifier("e"),
                )
                actual shouldBe expected
            }

            test("deep free variable") {
                val actual = parse("λa b c d e f.g")
                val expected =
                    Function(
                        "a",
                        Function(
                            "b",
                            Function(
                                "c",
                                Function(
                                    "d",
                                    Function(
                                        "e",
                                        Function(
                                            "f",
                                            Identifier("g")
                                        )
                                    )
                                )
                            )
                        )
                    )
                actual shouldBe expected
            }

            test("function with long body") {
                val actual = parse("λa. a b c")
                val expected = Function(
                    "a",
                    Application(
                        Application(
                            Identifier("a"),
                            Identifier("b"),
                        ),
                        Identifier("c"),
                    )
                )
                actual shouldBe expected
            }
        }

        test("complex expressions") {
            listOf(
                "λa.λa.a" to Function("a", Function("a", Identifier("a"))),
                "(λx.x)λx.x" to Application(
                    Function("x", Identifier("x")),
                    Function("x", Identifier("x")),
                ),
                "(λx y.x)x \\t. t t" to Application(
                    Application(
                        Function("x", Function("y", Identifier("x"))),
                        Identifier("x"),
                    ),
                    Function(
                        "t", Application(
                            Identifier("t"),
                            Identifier("t"),
                        )
                    )
                )
            ).forAll {
                val (code, expected) = it
                parse(code) shouldBe expected
            }
        }
    }
})
