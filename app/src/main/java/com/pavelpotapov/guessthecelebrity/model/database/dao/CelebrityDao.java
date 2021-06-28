package com.pavelpotapov.guessthecelebrity.model.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pavelpotapov.guessthecelebrity.entity.Celebrity;

import java.util.List;

@Dao
public interface CelebrityDao {
    @Query("SELECT * FROM table_celebrity ORDER BY name")
    List<Celebrity> getAllCelebrity();

    @Query("SELECT * FROM table_celebrity WHERE position = :celebrityId")
    Celebrity getCelebrity(int celebrityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCelebrityListToDatabase(List<Celebrity> celebrityList);
}
