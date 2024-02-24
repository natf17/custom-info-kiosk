package com.ppublica.apps.kiosk.domain.model.kiosk;

public record KioskImage(String location, Integer width, Integer height) {
    public KioskImage() {
        this(null, null, null);
    }
   
}
