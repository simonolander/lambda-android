package org.simonolander.lambda.model

data class Level(
    val id: LevelId,
    val name: String,
)

@JvmInline
value class LevelId(val value: String)
