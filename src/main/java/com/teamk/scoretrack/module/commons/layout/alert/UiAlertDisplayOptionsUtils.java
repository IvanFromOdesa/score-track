package com.teamk.scoretrack.module.commons.layout.alert;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;
import java.util.function.BiConsumer;

import static com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptions.ATTRIBUTE_NAME;

public class UiAlertDisplayOptionsUtils {

    public static void addToHttpSession(HttpSession session, BiConsumer<UiAlertDisplayOptions, Boolean> setter) {
        UiAlertDisplayOptions attribute = getAttribute(session);
        if (attribute != null) {
            setter.accept(attribute, true);
        } else {
            UiAlertDisplayOptions displayOptions = new UiAlertDisplayOptions();
            setter.accept(displayOptions, true);
            session.setAttribute(ATTRIBUTE_NAME, displayOptions);
        }
    }

    public static Optional<UiAlertDisplayOptions> getAlertDisplayOptions(HttpSession session) {
        return Optional.ofNullable(getAttribute(session));
    }

    private static UiAlertDisplayOptions getAttribute(HttpSession session) {
        return (UiAlertDisplayOptions) session.getAttribute(ATTRIBUTE_NAME);
    }
}
