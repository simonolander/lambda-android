package org.simonolander.lambda.engine

import junit.framework.Assert.assertEquals
import org.junit.Test

class ParserTest {
    @Test
    fun parseIdentifier() {
        val actual = parse("x")
        val expected = Identifier("x", -1)
        assertEquals(expected, actual)
    }

    @Test
    fun parseLongIdentifier() {
        val actual = parse("house")
        val expected = Identifier("house", -1)
        assertEquals(expected, actual)
    }

    @Test
    fun parseFunctionBackslash() {
        val actual = parse("\\x.x")
        val expected = Function("x", Identifier("x", 0))
        assertEquals(expected, actual)
    }

    @Test
    fun parseFunctionLambda() {
        val actual = parse("λx.x")
        val expected = Function("x", Identifier("x", 0))
        assertEquals(expected, actual)
    }

    @Test
    fun parseMultiParameterFunction() {
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
        assertEquals(expected, actual)
    }

    @Test
    fun parseApplication() {
        val actual = parse("a b")
        val expected = Application(
            Identifier("a", -1),
            Identifier("b", -1),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun parseLongApplication() {
        val actual = parse("a b c d e")
        val expected = Application(
            Application(
                Application(
                    Application(
                        Identifier("a", -1),
                        Identifier("b", -1),
                    ),
                    Identifier("c", -1),
                ),
                Identifier("d", -1),
            ),
            Identifier("e", -1),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun parseFunctionWithLongBody() {
        val actual = parse("λa. a b c")
        val expected = Function(
            "a",
            Application(
                Application(
                    Identifier("a", 0),
                    Identifier("b", -1),
                ),
                Identifier("c", -1),
            )
        )
        assertEquals(expected, actual)
    }

    @Test
    fun parseComplexExpressions() {
        for ((code, expected) in listOf(
            "λa.λa.a" to Function("a", Function("a", Identifier("a", 0))),
            "(λx.x)λx.x" to Application(
                Function("x", Identifier("x", 0)),
                Function("x", Identifier("x", 0)),
            ),
            "(λx y.x)x \\t. t t" to Application(
                Application(
                    Function("x", Function("y", Identifier("x", 1))),
                    Identifier("x", -1),
                ),
                Function("t", Application(
                    Identifier("t", 0),
                    Identifier("t", 0),
                ))
            )
        )) {
            val actual = parse(code)
            assertEquals("$code : incorrect parse result", expected, actual)
        }
    }
}
