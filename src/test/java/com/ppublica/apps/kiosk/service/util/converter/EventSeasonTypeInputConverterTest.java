package com.ppublica.apps.kiosk.service.util.converter;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EventSeasonType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.payloads.data.eventseason.EventSeasonInput;

public class EventSeasonTypeInputConverterTest {
    EventSeasonInputConverter converter;
    EventSeasonInput input;
    
    @BeforeEach
    public void setup() {

        this.input = new EventSeasonInput("type_fieldValue",
                                        3,
                                        List.of(new LocalizedInputField<>("1", "theme_fieldValue"),
                                                new LocalizedInputField<>("2", "theme_fieldValue_es")),
                                        2024,
                                        List.of(new LocalizedInputField<>("1", "durationText_fieldValue"),
                                                new LocalizedInputField<>("2", "durationText_fieldValue_es"))
                                        );

        this.converter = new EventSeasonInputConverter();
    }
    
    /*
     * NOTE the following about the output event season type:
     *  - kioskLocale is always null
     *  -  the Status is always Published
     *  - the lastModified and createdOn values are always "now"
     *  - the collection id is always null
     */
    @Test
    public void givenLocationInput_toLocalizedLocations_returnsLocationsTypeList() {
        List<EventSeasonType> eventSeasonTypes = converter.toLocalizedEventSeasons(input);

        Assertions.assertEquals(2, eventSeasonTypes.size());

        // select by lang:
        EventSeasonType eventSeasonTypeEn = eventSeasonTypes.get(0).kioskCollectionMetadata().localeId().equals(1L) ? eventSeasonTypes.get(0) : eventSeasonTypes.get(1);
        EventSeasonType eventSeasonTypeEs = eventSeasonTypes.get(0).kioskCollectionMetadata().localeId().equals(2L) ? eventSeasonTypes.get(0) : eventSeasonTypes.get(1);

        Assertions.assertNotNull(eventSeasonTypeEn);
        Assertions.assertNotNull(eventSeasonTypeEs);

        Assertions.assertEquals(new KioskCollectionField<>("type_fieldValue", false), eventSeasonTypeEn.seasonType());
        Assertions.assertEquals(new KioskCollectionField<>(3, false), eventSeasonTypeEn.durationDays());
        Assertions.assertEquals(new KioskCollectionField<>("theme_fieldValue", true), eventSeasonTypeEn.theme());
        Assertions.assertEquals(new KioskCollectionField<>(2024, false), eventSeasonTypeEn.serviceYear());
        Assertions.assertEquals(new KioskCollectionField<>("durationText_fieldValue", true), eventSeasonTypeEn.durationText());
        Assertions.assertEquals(null, eventSeasonTypeEn.collectionId());
        Assertions.assertEquals(CollectionType.EVENT_SEASON, eventSeasonTypeEn.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("EVENT_SEASON", false), eventSeasonTypeEn.kioskCollectionNameField());
        Assertions.assertNotNull(eventSeasonTypeEn.kioskCollectionMetadata());
        Assertions.assertEquals(1L, eventSeasonTypeEn.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, eventSeasonTypeEn.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, eventSeasonTypeEn.kioskCollectionMetadata().status());
        Assertions.assertNotNull(eventSeasonTypeEn.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(eventSeasonTypeEn.kioskCollectionMetadata().lastModified());

        Assertions.assertEquals(new KioskCollectionField<>("type_fieldValue", false), eventSeasonTypeEs.seasonType());
        Assertions.assertEquals(new KioskCollectionField<>(3, false), eventSeasonTypeEs.durationDays());
        Assertions.assertEquals(new KioskCollectionField<>("theme_fieldValue_es", true), eventSeasonTypeEs.theme());
        Assertions.assertEquals(new KioskCollectionField<>(2024, false), eventSeasonTypeEs.serviceYear());
        Assertions.assertEquals(new KioskCollectionField<>("durationText_fieldValue_es", true), eventSeasonTypeEs.durationText());
        Assertions.assertEquals(null, eventSeasonTypeEs.collectionId());
        Assertions.assertEquals(CollectionType.EVENT_SEASON, eventSeasonTypeEs.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("EVENT_SEASON", false), eventSeasonTypeEs.kioskCollectionNameField());
        Assertions.assertNotNull(eventSeasonTypeEs.kioskCollectionMetadata());
        Assertions.assertEquals(2L, eventSeasonTypeEs.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, eventSeasonTypeEs.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, eventSeasonTypeEs.kioskCollectionMetadata().status());
        Assertions.assertNotNull(eventSeasonTypeEs.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(eventSeasonTypeEs.kioskCollectionMetadata().lastModified());
 
    }
}
