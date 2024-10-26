package com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.projection;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;

/**
 * Every stat, besides fgp, ftp, tpp and plusMinus is calculated on per minute base.
 */
public class AvgStats implements IdAware<Integer> {
    private double points;
    private double fgm;
    private double fga;
    private double fgp;
    private double ftm;
    private double fta;
    private double ftp;
    private double tpm;
    private double tpa;
    private double tpp;
    private double offReb;
    private double defReb;
    private double totReb;
    private double assists;
    private double pFouls;
    private double steals;
    private double turnovers;
    private double blocks;
    private double plusMinus;

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getFgm() {
        return fgm;
    }

    public void setFgm(double fgm) {
        this.fgm = fgm;
    }

    public double getFga() {
        return fga;
    }

    public void setFga(double fga) {
        this.fga = fga;
    }

    public double getFgp() {
        return fgp;
    }

    public void setFgp(double fgp) {
        this.fgp = fgp;
    }

    public double getFtm() {
        return ftm;
    }

    public void setFtm(double ftm) {
        this.ftm = ftm;
    }

    public double getFta() {
        return fta;
    }

    public void setFta(double fta) {
        this.fta = fta;
    }

    public double getFtp() {
        return ftp;
    }

    public void setFtp(double ftp) {
        this.ftp = ftp;
    }

    public double getTpm() {
        return tpm;
    }

    public void setTpm(double tpm) {
        this.tpm = tpm;
    }

    public double getTpa() {
        return tpa;
    }

    public void setTpa(double tpa) {
        this.tpa = tpa;
    }

    public double getTpp() {
        return tpp;
    }

    public void setTpp(double tpp) {
        this.tpp = tpp;
    }

    public double getOffReb() {
        return offReb;
    }

    public void setOffReb(double offReb) {
        this.offReb = offReb;
    }

    public double getDefReb() {
        return defReb;
    }

    public void setDefReb(double defReb) {
        this.defReb = defReb;
    }

    public double getTotReb() {
        return totReb;
    }

    public void setTotReb(double totReb) {
        this.totReb = totReb;
    }

    public double getAssists() {
        return assists;
    }

    public void setAssists(double assists) {
        this.assists = assists;
    }

    public double getpFouls() {
        return pFouls;
    }

    public void setpFouls(double pFouls) {
        this.pFouls = pFouls;
    }

    public double getSteals() {
        return steals;
    }

    public void setSteals(double steals) {
        this.steals = steals;
    }

    public double getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(double turnovers) {
        this.turnovers = turnovers;
    }

    public double getBlocks() {
        return blocks;
    }

    public void setBlocks(double blocks) {
        this.blocks = blocks;
    }

    public double getPlusMinus() {
        return plusMinus;
    }

    public void setPlusMinus(double plusMinus) {
        this.plusMinus = plusMinus;
    }

    @Override
    public Integer getId() {
        return this.hashCode();
    }
}
