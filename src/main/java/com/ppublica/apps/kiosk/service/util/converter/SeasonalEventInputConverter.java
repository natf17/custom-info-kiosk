package com.ppublica.apps.kiosk.service.util.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultSeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.NonLocalizableKioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.payloads.data.seasonalevent.SeasonalEventInput;
import com.ppublica.apps.kiosk.service.util.LocalDateKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.StringKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.StringToLocalDateConverter;

@Component
public class SeasonalEventInputConverter {

    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    public SeasonalEventType toSeasonalEvent(SeasonalEventInput adminInput) {
        DefaultSeasonalEventType seasonalEvent = new DefaultSeasonalEventType.Builder()
                                                                    .kioskCollectionMetadata(new NonLocalizableKioskCollectionMetadata( Status.PUBLISHED, LocalDate.now(), LocalDateTime.now()))
                                                                    .collectionNameField(new KioskCollectionField<>(CollectionType.SEASONAL_EVENT.toString(), false))
                                                                    .startDate(new LocalDateKioskFieldCreator()
                                                                                        .create(new StringToLocalDateConverter()
                                                                                                    .convert(adminInput.startDate()), false))
                                                                    .eventLanguage(new StringKioskFieldCreator()
                                                                                        .create(adminInput.eventLanguage(), false))
                                                                    .build();

        return seasonalEvent;
    }

}
