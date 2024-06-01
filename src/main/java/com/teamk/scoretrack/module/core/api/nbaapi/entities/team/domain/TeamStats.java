package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.Stats;

@JsonIgnoreProperties("id")
public class TeamStats extends Stats {
    private int games;
    private int fastBreakPoints;
    private int pointsInPaint;
    private int biggestLead;
    private int secondChancePoints;
    private int pointsOffTurnovers;
    private int longestRun;

    public TeamStats() {
        super();
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getFastBreakPoints() {
        return fastBreakPoints;
    }

    public void setFastBreakPoints(int fastBreakPoints) {
        this.fastBreakPoints = fastBreakPoints;
    }

    public int getPointsInPaint() {
        return pointsInPaint;
    }

    public void setPointsInPaint(int pointsInPaint) {
        this.pointsInPaint = pointsInPaint;
    }

    public int getBiggestLead() {
        return biggestLead;
    }

    public void setBiggestLead(int biggestLead) {
        this.biggestLead = biggestLead;
    }

    public int getSecondChancePoints() {
        return secondChancePoints;
    }

    public void setSecondChancePoints(int secondChancePoints) {
        this.secondChancePoints = secondChancePoints;
    }

    public int getPointsOffTurnovers() {
        return pointsOffTurnovers;
    }

    public void setPointsOffTurnovers(int pointsOffTurnovers) {
        this.pointsOffTurnovers = pointsOffTurnovers;
    }

    public int getLongestRun() {
        return longestRun;
    }

    public void setLongestRun(int longestRun) {
        this.longestRun = longestRun;
    }
}
