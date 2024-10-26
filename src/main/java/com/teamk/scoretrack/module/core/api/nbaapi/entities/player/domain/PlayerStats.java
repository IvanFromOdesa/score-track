package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.Stats;

@JsonIgnoreProperties("id")
public class PlayerStats extends Stats {
    private GameId game;
    private TeamId team;
    private PlayerId player;
    private PlayerGamePosition position;
    private double minutes;

    public GameId getGame() {
        return game;
    }

    public void setGame(GameId game) {
        this.game = game;
    }

    public TeamId getTeam() {
        return team;
    }

    public void setTeam(TeamId team) {
        this.team = team;
    }

    public PlayerId getPlayer() {
        return player;
    }

    public void setPlayer(PlayerId player) {
        this.player = player;
    }

    public PlayerGamePosition getPosition() {
        return position;
    }

    public void setPosition(PlayerGamePosition position) {
        this.position = position;
    }

    public double getMinutes() {
        return minutes;
    }

    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }

    public record GameId(long id) {}

    public record TeamId(String id, String name, String nickname, String code, String logo) {}

    public record PlayerId(String id, String firstName, String lastName) {}
}
