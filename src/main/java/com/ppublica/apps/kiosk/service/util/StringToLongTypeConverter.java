package com.ppublica.apps.kiosk.service.util;

public class StringToLongTypeConverter implements ValueConverter<String,Long> {
    public Long convert(String input) {
        return input != null ? Long.parseLong(input) : null;
    }
}
