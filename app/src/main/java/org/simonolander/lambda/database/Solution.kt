package org.simonolander.lambda.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simonolander.lambda.domain.LevelId
import java.time.Instant
import java.util.*

@Entity
data class Solution(
    @PrimaryKey
    val id: UUID,

    @ColumnInfo(name = "value")
    val value: String,

    @ColumnInfo(name = "level_id")
    val levelId: LevelId,

    @ColumnInfo(name = "created_time")
    val createdTime: Instant,
)
