package org.simonolander.lambda.engine

import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe

infix fun <T : Expression, U : T> T.shouldBeAlphaEquivalentTo(expected: U) {
    withClue("expected $this to be alpha equivalent to $expected") {
        alphaEquals(expected) shouldBe true
    }
}
