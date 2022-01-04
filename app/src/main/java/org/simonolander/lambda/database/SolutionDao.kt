package org.simonolander.lambda.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SolutionDao {
    @Query("select * from solution")
    fun getAll(): List<Solution>
}
