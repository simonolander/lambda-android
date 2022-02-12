package org.simonolander.lambda.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.simonolander.lambda.domain.EventType

@Dao
interface EventDao {
    @Query("select exists(select * from event where type = :type)")
    fun hasEvent(type: EventType): Flow<Boolean>

    @Insert
    suspend fun insert(event: Event)
}
