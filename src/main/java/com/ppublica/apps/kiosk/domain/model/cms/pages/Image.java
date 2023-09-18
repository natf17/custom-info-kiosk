package com.ppublica.apps.kiosk.domain.model.cms.pages;

public record Image(String location, Integer width, Integer height) {
    public Image() {
        this(null, null, null);
    }
   
}
