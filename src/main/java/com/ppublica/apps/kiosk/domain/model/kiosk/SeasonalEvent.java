package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;

public interface SeasonalEvent {
    KioskCollectionField<LocalDate> startDate();
    KioskCollectionField<String> eventLanguage();
    KioskCollectionField<Long> seasonId();
}
