package org.simonolander.lambda.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simonolander.lambda.domain.EventType
import java.util.*

@Entity
data class Event(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "type")
    val type: EventType,
    @ColumnInfo(name = "created_time")
    val createdTime: Long = System.currentTimeMillis(),
)
