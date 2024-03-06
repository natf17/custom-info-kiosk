package com.ppublica.apps.kiosk.service.util.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEventType;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventAdminView;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventView;

@Component
public class SeasonalEventViewsConverter {
    public SeasonalEventView buildView(SeasonalEventType seasonalEvent) {

        return new SeasonalEventView(seasonalEvent.collectionId(), 
                                null, 
                                seasonalEvent.startDate().fieldValue() != null ? seasonalEvent.startDate().fieldValue().toString(): null,
                                seasonalEvent.eventLanguage().fieldValue(),
                                seasonalEvent.seasonId().fieldValue()
                                );
    }

    // expects at least one element in the list
    public SeasonalEventAdminView buildAdminView(List<? extends SeasonalEventType> seasonalEvents) {
        SeasonalEventType arbitarySeasonEvent = seasonalEvents.get(0);

        SeasonalEventView seasonalEventView = buildView(arbitarySeasonEvent);
        

        return new SeasonalEventAdminView(seasonalEventView.id(), null, seasonalEventView.startDate(), seasonalEventView.eventLanguage(), seasonalEventView.seasonId());
    }
}
