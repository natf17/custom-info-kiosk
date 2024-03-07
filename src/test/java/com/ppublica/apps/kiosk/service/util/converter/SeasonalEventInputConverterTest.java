package com.ppublica.apps.kiosk.service.util.converter;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.payloads.data.seasonalevent.SeasonalEventInput;

public class SeasonalEventInputConverterTest {
    SeasonalEventInputConverter converter;
    SeasonalEventInput input;
    
    @BeforeEach
    public void setup() {

        this.input = new SeasonalEventInput("2024-03-06", "en");

        this.converter = new SeasonalEventInputConverter();
    }
    
    /*
     * NOTE the following about the output event season type:
     *  - kioskLocale is always null
     *  -  the Status is always Published
     *  - the lastModified and createdOn values are always "now"
     *  - the collection id is always null
     * 
     * In practice, SeasonalEventTypes should always have the same locale
     */
    @Test
    public void givenSeasonalEventInput_toLocalizedSeasonalEvents_returnsSeasonalEventsTypeList() {
        SeasonalEventType seasonalEventType = converter.toSeasonalEvent(input);

        Assertions.assertNotNull(seasonalEventType);

        Assertions.assertEquals(new KioskCollectionField<>(LocalDate.of(2024, 3, 6), false), seasonalEventType.startDate());
        Assertions.assertEquals(new KioskCollectionField<>("en", false), seasonalEventType.eventLanguage());
        Assertions.assertEquals(null, seasonalEventType.collectionId());
        Assertions.assertEquals(CollectionType.SEASONAL_EVENT, seasonalEventType.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("SEASONAL_EVENT", false), seasonalEventType.kioskCollectionNameField());
        Assertions.assertNotNull(seasonalEventType.kioskCollectionMetadata());
        Assertions.assertEquals(Status.PUBLISHED, seasonalEventType.kioskCollectionMetadata().status());
        Assertions.assertNotNull(seasonalEventType.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(seasonalEventType.kioskCollectionMetadata().lastModified());
 
    }

}
