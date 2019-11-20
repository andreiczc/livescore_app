package com.example.livescore_app;

import java.net.URL;
import java.util.Date;

public class Match {
    private String competitionName;
    private String eventDate;
    private boolean finished;
    private String homeTeamName;
    private String awayTeamName;
    private int homeTeamGoals;
    private int awayTeamGoals;
    private URL homeTeamCrest;
    private URL awayTeamCrest;

    public Match(String competitionName, String eventDate, boolean finished, String homeTeamName, String awayTeamName, int homeTeamGoals, int awayTeamGoals, URL homeTeamCrest, URL awayTeamCrest) {
        this.competitionName = competitionName;
        this.eventDate = eventDate;
        this.finished = finished;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.homeTeamCrest = homeTeamCrest;
        this.awayTeamCrest = awayTeamCrest;
    }

    public Match(String competitionName, String eventDate, boolean finished, String homeTeamName, String awayTeamName, URL homeTeamCrest, URL awayTeamCrest) {
        this.competitionName = competitionName;
        this.eventDate = eventDate;
        this.finished = finished;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamCrest = homeTeamCrest;
        this.awayTeamCrest = awayTeamCrest;
    }

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

    public URL getHomeTeamCrest() {
        return homeTeamCrest;
    }

    public URL getAwayTeamCrest() {
        return awayTeamCrest;
    }
}
