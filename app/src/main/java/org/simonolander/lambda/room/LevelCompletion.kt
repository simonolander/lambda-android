package org.simonolander.lambda.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LevelCompletion(
    @PrimaryKey val id: String,
    val levelId: String,
    val createdTime: Long
)
