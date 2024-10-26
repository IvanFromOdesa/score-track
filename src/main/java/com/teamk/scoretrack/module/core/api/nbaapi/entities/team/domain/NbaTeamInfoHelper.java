package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NbaTeamInfoHelper {
    ATLANTA_HAWKS("State_Farm_Arena", "ATL", "Atlanta_Hawks", createColors("#C8102E", "#FDB927", "#000000")),
    BOSTON_CELTICS("TD_Garden", "BOS", "Boston_Celtics", createColors("#007A33", "#BA9653", "#000000")),
    BROOKLYN_NETS("Barclays_Center", "BKN", "Brooklyn_Nets", createColors("#000000", "#FFFFFF", "#808080")),
    CHARLOTTE_HORNETS("Spectrum_Center", "CHA", "Charlotte_Hornets", createColors("#1D1160", "#00788C", "#A1A1A4")),
    CHICAGO_BULLS("United_Center", "CHI", "Chicago_Bulls", createColors("#CE1141", "#000000", "#FFFFFF")),
    CLEVELAND_CAVALIERS("Rocket_Mortgage_FieldHouse", "CLE", "Cleveland_Cavaliers", createColors("#6F263D", "#FFB81C", "#041E42")),
    DALLAS_MAVERICKS("American_Airlines_Center", "DAL", "Dallas_Mavericks", createColors("#00538C", "#002B5E", "#B8C4CA")),
    DENVER_NUGGETS("Ball_Arena", "DEN", "Denver_Nuggets", createColors("#0E2240", "#FEC524", "#8B2131")),
    DETROIT_PISTONS("Little_Caesars_Arena", "DET", "Detroit_Pistons", createColors("#C8102E", "#006BB6", "#BEC0C2")),
    GOLDEN_STATE_WARRIORS("Chase_Center", "GSW", "Golden_State_Warriors", createColors("#1D428A", "#FFC72C", "#26282A")),
    HOUSTON_ROCKETS("Toyota_Center", "HOU", "Houston_Rockets", createColors("#CE1141", "#000000", "#C4CED4")),
    INDIANA_PACERS("Bankers_Life_FieldHouse", "IND", "Indiana_Pacers", createColors("#002D62", "#FDBB30", "#BEC0C2")),
    LA_CLIPPERS("Crypto.com_Arena", "LAC", "Los_Angeles_Clippers", createColors("#C8102E", "#1D428A", "#BEC0C2")),
    LOS_ANGELES_LAKERS("Crypto.com_Arena", "LAL", "Los_Angeles_Lakers", createColors("#552583", "#FDB927", "#000000")),
    MEMPHIS_GRIZZLIES("FedExForum", "MEM", "Memphis_Grizzlies", createColors("#5D76A9", "#12173F", "#F5B112")),
    MIAMI_HEAT("American_Airlines_Arena", "MIA", "Miami_Heat", createColors("#98002E", "#F9A01B", "#000000")),
    MILWAUKEE_BUCKS("Fiserv_Forum", "MIL", "Milwaukee_Bucks", createColors("#00471B", "#EEE1C6", "#0077C0")),
    MINNESOTA_TIMBERWOLVES("Target_Center", "MIN", "Minnesota_Timberwolves", createColors("#0C2340", "#236192", "#9EA2A2")),
    NEW_ORLEANS_PELICANS("Smoothie_King_Center", "NOP", "New_Orleans_Pelicans", createColors("#0C2340", "#C8102E", "#85714D")),
    NEW_YORK_KNICKS("Madison_Square_Garden", "NYK", "New_York_Knicks", createColors("#006BB6", "#F58426", "#BEC0C2")),
    OKLAHOMA_CITY_THUNDER("Paycom_Center", "OKC", "Oklahoma_City_Thunder", createColors("#007AC1", "#EF3B24", "#FDBB30")),
    ORLANDO_MAGIC("Amway_Center", "ORL", "Orlando_Magic", createColors("#0077C0", "#C4CED4", "#000000")),
    PHILADELPHIA_76ERS("Wells_Fargo_Center", "PHI", "Philadelphia_76ers", createColors("#006BB6", "#ED174C", "#002B5C")),
    PHOENIX_SUNS("Footprint_Center", "PHX", "Phoenix_Suns", createColors("#E56020", "#1D1160", "#63727A")),
    PORTLAND_TRAIL_BLAZERS("Moda_Center", "POR", "Portland_Trail_Blazers", createColors("#E03A3E", "#000000", "#B6BFBF")),
    SACRAMENTO_KINGS("Golden_1_Center", "SAC", "Sacramento_Kings", createColors("#5A2D81", "#63727A", "#000000")),
    SAN_ANTONIO_SPURS("Frost_Bank_Center", "SAS", "San_Antonio_Spurs", createColors("#000000", "#C4CED4", "#BEC0C2")),
    TORONTO_RAPTORS("Scotiabank_Arena", "TOR", "Toronto_Raptors", createColors("#CE1141", "#000000", "#A1A1A4")),
    UTAH_JAZZ("Delta_Center", "UTA", "Utah_Jazz", createColors("#002B5C", "#00471B", "#F9A01B")),
    WASHINGTON_WIZARDS("Capital_One_Arena", "WAS", "Washington_Wizards", createColors("#002B5C", "#E31837", "#C4CED4")),
    UNDEFINED("", "", "", createColors("", "", ""));

    private final String arena;
    private final String arenaName;
    private final String code;
    private final String src;
    private final Colors colors;

    private static final String WIKI_SRC = "https://en.wikipedia.org/wiki/";

    private static final Map<String, NbaTeamInfoHelper> LOOKUP_MAP = new HashMap<>();

    static {
        Arrays.stream(NbaTeamInfoHelper.values()).forEach(i -> LOOKUP_MAP.put(i.code, i));
    }

    NbaTeamInfoHelper(String arena, String code, String src, Colors colors) {
        this.arena = "/api/nbaapi/arenas/" + arena + ".jpg";
        this.arenaName = arena.replaceAll("_", " ");
        this.code = code;
        this.src = WIKI_SRC + src;
        this.colors = colors;
    }

    public String getArena() {
        return arena;
    }

    public String getArenaName() {
        return arenaName;
    }

    public String getCode() {
        return code;
    }

    public String getSrc() {
        return src;
    }

    public Colors getColors() {
        return colors;
    }

    public static NbaTeamInfoHelper getByCode(String code) {
        return LOOKUP_MAP.getOrDefault(code, UNDEFINED);
    }

    private static Colors createColors(String primary, String secondary, String tertiary) {
        return new Colors(primary, secondary, tertiary);
    }

    public record Colors(String primary, String secondary, String tertiary) {}
}
