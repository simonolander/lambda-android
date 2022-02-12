package org.simonolander.lambda.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Solution::class, Event::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
)
abstract class LambdaDatabase : RoomDatabase() {
    abstract fun solutionDao(): SolutionDao
    abstract fun eventDao(): EventDao

    companion object {
        private var instance: LambdaDatabase? = null

        @Synchronized
        fun getInstance(context: Context): LambdaDatabase {
            val old = instance
            if (old != null) {
                return old
            }
            val new = Room
                .databaseBuilder(context, LambdaDatabase::class.java, "Lambda")
                .build()
            instance = new
            return new
        }
    }
}
