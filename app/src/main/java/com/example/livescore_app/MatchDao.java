package com.example.livescore_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MatchDao {

    @Query("select* from matches")
    List<Match> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMatch(Match match);

    @Delete
    void deleteMatch(Match match);

    @Query("delete from matches")
    void deleteAllMatches();
}
