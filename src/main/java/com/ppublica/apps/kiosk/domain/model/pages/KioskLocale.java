package com.ppublica.apps.kiosk.domain.model.pages;

public enum KioskLocale {
    EN("EN");

    private String localeAbbrev;

    KioskLocale(String localeAbbrev) {
        this.localeAbbrev = localeAbbrev;
    }

    // todo: what happens if locale not found?
    // use something beside an enum? we don't want to hardcode languages
    static KioskLocale from(String localeAbbrev) {
        return KioskLocale.valueOf(localeAbbrev.toUpperCase());
    }


}
