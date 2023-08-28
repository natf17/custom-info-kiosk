package com.ppublica.apps.kiosk.domain.model.util;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class KioskLocale {
    @Id
    private Long id;   
    
    private String abbrev;
    private String name;

    @PersistenceCreator
    public KioskLocale(String abbrev, String name) {
        this.abbrev = abbrev;
        this.name = name;
    }

    KioskLocale(String abbrev) {
        this(abbrev, null);
    }

    // returns a KioskLocale that might or might not exist
    static KioskLocale from(String localeAbbrev) {
        return new KioskLocale(localeAbbrev.toUpperCase());
    }


}
