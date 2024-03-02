package com.ppublica.apps.kiosk.service.util.converter;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LocationType;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.payloads.locations.LocationInput;
import com.ppublica.apps.kiosk.service.payloads.locations.MapImageInput;

public class LocationInputConverterTest {
    LocationInputConverter converter;
    LocationInput input;
    
    @BeforeEach
    public void setup() {

        this.input = new LocationInput(List.of(new LocalizedInputField<>("1", "fullName_fieldValue"),
                                                new LocalizedInputField<>("2", "fullName_fieldValue_es")),
                                        1,
                                        List.of(new LocalizedInputField<>("1", "levelName_fieldValue"),
                                                new LocalizedInputField<>("2", "levelName_fieldValue_es")),
                                        List.of(new LocalizedInputField<>("1", new MapImageInput(1, 2, "url")),
                                                new LocalizedInputField<>("2", new MapImageInput(1, 2, "url_es")))
                                        );

        this.converter = new LocationInputConverter();
    }
    
    /*
     * NOTE the following about the output location type:
     *  - kioskLocale is always null
     *  -  the Status is always Published
     *  - the lastModified and createdOn values are always "now"
     *  - the collection id is always null
     */
    @Test
    public void givenLocationInput_toLocalizedLocations_returnsLocationsTypeList() {
        List<LocationType> locationTypes = converter.toLocalizedLocations(input);

        Assertions.assertEquals(2, locationTypes.size());

        // select by lang:
        LocationType locationTypeEn = locationTypes.get(0).kioskCollectionMetadata().localeId().equals(1L) ? locationTypes.get(0) : locationTypes.get(1);
        LocationType locationTypeEs = locationTypes.get(0).kioskCollectionMetadata().localeId().equals(2L) ? locationTypes.get(0) : locationTypes.get(1);

        Assertions.assertNotNull(locationTypeEn);
        Assertions.assertNotNull(locationTypeEs);

        Assertions.assertEquals(new KioskCollectionField<>(1, false), locationTypeEn.level_num());
        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url", 1, 2), true), locationTypeEn.map());
        Assertions.assertEquals(new KioskCollectionField<>("fullName_fieldValue", true), locationTypeEn.fullName());
        Assertions.assertEquals(new KioskCollectionField<>("levelName_fieldValue", true), locationTypeEn.level_name());
        Assertions.assertEquals(null, locationTypeEn.collectionId());
        Assertions.assertEquals(CollectionType.LOCATION, locationTypeEn.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("LOCATION", false), locationTypeEn.kioskCollectionNameField());
        Assertions.assertNotNull(locationTypeEn.kioskCollectionMetadata());
        Assertions.assertEquals(1L, locationTypeEn.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, locationTypeEn.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, locationTypeEn.kioskCollectionMetadata().status());
        Assertions.assertNotNull(locationTypeEn.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(locationTypeEn.kioskCollectionMetadata().lastModified());

        Assertions.assertEquals(new KioskCollectionField<>(1, false), locationTypeEs.level_num());
        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url_es", 1, 2), true), locationTypeEs.map());
        Assertions.assertEquals(new KioskCollectionField<>("fullName_fieldValue_es", true), locationTypeEs.fullName());
        Assertions.assertEquals(new KioskCollectionField<>("levelName_fieldValue_es", true), locationTypeEs.level_name());
        Assertions.assertEquals(null, locationTypeEs.collectionId());
        Assertions.assertEquals(CollectionType.LOCATION, locationTypeEs.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("LOCATION", false), locationTypeEs.kioskCollectionNameField());
        Assertions.assertNotNull(locationTypeEs.kioskCollectionMetadata());
        Assertions.assertEquals(2L, locationTypeEs.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, locationTypeEs.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, locationTypeEs.kioskCollectionMetadata().status());
        Assertions.assertNotNull(locationTypeEs.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(locationTypeEs.kioskCollectionMetadata().lastModified());

    }

    
}
