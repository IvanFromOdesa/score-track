package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.StatsDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APINbaPlayerStatsDto extends StatsDto<String> {
    public GameId game;
    public TeamId team;
    public PlayerId player;
    public String pos;
    public String min;

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

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public record GameId(long id) {}

    public record TeamId(long id, String name, String nickname, String code, String logo) {}

    public record PlayerId(long id, String firstname, String lastname) {}
}
