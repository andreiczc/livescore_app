package com.example.livescore_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.net.URL;
import java.util.Date;

@Entity(tableName = "matches", foreignKeys = @ForeignKey(entity = League.class, parentColumns = "leagueName", childColumns = "leagueName", onDelete = ForeignKey.CASCADE))
public class Match {

    @PrimaryKey(autoGenerate = true)
    private int matchId;

    private String competitionName;

    private String eventDate;

    private boolean finished;

    private String homeTeamName;

    private String awayTeamName;

    private int homeTeamGoals;

    private int awayTeamGoals;

    private String homeTeamCrest;

    private String awayTeamCrest;

    private String leagueName;

    @Ignore
    public Match() {

    }

    public Match(String competitionName, String eventDate, boolean finished, String homeTeamName,
                 String awayTeamName, int homeTeamGoals, int awayTeamGoals, String homeTeamCrest, String awayTeamCrest) {
        this.competitionName = competitionName;
        this.eventDate = eventDate;
        this.finished = finished;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.homeTeamCrest = homeTeamCrest;
        this.awayTeamCrest = awayTeamCrest;

        leagueName = competitionName;
    }

    @Ignore
    public Match(String competitionName, String eventDate, boolean finished, String homeTeamName, String awayTeamName, String homeTeamCrest, String awayTeamCrest) {
        this.competitionName = competitionName;
        this.eventDate = eventDate;
        this.finished = finished;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamCrest = homeTeamCrest;
        this.awayTeamCrest = awayTeamCrest;

        leagueName = competitionName;
    }

    @Ignore
    @Override
    public String toString() {
        return "Match{" +
                "competitionName='" + competitionName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", finished=" + finished +
                ", homeTeamName='" + homeTeamName + '\'' +
                ", awayTeamName='" + awayTeamName + '\'' +
                ", homeTeamGoals=" + homeTeamGoals +
                ", awayTeamGoals=" + awayTeamGoals +
                ", homeTeamCrest=" + homeTeamCrest +
                ", awayTeamCrest=" + awayTeamCrest +
                '}';
    }

    @Ignore
    public String toStringR() {
        String res = "Match: \n" +
                "Competition name: " + competitionName + '\n' +
                "Event date: " + eventDate + '\n' +
                "Finished: " + finished + '\n' +
                "Home team name: " + homeTeamName + '\n' +
                "Away team name: " + awayTeamName + '\n';

        if (isFinished())
            res += "Home team goals: " + homeTeamGoals + '\n' +
                    "Away team goals: " + awayTeamGoals + '\n';

        return res;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public int getMatchId() {
        return matchId;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public String getHomeTeamCrest() {
        return homeTeamCrest;
    }

    public String getAwayTeamCrest() {
        return awayTeamCrest;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public void setHomeTeamCrest(String homeTeamCrest) {
        this.homeTeamCrest = homeTeamCrest;
    }

    public void setAwayTeamCrest(String awayTeamCrest) {
        this.awayTeamCrest = awayTeamCrest;
    }
}
