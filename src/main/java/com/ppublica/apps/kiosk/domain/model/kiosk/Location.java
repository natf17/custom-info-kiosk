package com.ppublica.apps.kiosk.domain.model.kiosk;


public interface Location {
    KioskCollectionField<String> fullName();
    KioskCollectionField<Integer> level_num();
    KioskCollectionField<String> level_name();
    KioskCollectionField<KioskImage> map();
}
