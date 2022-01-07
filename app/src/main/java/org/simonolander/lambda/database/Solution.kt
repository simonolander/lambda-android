package org.simonolander.lambda.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simonolander.lambda.domain.LevelId
import java.util.*

@Entity
data class Solution(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "level_id")
    val levelId: String,
    @ColumnInfo(name = "value")
    val value: String? = null,
    @ColumnInfo(name = "created_time")
    val createdTime: Long = System.currentTimeMillis(),
)
