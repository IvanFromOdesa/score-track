package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain;

public enum PlayerDataStatCategoryInfoHelper {
    EFFICIENCY("efficiency", "highlight-text"),
    PPG("points"),
    APG("assists"),
    BPG("blocks"),
    SPG("steals"),
    FGP("fgp"),
    FGM("fgm"),
    TPP("tpp"),
    TPM("tpm");

    private final String statName;
    private final String uiHintDropdownTitleCode;
    private final String uiHintTitleCode;
    private final String uiHintDescriptionCode;
    private final String uiHintClassName;

    PlayerDataStatCategoryInfoHelper(String statName) {
        this.statName = statName;
        this.uiHintDropdownTitleCode = statName + "DropdownTitle";
        this.uiHintTitleCode = statName + "Title";
        this.uiHintDescriptionCode = statName + "Description";
        this.uiHintClassName = null;
    }

    PlayerDataStatCategoryInfoHelper(String statName, String uiHintClassName) {
        this.statName = statName;
        this.uiHintDropdownTitleCode = statName + "DropdownTitle";
        this.uiHintTitleCode = statName + "Title";
        this.uiHintDescriptionCode = statName + "Description";
        this.uiHintClassName = uiHintClassName;
    }

    public String getStatName() {
        return statName;
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

    public static PlayerDataStatCategoryInfoHelper byFieldName(String fieldName) {
        for (PlayerDataStatCategoryInfoHelper type: PlayerDataStatCategoryInfoHelper.values()) {
            if (type.statName.equals(fieldName)) {
                return type;
            }
        }
        return EFFICIENCY;
    }
}
