package org.simonolander.lambda.engine

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.property.*
import io.kotest.property.arbitrary.*

class ExpressionTest : FunSpec({
    context("alphaEquals") {
        context("identifiers with same name are alpha equivalent") {
            forAll<String> { name ->
                val a = Identifier(name)
                val b = Identifier(name)
                withClue("`$a` should be alpha equivalent to `$b`") {
                    a.alphaEquals(b)
                }
            }
        }

        context("identifiers with different name are NOT alpha equivalent") {
            forAll<String, String> { a, b ->
                !Identifier(a).alphaEquals(Identifier(b)) || a == b
            }
        }

        context("identical expressions should be alpha equivalent") {
            forAll(expressionArb) {
                it.alphaEquals(it)
            }
        }

        context("safely renaming a functions parameter results in an alpha equivalent function") {
            checkAll(functionArb) { function ->
                val newParameterName =
                    nextName(function.unsafeParameterNames + function.parameterName)
                val renamedFunction = function.unsafeRenameParameter(newParameterName)
                withClue("`$function` should be alpha equivalent to `$renamedFunction`") {
                    function.alphaEquals(renamedFunction) shouldBe true
                }
            }
        }

        context("some alpha equivalent base cases") {
            listOf(
                "λ apple. apple" to "λ pear. pear",
                "λ a b c. a b c" to "λ x y z. x y z",
                "a (λx.x)" to "a (λa.a)",
                "(λx.x)(λx.x)" to "(λa.a)(λb.b)",
                "λx.(λx.x)(λx.x)" to "λa.(λa.a)(λb.b)",
                "λx.(λx.x)(λx.x)" to "λa.(λb.b)(λc.c)",
                "λx a. x (x a)" to "λf x. f (f x)",
            )
                .map { parse(it.first) to parse(it.second) }
                .forAll { (first, second) ->
                    withClue("$first` should be alpha equivalent to `$second`") {
                        first.alphaEquals(second) shouldBe true
                    }
                }
        }

        context("some not alpha equivalent base cases") {
            listOf(
                "λ apple. snapple" to "λ pear. pear",
                "λ a b c. a b c" to "λ x y z. x z y",
                "a (λx.x)" to "b (λa.a)",
                "(λx.x)(λx.x)" to "λx.x λx.x",
                "λx.(λx.x)(λx.x)" to "λa b.(λa.a)(λb.b)",
                "λx.(λx.x)(λx.x)" to "λa.(λb.a)(λc.c)",
                "λf x. f(f(x))" to "λf x. f(f(f(x)))",
            )
                .map { parse(it.first) to parse(it.second) }
                .forAll { (first, second) ->
                    withClue("$first` should not be alpha equivalent to `$second`") {
                        first.alphaEquals(second) shouldBe false
                    }
                }
        }
    }

    context("prettyPrint") {
        test("Base cases") {
            listOf(
                "a",
                "λ a. a",
                "a b c",
                "a (b c)",
                "a λ k. k",
                "a ((λ k. k) a)",
                "λ k. a ((λ k. k) a)",
                "(λ x. x) x",
                "(λ a. a) λ a. a λ a. a",
                "((λ a. a) λ a. a) λ a. a",
            ).forAll { expression ->
                withClue("Expression $expression should pretty print to itself") {
                    parse(expression).prettyPrint() shouldBe expression
                }
            }
        }

        test("parse(expression.prettyPrint()) should always equal expression") {
            PropertyTesting.defaultShrinkingMode = ShrinkingMode.Unbounded
            checkAll(expressionArb) { expression ->
                val printedValue = expression.prettyPrint()
                withClue("$printedValue should parse to $expression") {
                    parse(printedValue) shouldBe expression
                }
            }
        }
    }

    test("freeVariables") {
        listOf(
            "x" to "x",
            "a b c" to "a b c",
            "a b b" to "a b",
            "λa. a" to "",
            "λa. b" to "b",
            "λa. a b" to "b",
            "λa b. ab" to "ab",
            "λa b c. a b c" to "",
            "λa.(λa.b)(λb.a)" to "b",
        ).forAll {
            val expected = it.second.words()
            val actual = parse(it.first).freeVariables
            actual shouldBe expected
        }
    }

    context("Function") {
        test("unsafeParameterNames") {
            listOf(
                "λa. a" to "",
                "λa. b" to "b",
                "λa. λb. a" to "b",
            )
                .map { parse(it.first) as Function to it.second.words() }
                .forAll { (function, expected) ->
                    function.unsafeParameterNames shouldBe expected
                }
        }
    }
})

val expressionArb = expressionArb(emptyList(), 100)

fun expressionArb(boundNames: List<String>, maxDepth: Int): Arb<Expression> {
    return arbitrary(
        classifier = {
            when (it) {
                is Application -> "Application"
                is Function -> "Function"
                is Identifier -> "Identifier"
            }
        },
        shrinker = ExpressionShrinker(boundNames)
    ) {
        if (maxDepth <= 1) {
            identifierArb(boundNames).bind()
        }
        else {
            when (val case = it.random.nextInt(3)) {
                0 -> identifierArb(boundNames).bind()
                1 -> functionArb(boundNames, maxDepth).bind()
                2 -> applicationArb(boundNames, maxDepth).bind()
                else -> throw IllegalStateException("Invalid case $case, should have been one of {0, 1, 2} (seed ${it.seed})")
            }
        }
    }
}

fun identifierArb(boundNames: List<String>): Arb<Identifier> {
    return arbitrary(IdentifierShrinker()) {
        if (boundNames.isNotEmpty() && Arb.boolean().bind()) {
            val name = Arb.element(boundNames).bind()
            Identifier(name)
        }
        else {
            val name = parameterNameArb(boundNames).bind()
            Identifier(name)
        }
    }
}

val functionArb = functionArb(emptyList(), 10)

fun functionArb(boundNames: List<String>, maxDepth: Int): Arb<Function> {
    return arbitrary(
        shrinker = FunctionShrinker(boundNames)
    ) {
        val identifier = identifierArb(boundNames).bind()
        val body = expressionArb(boundNames + identifier.name, maxDepth - 1).bind()
        Function(identifier.name, body)
    }
}

fun applicationArb(boundNames: List<String>, maxDepth: Int): Arb<Application> {
    return arbitrary {
        Application(
            expressionArb(boundNames, maxDepth - 1).bind(),
            expressionArb(boundNames, maxDepth - 1).bind(),
        )
    }
}

fun parameterNameArb(excludedNames: List<String>): Arb<String> {
    return arbitrary {
        it.random.azstring(it.random.nextInt(1, 10))
    }.filterNot {
        it in excludedNames
    }
}

class ExpressionShrinker(private val boundNames: List<String>) : Shrinker<Expression> {
    override fun shrink(value: Expression): List<Expression> {
        return when (value) {
            is Application -> shrinkApplication(value)
            is Function -> shrinkFunction(value)
            is Identifier -> IdentifierShrinker().shrink(value)
        }
    }

    private fun shrinkApplication(application: Application): List<Expression> {
        val shrunkApplication = ApplicationShrinker(boundNames).shrink(application)
        return shrunkApplication + application.argument + application.function
    }

    private fun shrinkFunction(function: Function): List<Expression> {
        val shrunkFunction = FunctionShrinker(boundNames).shrink(function)
        return shrunkFunction
    }
}

class IdentifierShrinker :
    Shrinker<Identifier> {
    override fun shrink(value: Identifier): List<Identifier> {
        return StringShrinkerWithMin(1)
            .shrink(value.name)
            .map {
                Identifier(it)
            }
    }
}

class FunctionShrinker(private val boundNames: List<String>) : Shrinker<Function> {
    override fun shrink(value: Function): List<Function> {
        return shrinkParameterName(value) + shrinkBody(value) + removeIntermediate(value) +
            Function(
                value.parameterName,
                Identifier(value.parameterName)
            )
    }

    private fun removeIntermediate(function: Function): List<Function> {
        val (parameterName, body) = function
        if (body !is Function) {
            return emptyList()
        }
        if (parameterName in body.freeVariables) {
            return emptyList()
        }
        return listOf(body)
    }

    private fun shrinkParameterName(function: Function): List<Function> {
        return StringShrinkerWithMin(1)
            .shrink(function.parameterName)
            .map { newParameterName ->
                Function(
                    newParameterName,
                    function.body.substitute(function.parameterName, Identifier(newParameterName)),
                )
            }
    }

    private fun shrinkBody(function: Function): List<Function> {
        return ExpressionShrinker(boundNames + function.parameterName).shrink(function.body)
            .map { Function(function.parameterName, it) }
    }
}

class ApplicationShrinker(private val boundNames: List<String>) : Shrinker<Application> {
    override fun shrink(value: Application): List<Application> {
        val shrunkFunction = ExpressionShrinker(boundNames)
            .shrink(value.function)
            .map { Application(it, value.argument) }
        val shrunkArgument = ExpressionShrinker(boundNames)
            .shrink(value.function)
            .map { Application(value.function, it) }
        return shrunkFunction + shrunkArgument
    }
}

private fun String.words(): Set<String> {
    return split(" ")
        .filter(String::isNotEmpty)
        .toSet()
}
