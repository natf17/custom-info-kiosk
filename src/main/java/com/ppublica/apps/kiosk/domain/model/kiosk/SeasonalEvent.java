package com.ppublica.apps.kiosk.domain.model.kiosk;

public interface SeasonalEvent {
    KioskCollectionField<String> seasonalType();
    KioskCollectionField<Long> startDate();
    KioskCollectionField<String> eventLanguage();
    KioskCollectionField<Long> seasonId();
}
