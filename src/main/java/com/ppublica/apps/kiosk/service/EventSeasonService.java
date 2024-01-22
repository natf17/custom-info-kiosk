package com.ppublica.apps.kiosk.service;

import java.util.List;

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
}
