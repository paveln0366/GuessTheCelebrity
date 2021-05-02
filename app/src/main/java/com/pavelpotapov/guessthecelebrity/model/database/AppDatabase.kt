package com.pavelpotapov.guessthecelebrity.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pavelpotapov.guessthecelebrity.model.database.dao.CelebrityDao
import com.pavelpotapov.guessthecelebrity.model.database.entity.Celebrity

@Database(entities = [Celebrity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "app.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it } // Выполнится если БД не равна null

                // Выполнится если БД равна null
                val instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun celebrityDao(): CelebrityDao
}