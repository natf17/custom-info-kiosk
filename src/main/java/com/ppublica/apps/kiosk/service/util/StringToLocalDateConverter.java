package com.ppublica.apps.kiosk.service.util;

import java.time.LocalDate;

public class StringToLocalDateConverter implements ValueConverter<String, LocalDate> {
    public LocalDate convert(String input) {
        return input != null ? LocalDate.parse(input): null;
    }
}
