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
import java.util.function.Predicate;

public enum Language implements IEnumConvert<String, Language> {
    ENGLISH("0", "en-US", "English", "Please select the language", Locale.US,
            getEnglishDateTimeFormatter(),
            DateTimeFormatter.ofPattern("MM/dd/yyyy")),
    UKRAINIAN("1", "ukr", "Українська", "Будь ласка, оберіть мову", Locales.UKRAINE,
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(Zones.EET).withLocale(Locale.ENGLISH),
            DateTimeFormatter.ISO_LOCAL_DATE),
    DEFAULT("999", "en", "English", "Please select the language", Locale.ENGLISH,
            getEnglishDateTimeFormatter(),
            DateTimeFormatter.ISO_LOCAL_DATE),
    UNDEFINED(CODE_UNDEFINED_STR, "", "", "", null, null, null);

    public static final BiMap<String, Language> LOOKUP_MAP = EnumUtils.createLookup(Language.class);

    private final String code;
    private final String alias;
    private final String name;
    private final String imagePath;
    private final String helpText;
    private final Locale locale;
    /**
     * DateTime with a zone offset (Instant, OffsetDateTime, ZonedDateTime)
     */
    private final DateTimeFormatter dtFormatter;
    /**
     * Date without a zone offset (LocalDate)
     */
    private final DateTimeFormatter dateFormatter;

    Language(String code, String alias, String name, String helpText, Locale locale,
             DateTimeFormatter dtFormatter, DateTimeFormatter dateFormatter) {
        this.code = code;
        this.alias = alias;
        this.name = name;
        this.helpText = helpText;
        this.locale = locale;
        this.imagePath = locale != null ? "/lang-icons/" + locale.getLanguage().toLowerCase() + ".svg" : "";
        this.dtFormatter = dtFormatter;
        this.dateFormatter = dateFormatter;
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

    public DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }

    public static Language byIsoCode(String isoCode) {
        return getLanguage(l -> {
            Locale locale = l.locale;
            return locale != null && locale.getCountry().equals(isoCode);
        });
    }

    public static Language byLocale(Locale locale) {
        return getLanguage(l -> l.locale != null && l.locale.equals(locale));
    }

    public static Language byAlias(String alias) {
        return getLanguage(l -> l.getAlias().equals(alias));
    }

    private static Language getLanguage(Predicate<Language> p) {
        return LOOKUP_MAP.values().stream().filter(p).findFirst().orElse(Language.UNDEFINED);
    }

    // FIXME: calling /preferences/lang/en-US results in reCAPTCHA error:
    //  Invalid action name, may only include "A-Za-z/_". Do not include user-specific information.
    public static List<Language> supported() {
        return List.of(DEFAULT, UKRAINIAN);
    }

    public static Language byCode(String code) {
        return UNDEFINED.getByKey(code);
    }

    public boolean isValid() {
        return this != UNDEFINED;
    }

    public Language getValid() {
        return this == UNDEFINED ? DEFAULT : this;
    }

    private static DateTimeFormatter getEnglishDateTimeFormatter() {
        return DateTimeFormatter.RFC_1123_DATE_TIME.withZone(Zones.FROM_UTC).withLocale(Locale.ENGLISH);
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
