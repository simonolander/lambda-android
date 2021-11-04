package org.simonolander.lambda.model

import androidx.annotation.IdRes

data class Level(
    val id: LevelId,
    val name: String,
    @IdRes val destination: Int,
)

@JvmInline
value class LevelId(val value: String)
