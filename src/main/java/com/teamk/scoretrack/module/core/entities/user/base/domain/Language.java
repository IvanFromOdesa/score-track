package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

public enum Language implements IEnumConvert<String, Language> {
    ENGLISH("0", "en", "English", "Please select the language", Locale.US, DateTimeFormatter.RFC_1123_DATE_TIME.withZone(Zones.FROM_UTC).withLocale(Locale.ENGLISH)),
    UKRAINIAN("1", "ukr", "Українська", "Будь ласка, оберіть мову", Locales.UKRAINE, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.ENGLISH).withZone(Zones.EET)),
    UNDEFINED(CODE_UNDEFINED_STR, "", "", "", null, null);

    public static final BiMap<String, Language> LOOKUP_MAP = EnumUtils.createLookup(Language.class);

    private final String code;
    private final String alias;
    private final String name;
    private final String imagePath;
    private final String helpText;
    private final Locale locale;
    private final DateTimeFormatter dtFormatter;

    Language(String code, String alias, String name, String helpText, Locale locale, DateTimeFormatter dtFormatter) {
        this.code = code;
        this.alias = alias;
        this.name = name;
        this.helpText = helpText;
        this.locale = locale;
        this.imagePath = locale != null ? "/lang-icons/" + locale.getLanguage().toLowerCase() + ".svg" : "";
        this.dtFormatter = dtFormatter;
    }

    public String getCode() {
        return code;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getHelpText() {
        return helpText;
    }

    public Locale getLocale() {
        return locale;
    }

    public DateTimeFormatter getDtFormatter() {
        return dtFormatter;
    }

    public static Language byIsoCode(String isoCode) {
        return LOOKUP_MAP.values().stream().filter(language -> {
            Locale locale = language.locale;
            return locale != null && locale.getCountry().equals(isoCode);
        }).findFirst().orElse(Language.UNDEFINED);
    }


    public static Language byAlias(String alias) {
        return LOOKUP_MAP.values().stream().filter(l -> l.getAlias().equals(alias)).findFirst().orElse(Language.UNDEFINED);
    }

    public static List<Language> supported() {
        return List.of(ENGLISH, UKRAINIAN);
    }

    public static Language byCode(String code) {
        return UNDEFINED.getByKey(code);
    }

    public boolean isValid() {
        return this != UNDEFINED;
    }

    public Language getValid() {
        return this == UNDEFINED ? ENGLISH : this;
    }

    static class Locales {
        private static final Locale UKRAINE = new Locale("ukr", "UA");
    }

    static class Zones {
        private static final ZoneId EET = ZoneId.of("Europe/Kiev");
        private static final ZoneId FROM_UTC = ZoneId.from(ZoneOffset.UTC);
    }

    @Override
    public BiMap<String, Language> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public Language getDefault() {
        return UNDEFINED;
    }

    @Override
    public String getKey() {
        return alias;
    }
}
