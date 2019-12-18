package com.example.livescore_app;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "leagues")
public class League {

    @NonNull
    @PrimaryKey
    public String leagueName;

    @Ignore
    List<Match> matches;

    public League(@NonNull String leagueName) {
        this.leagueName = leagueName;

        matches = MainActivity.database.dbDao().getAllMatchesForLeague(leagueName);
    }
}
