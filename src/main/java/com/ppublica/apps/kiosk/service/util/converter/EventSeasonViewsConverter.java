package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.EventSeasonType;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonAdminView;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonView;

@Component
public class EventSeasonViewsConverter {
    public EventSeasonView buildView(EventSeasonType eventSeason) {

        return new EventSeasonView(eventSeason.collectionId(), 
                                eventSeason.seasonType().fieldValue(), 
                                eventSeason.durationDays().fieldValue(),
                                eventSeason.theme().fieldValue(),
                                eventSeason.serviceYear().fieldValue(),
                                "",
                                eventSeason.durationText().fieldValue()
                                );
    }

    // expects at least one element in the list
    public EventSeasonAdminView buildAdminView(List<? extends EventSeasonType> eventSeasons) {
        EventSeasonType arbitraryEventSeason = eventSeasons.get(0);

        List<LocalizedField> themeLoc = new ArrayList<>();
        List<LocalizedField> durationTextLoc = new ArrayList<>();

        // process localizable fields
        for (EventSeasonType eventSeason : eventSeasons) {
            String locale = eventSeason.kioskCollectionMetadata().getCollectionInternals().getKioskLocale();

            String theme = eventSeason.theme().fieldValue();
            if(theme != null) {
                themeLoc.add(new LocalizedField(locale, theme));
            }

            String durationText = eventSeason.durationText().fieldValue();
            if(durationText != null) {
                durationTextLoc.add(new LocalizedField(locale, durationText));
            }
        }

        Long id = arbitraryEventSeason.collectionId();

        // process non-locale fields
        String seasonType = arbitraryEventSeason.seasonType().fieldValue();
        Integer durationDays = arbitraryEventSeason.durationDays().fieldValue();
        Integer serviceYear = arbitraryEventSeason.serviceYear().fieldValue();
        String seasonYears = "";

        return new EventSeasonAdminView(id, seasonType, durationDays, themeLoc, serviceYear, seasonYears, durationTextLoc);
    }
}
