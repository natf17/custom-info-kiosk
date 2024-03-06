package com.ppublica.apps.kiosk.domain.model.kiosk;

public interface SeasonalEvent {
    KioskCollectionField<String> seasonType();
    KioskCollectionField<Long> startDate();
    KioskCollectionField<String> eventLanguage();
    KioskCollectionField<Long> seasonId();
}
