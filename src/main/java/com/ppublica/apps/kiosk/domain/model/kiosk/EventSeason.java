package com.ppublica.apps.kiosk.domain.model.kiosk;

public interface EventSeason {
    KioskCollectionField<String> seasonType();
    KioskCollectionField<Integer> durationDays();
    KioskCollectionField<String> theme();
    KioskCollectionField<Integer> serviceYear();
    KioskCollectionField<String> durationText();
}
