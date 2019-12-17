package com.example.livescore_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DbDao {

    @Query("select* from 'matches'")
    List<Match> getMatches();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMatch(Match match);

    @Delete
    void deleteMatch(Match match);

    @Query("delete from 'matches'")
    void deleteAllMatches();



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertLeague(League league);

    @Query("select* from 'leagues'")
    List<League> getLeagues();

    @Delete
    void deleteLeague(League league);

    @Query("delete from 'leagues'")
    void deleteAllLeagues();

    @Query("select* from matches where leagueName LIKE :leagueName")
    List<Match> getAllMatchesForLeague(String leagueName);
}
