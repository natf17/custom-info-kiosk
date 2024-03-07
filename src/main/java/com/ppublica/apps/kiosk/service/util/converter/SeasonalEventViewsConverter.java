package com.ppublica.apps.kiosk.service.util.converter;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEventType;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventAdminView;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventView;

@Component
public class SeasonalEventViewsConverter {
    public SeasonalEventView buildView(SeasonalEventType seasonalEvent) {

        return new SeasonalEventView(seasonalEvent.collectionId(), 
                                seasonalEvent.startDate().fieldValue() != null ? seasonalEvent.startDate().fieldValue().toString(): null,
                                seasonalEvent.eventLanguage().fieldValue(),
                                seasonalEvent.seasonId().fieldValue()
                                );
    }

    public SeasonalEventAdminView buildAdminView(SeasonalEventType seasonalEvent) {

        SeasonalEventView seasonalEventView = buildView(seasonalEvent);
        

        return new SeasonalEventAdminView(seasonalEventView.id(), seasonalEventView.startDate(), seasonalEventView.eventLanguage(), seasonalEventView.seasonId());
    }
}
