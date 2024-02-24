package com.ppublica.apps.kiosk.service.util;

public class SameTypeConverter<S> implements ValueConverter<S,S> {
    public S convert(S input) {
        return input;
    }
}
