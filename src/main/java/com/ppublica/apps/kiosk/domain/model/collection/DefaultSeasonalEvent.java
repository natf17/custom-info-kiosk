package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;

public class DefaultSeasonalEvent implements SeasonalEvent {
    private DefaultEventSeason season;
    private LocalDate startDate;

    public DefaultSeasonalEvent(LocalDate startDate) {
        this.startDate = startDate;
    }

    public DefaultSeasonalEvent(LocalDate startDate, DefaultEventSeason season) {
        this.startDate = startDate;
        this.season = season;
    }

    public void setEventSeason(DefaultEventSeason season) {
        this.season = season;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public DefaultEventSeason getEventSeason() {
        return this.season;
    }
}
