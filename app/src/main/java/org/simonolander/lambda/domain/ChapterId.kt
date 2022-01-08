package org.simonolander.lambda.domain

@JvmInline
value class ChapterId(val value: String) {
    override fun toString(): String = value
}
