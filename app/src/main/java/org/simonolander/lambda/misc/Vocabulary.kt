package org.simonolander.lambda.misc

const val lambda = "λ"
const val lambdaCalculus = "λ‑calculus" // Non-breaking dash
const val javascript = "Javascript"

/**
 * Replace spaces and dashes with their non-breaking counterparts
 */
fun String.nonBreaking(): String =
    replace(' ', ' ')
        .replace('-', '‑')
