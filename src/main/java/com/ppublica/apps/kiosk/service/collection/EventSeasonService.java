package com.ppublica.apps.kiosk.service.collection;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.data.eventseason.EventSeasonInput;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonAdminView;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonView;

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
}
