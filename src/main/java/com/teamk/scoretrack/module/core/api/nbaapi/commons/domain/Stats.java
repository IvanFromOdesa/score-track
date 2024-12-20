package com.teamk.scoretrack.module.core.api.nbaapi.commons.domain;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;

import java.util.List;
import java.util.UUID;

public class Stats implements IdAware<UUID> {
    private UUID uuid;
    private int points;
    private int fgm;
    private int fga;
    private double fgp;
    private int ftm;
    private int fta;
    private double ftp;
    private int tpm;
    private int tpa;
    private double tpp;
    private int offReb;
    private int defReb;
    private int totReb;
    private int assists;
    private int pFouls;
    private int steals;
    private int turnovers;
    private int blocks;
    private int plusMinus;

    public Stats() {
        uuid = UUID.randomUUID();
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getFgm() {
        return fgm;
    }

    public void setFgm(int fgm) {
        this.fgm = fgm;
    }

    public int getFga() {
        return fga;
    }

    public void setFga(int fga) {
        this.fga = fga;
    }

    public double getFgp() {
        return fgp;
    }

    public void setFgp(double fgp) {
        this.fgp = fgp;
    }

    public int getFtm() {
        return ftm;
    }

    public void setFtm(int ftm) {
        this.ftm = ftm;
    }

    public int getFta() {
        return fta;
    }

    public void setFta(int fta) {
        this.fta = fta;
    }

    public double getFtp() {
        return ftp;
    }

    public void setFtp(double ftp) {
        this.ftp = ftp;
    }

    public int getTpm() {
        return tpm;
    }

    public void setTpm(int tpm) {
        this.tpm = tpm;
    }

    public int getTpa() {
        return tpa;
    }

    public void setTpa(int tpa) {
        this.tpa = tpa;
    }

    public double getTpp() {
        return tpp;
    }

    public void setTpp(double tpp) {
        this.tpp = tpp;
    }

    public int getOffReb() {
        return offReb;
    }

    public void setOffReb(int offReb) {
        this.offReb = offReb;
    }

    public int getDefReb() {
        return defReb;
    }

    public void setDefReb(int defReb) {
        this.defReb = defReb;
    }

    public int getTotReb() {
        return totReb;
    }

    public void setTotReb(int totReb) {
        this.totReb = totReb;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getpFouls() {
        return pFouls;
    }

    public void setpFouls(int pFouls) {
        this.pFouls = pFouls;
    }

    public int getSteals() {
        return steals;
    }

    public void setSteals(int steals) {
        this.steals = steals;
    }

    public int getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(int turnovers) {
        this.turnovers = turnovers;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public int getPlusMinus() {
        return plusMinus;
    }

    public void setPlusMinus(int plusMinus) {
        this.plusMinus = plusMinus;
    }

    public static List<String> getFieldNames() {
        return List.of(
                "points",
                "fgm",
                "fga",
                "fgp",
                "ftm",
                "fta",
                "ftp",
                "tpm",
                "tpa",
                "tpp",
                "offReb",
                "defReb",
                "totReb",
                "assists",
                "pFouls",
                "steals",
                "turnovers",
                "blocks",
                "plusMinus"
        );
    }
}
