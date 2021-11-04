package org.simonolander.lambda.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LevelCompletion::class], version = 1)
abstract class LambdaDatabase : RoomDatabase() {
    abstract fun levelCompletionDao(): LevelCompletionDao

    companion object {
        private var instance: LambdaDatabase? = null

        fun getInstance(context: Context): LambdaDatabase {
            val instance = this.instance
                ?: Room.databaseBuilder(
                    context.applicationContext,
                    LambdaDatabase::class.java,
                    "lambda-database"
                ).build()
            this.instance = instance
            return instance
        }
    }
}
