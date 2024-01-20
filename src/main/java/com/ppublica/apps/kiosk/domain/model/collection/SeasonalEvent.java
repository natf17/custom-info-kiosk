package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;

public interface SeasonalEvent {

    public LocalDate getStartDate();

    public DefaultEventSeason getEventSeason();
    
}
