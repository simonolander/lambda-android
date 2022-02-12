package org.simonolander.lambda.domain

@JvmInline
value class LevelId(val value: String) {
    override fun toString(): String = value
}
