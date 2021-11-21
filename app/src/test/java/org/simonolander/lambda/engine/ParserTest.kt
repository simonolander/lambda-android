package org.simonolander.lambda.engine

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class ParserTest : FunSpec({
    context("parse") {
        context("base cases") {
            test("identifier") {
                val actual = parse("x")
                val expected = Identifier("x", 0)
                actual shouldBe expected
            }

            test("long identifier") {
                val actual = parse("house")
                val expected = Identifier("house", 0)
                actual shouldBe expected
            }

            test("function backslash") {
                val actual = parse("\\x.x")
                val expected = Function("x", Identifier("x", 0))
                actual shouldBe expected
            }

            test("function lambda") {
                val actual = parse("λx.x")
                val expected = Function("x", Identifier("x", 0))
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
                            Identifier("b", 1)
                        )
                    )
                )
                actual shouldBe expected
            }

            test("application") {
                val actual = parse("a b")
                val expected = Application(
                    Identifier("a", 0),
                    Identifier("b", 0),
                )
                actual shouldBe expected
            }

            test("long application") {
                val actual = parse("a b c d e")
                val expected = Application(
                    Application(
                        Application(
                            Application(
                                Identifier("a", 0),
                                Identifier("b", 0),
                            ),
                            Identifier("c", 0),
                        ),
                        Identifier("d", 0),
                    ),
                    Identifier("e", 0),
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
                                            Identifier("g", 6)
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
                            Identifier("a", 0),
                            Identifier("b", 1),
                        ),
                        Identifier("c", 1),
                    )
                )
                actual shouldBe expected
            }
        }

        test("complex expressions") {
            listOf(
                "λa.λa.a" to Function("a", Function("a", Identifier("a", 0))),
                "(λx.x)λx.x" to Application(
                    Function("x", Identifier("x", 0)),
                    Function("x", Identifier("x", 0)),
                ),
                "(λx y.x)x \\t. t t" to Application(
                    Application(
                        Function("x", Function("y", Identifier("x", 1))),
                        Identifier("x", 0),
                    ),
                    Function(
                        "t", Application(
                            Identifier("t", 0),
                            Identifier("t", 0),
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
