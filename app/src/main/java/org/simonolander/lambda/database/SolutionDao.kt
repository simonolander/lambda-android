package org.simonolander.lambda.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SolutionDao {
    @Query("select * from solution")
    fun getAll(): Flow<List<Solution>>

    @Insert
    suspend fun insert(solution: Solution)
}
