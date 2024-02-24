package com.ppublica.apps.kiosk.service.util;

interface ValueConverter<S,T> {
    public T convert(S input);
}
