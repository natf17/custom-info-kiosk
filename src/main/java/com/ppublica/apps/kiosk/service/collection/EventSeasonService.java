package com.ppublica.apps.kiosk.service.collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ppublica.apps.kiosk.domain.model.collection.SeasonalEvent;
import com.ppublica.apps.kiosk.service.payloads.data.eventseason.EventSeasonInput;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonAdminView;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonView;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventView;

public class EventSeasonService {
    public List<EventSeasonView> getEventSeasons(String locale) {
        /*
        Optional<Page> errorPageOpt = repo.findByPageTypeAndKioskLocale(Error404Page.PAGE_TYPE, locale);
        
        if (errorPageOpt.isEmpty()) {
            return Optional.empty();
        }

        Error404Page error404Page = new Error404Page.Builder(errorPageOpt.get())
                                    .build();

        return Optional.of(transformToView(error404Page));
         */

         return null;

    }

    public EventSeasonAdminView createEventSeason(EventSeasonInput data) {
        throw new UnsupportedOperationException();
    }

    public Map<Long,EventSeasonView> getBatchEventSeasons(Set<Long> eventSeasonIds) {
        Map<Long,EventSeasonView> eventSeasonsMock = new HashMap<Long,EventSeasonView>();
        eventSeasonsMock.put(1L, new EventSeasonView(1L, "type", 3, "theme", 2023, "", "three days"));
        eventSeasonsMock.put(2L, new EventSeasonView(2L, "type", 4, "theme2", 2023, "", "four days"));

        return eventSeasonsMock;
    }
}
