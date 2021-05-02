package com.pavelpotapov.guessthecelebrity.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pavelpotapov.guessthecelebrity.model.database.entity.Celebrity

@Dao
interface CelebrityDao {
    @Query("SELECT * FROM table_celebrity ORDER BY name")
    fun getAllCelebrity(): List<Celebrity>

    @Query("SELECT * FROM table_celebrity WHERE position = :celebrityId")
    fun getCelebrity(celebrityId: Int): Celebrity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCelebrityListToDatabase(celebrityList: List<Celebrity>)
}