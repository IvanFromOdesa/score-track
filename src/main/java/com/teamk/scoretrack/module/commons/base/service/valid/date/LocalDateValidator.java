package com.teamk.scoretrack.module.commons.base.service.valid.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

public class LocalDateValidator {
    private final DateTimeFormatter dtf;
    private final Predicate<LocalDate> additionalValidation;

    public LocalDateValidator(String dateFormat) {
        this.dtf = DateTimeFormatter.ofPattern(dateFormat);
        this.additionalValidation = ld -> false;
    }

    public LocalDateValidator(String dateFormat, Predicate<LocalDate> additionalValidation) {
        this.dtf = DateTimeFormatter.ofPattern(dateFormat);
        this.additionalValidation = additionalValidation;
    }

    public boolean isValid(String dateStr) {
        try {
            LocalDate parsed = LocalDate.parse(dateStr, dtf);
            if (additionalValidation.test(parsed)) {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public DateTimeFormatter getDtf() {
        return dtf;
    }
}
