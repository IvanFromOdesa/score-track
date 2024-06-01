package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NbaTeamInfoHelper {
    ATLANTA_HAWKS("State_Farm_Arena", "ATL", "Atlanta_Hawks", "#C8102E"),
    BOSTON_CELTICS("TD_Garden", "BOS", "Boston_Celtics", "#007A33"),
    BROOKLYN_NETS("Barclays_Center", "BKN", "Brooklyn_Nets", "#000000"),
    CHARLOTTE_HORNETS("Spectrum_Center", "CHA", "Charlotte_Hornets", "#1D1160"),
    CHICAGO_BULLS("United_Center", "CHI", "Chicago_Bulls", "#CE1141"),
    CLEVELAND_CAVALIERS("Rocket_Mortgage_FieldHouse", "CLE", "Cleveland_Cavaliers", "#FDBB30"),
    DALLAS_MAVERICKS("American_Airlines_Center", "DAL", "Dallas_Mavericks", "#00538C"),
    DENVER_NUGGETS("Ball_Arena", "DEN", "Denver_Nuggets", "#0E2240"),
    DETROIT_PISTONS("Little_Caesars_Arena", "DET", "Detroit_Pistons", "#C8102E"),
    GOLDEN_STATE_WARRIORS("Chase_Center", "GSW", "Golden_State_Warriors", "#1D428A"),
    HOUSTON_ROCKETS("Toyota_Center", "HOU", "Houston_Rockets", "#CE1141"),
    INDIANA_PACERS("Bankers_Life_FieldHouse", "IND", "Indiana_Pacers", "#002D62"),
    LA_CLIPPERS("Crypto.com_Arena", "LAC", "Los_Angeles_Clippers", "#C8102E"),
    LOS_ANGELES_LAKERS("Crypto.com_Arena", "LAL", "Los_Angeles_Lakers", "#552583"),
    MEMPHIS_GRIZZLIES("FedExForum", "MEM", "Memphis_Grizzlies", "#5D76A9"),
    MIAMI_HEAT("American_Airlines_Arena", "MIA", "Miami_Heat", "#98002E"),
    MILWAUKEE_BUCKS("Fiserv_Forum", "MIL", "Milwaukee_Bucks", "#00471B"),
    MINNESOTA_TIMBERWOLVES("Target_Center", "MIN", "Minnesota_Timberwolves", "#0C2340"),
    NEW_ORLEANS_PELICANS("Smoothie_King_Center", "NOP", "New_Orleans_Pelicans", "#0A2240"),
    NEW_YORK_KNICKS("Madison_Square_Garden", "NYK", "New_York_Knicks", "#006BB6"),
    OKLAHOMA_CITY_THUNDER("Paycom_Center", "OKC", "Oklahoma_City_Thunder", "#007AC1"),
    ORLANDO_MAGIC("Amway_Center", "ORL", "Orlando_Magic", "#0077C0"),
    PHILADELPHIA_76ERS("Wells_Fargo_Center", "PHI", "Philadelphia_76ers", "#006BB6"),
    PHOENIX_SUNS("Footprint_Center", "PHX", "Phoenix_Suns", "#E56020"),
    PORTLAND_TRAIL_BLAZERS("Moda_Center", "POR", "Portland_Trail_Blazers", "#E03A3E"),
    SACRAMENTO_KINGS("Golden_1_Center", "SAC", "Sacramento_Kings", "#5A2D81"),
    SAN_ANTONIO_SPURS("Frost_Bank_Center", "SAS", "San_Antonio_Spurs", "#000000"),
    TORONTO_RAPTORS("Scotiabank_Arena", "TOR", "Toronto_Raptors", "#CE1141"),
    UTAH_JAZZ("Delta_Center", "UTA", "Utah_Jazz", "#002B5C"),
    WASHINGTON_WIZARDS("Capital_One_Arena", "WAS", "Washington_Wizards", "#002B5C"),
    UNDEFINED("", "", "", "");

    private final String arena;
    private final String arenaName;
    private final String code;
    private final String src;
    private final String color;

    private static final String WIKI_SRC = "https://en.wikipedia.org/wiki/";

    private static final Map<String, NbaTeamInfoHelper> LOOKUP_MAP = new HashMap<>();

    static {
        Arrays.stream(NbaTeamInfoHelper.values()).forEach(i -> LOOKUP_MAP.put(i.code, i));
    }

    NbaTeamInfoHelper(String arena, String code, String src, String color) {
        this.arena = "/api/nbaapi/arenas/" + arena + ".jpg";
        this.arenaName = arena.replaceAll("_", " ");
        this.code = code;
        this.src = WIKI_SRC + src;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public static NbaTeamInfoHelper getByCode(String code) {
        return LOOKUP_MAP.getOrDefault(code, UNDEFINED);
    }
}
