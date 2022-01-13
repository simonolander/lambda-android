package org.simonolander.lambda.misc

const val lambda = "λ"
const val lambdaCalculus = "λ‑calculus" // Non-breaking dash
const val javascript = "Javascript"

fun String.nonBreaking(): String = replace(" ", " ")
