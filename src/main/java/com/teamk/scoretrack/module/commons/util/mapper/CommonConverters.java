package com.teamk.scoretrack.module.commons.util.mapper;

import org.modelmapper.Converter;

public class CommonConverters {
    public static Converter<String, Integer> stringToInt() {
        return ctx -> {
            try {
                return Integer.parseInt(ctx.getSource());
            } catch (NumberFormatException e) {
                return 0;
            }
        };
    }

    public static Converter<Integer, String> intToString() {
        return ctx -> String.valueOf(ctx.getSource());
    }
}
