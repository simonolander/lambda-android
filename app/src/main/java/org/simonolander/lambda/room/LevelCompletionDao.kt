package org.simonolander.lambda.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LevelCompletionDao {
    @Query("SELECT * FROM levelcompletion")
    fun getAll(): LiveData<List<LevelCompletion>>

    @Insert
    fun insertAll(vararg levelCompletions: LevelCompletion)

    @Delete
    fun delete(levelCompletion: LevelCompletion)
}
