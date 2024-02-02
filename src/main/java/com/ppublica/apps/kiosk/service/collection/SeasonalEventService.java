package com.ppublica.apps.kiosk.service.collection;

import java.util.List;
import java.util.Optional;

import com.ppublica.apps.kiosk.service.payloads.data.seasonalevent.SeasonalEventInput;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventView;

public class SeasonalEventService {

    public List<SeasonalEventView> getSeasonalEvents(String locale, String sort) {
        throw new UnsupportedOperationException();
    }

    public Optional<SeasonalEventView> getSeasonalEvent(Long seasonalEventId, String seasonLocale) {
        throw new UnsupportedOperationException();
    }

    public List<SeasonalEventView> createSeasonalEventsBatch(Long eventSeasonId, List<SeasonalEventInput> data) {
        throw new UnsupportedOperationException();
    }

    public SeasonalEventView updateSeasonalEvent(Long seasonalEventId, SeasonalEventInput data) {
        throw new UnsupportedOperationException();
    }

    public void deleteSeasonalEvent(Long seasonalEventId) {
        throw new UnsupportedOperationException();
    }
    
}
