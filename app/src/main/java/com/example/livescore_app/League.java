package com.example.livescore_app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class League {

    @PrimaryKey(autoGenerate = true)
    private int leagueId;

    private String leagueName;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }
}
