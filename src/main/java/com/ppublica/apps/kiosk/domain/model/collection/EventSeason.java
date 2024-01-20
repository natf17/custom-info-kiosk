package com.ppublica.apps.kiosk.domain.model.collection;

import java.util.List;

public interface EventSeason {

    public Long getId();

    public KioskCollectionField<String> getKioskCollectionNameField();

    public KioskCollectionMetadata getKioskCollectionMetadata();

    public KioskCollectionField<Long> getSeasonYear();

    public KioskCollectionField<Long> getDurationDays();

    public KioskCollectionField<String> getTheme();

    public KioskCollectionField<String> getDurationText();

    public KioskCollectionField<String> getAddSeasonDatesText();

    public List<DefaultSeasonalEvent> getEvents();
    
}
