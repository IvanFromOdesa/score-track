package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum PlayerDataStatCategoryInfoHelper {
    EFFICIENCY("efficiency", "efficiency", "highlight-text", Type.SPECIAL),
    POINTS("points", Type.MAIN),
    PPG("ppg", "points", Type.PER),
    ASSISTS("assists", Type.MAIN),
    APG("apg", "assists", Type.PER),
    BLOCKS("blocks", Type.MAIN),
    BPG("bpg", "blocks", Type.PER),
    STEALS("steals", Type.MAIN),
    SPG("spg", "steals", Type.PER),
    FGP("fgp", Type.PERCENTAGE),
    FGM("fgm", Type.FIELD_GOALS),
    FGA("fga", Type.FIELD_GOALS),
    TPP("tpp", Type.PERCENTAGE),
    TPM("tpm", Type.THREE_POINTS),
    TPA("tpa", Type.THREE_POINTS),
    FTM("ftm", Type.FREE_THROWS),
    FTP("ftp", Type.PERCENTAGE),
    FTA("fta", Type.FREE_THROWS),
    OFF_REB("offReb", Type.REBOUNDS),
    DEF_REB("defReb", Type.REBOUNDS),
    TOT_REB("totReb", Type.REBOUNDS),
    P_FOULS("pFouls", Type.MISCELLANEOUS),
    TURNOVERS("turnovers", Type.MISCELLANEOUS),
    PLUS_MINUS("plusMinus", Type.SPECIAL);

    private final String categoryName;
    private final String fieldName;
    private final String uiHintDropdownTitleCode;
    private final String uiHintTitleCode;
    private final String uiHintDescriptionCode;
    private final String uiHintClassName;
    private final Type type;

    PlayerDataStatCategoryInfoHelper(String categoryName, Type type) {
        this(categoryName, categoryName, type);
    }

    PlayerDataStatCategoryInfoHelper(String categoryName, String fieldName, Type type) {
        this(categoryName, fieldName, null, type);
    }

    PlayerDataStatCategoryInfoHelper(String categoryName, String fieldName, String uiHintClassName, Type type) {
        this.categoryName = categoryName;
        this.fieldName = fieldName;
        this.uiHintDropdownTitleCode = categoryName + "DropdownTitle";
        this.uiHintTitleCode = categoryName + "Title";
        this.uiHintDescriptionCode = categoryName + "Description";
        this.uiHintClassName = uiHintClassName;
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getUiHintDropdownTitleCode() {
        return uiHintDropdownTitleCode;
    }

    public String getUiHintTitleCode() {
        return uiHintTitleCode;
    }

    public String getUiHintDescriptionCode() {
        return uiHintDescriptionCode;
    }

    public String getUiHintClassName() {
        return uiHintClassName;
    }

    public boolean isEfficiency() {
        return this == EFFICIENCY;
    }

    public boolean isLeaderboardCategory() {
        return Arrays.asList(getLeaderboardCategories()).contains(this);
    }

    public static PlayerDataStatCategoryInfoHelper[] getLeaderboardCategories() {
        return new PlayerDataStatCategoryInfoHelper[] {
                EFFICIENCY,
                PPG,
                APG,
                BPG,
                SPG,
                FGP,
                FGM,
                TPP,
                TPM
        };
    }

    public static Map<Type, PlayerDataStatCategoryInfoHelper[]> getGroupedStatBoardCategories() {
        final List<Type> types = List.of(
                Type.MAIN,
                Type.REBOUNDS,
                Type.MISCELLANEOUS,
                Type.FIELD_GOALS,
                Type.FREE_THROWS,
                Type.THREE_POINTS
        );
        return Arrays.stream(PlayerDataStatCategoryInfoHelper.values())
                .filter(c -> types.contains(c.type))
                .collect(Collectors.groupingBy(
                        c -> c.type,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.toArray(new PlayerDataStatCategoryInfoHelper[0])
                        ))
                );
    }

    public static PlayerDataStatCategoryInfoHelper byCategoryName(String categoryName) {
        for (PlayerDataStatCategoryInfoHelper type: PlayerDataStatCategoryInfoHelper.values()) {
            if (type.categoryName.equals(categoryName)) {
                return type;
            }
        }
        return EFFICIENCY;
    }

    public enum Type {
        MAIN("main"),
        REBOUNDS("rebounds"),
        MISCELLANEOUS("miscellaneous"),
        FIELD_GOALS("fieldGoals"),
        FREE_THROWS("freeThrows"),
        THREE_POINTS("threePoints"),
        PER("per"),
        PERCENTAGE("percentage"),
        SPECIAL("special");

        private final String name;
        private final String uiHintTitleCode;

        Type(String name) {
            this.name = name;
            this.uiHintTitleCode = name + "GroupTitle";
        }

        public String getName() {
            return name;
        }

        public String getUiHintTitleCode() {
            return uiHintTitleCode;
        }
    }
}
