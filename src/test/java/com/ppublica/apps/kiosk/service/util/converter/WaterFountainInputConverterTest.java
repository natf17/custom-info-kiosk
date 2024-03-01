package com.ppublica.apps.kiosk.service.util.converter;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.domain.model.kiosk.WaterFountainType;
import com.ppublica.apps.kiosk.service.payloads.ImageInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.payloads.waterfountains.WaterFountainInput;

public class WaterFountainInputConverterTest {
    WaterFountainInputConverter converter;
    WaterFountainInput input;
    
    @BeforeEach
    public void setup() {
        this.input = new WaterFountainInput(List.of(new LocalizedInputField<>("1", "name_fieldValue"),
                                                new LocalizedInputField<>("2", "name_fieldValue_es")),
                                        true,
                                        List.of(new LocalizedInputField<>("1", "6"),
                                                new LocalizedInputField<>("2", "6_es")),
                                        List.of(new LocalizedInputField<>("1", "note_fieldValue"),
                                                new LocalizedInputField<>("2", "note_fieldValue_es")),
                                        5L,
                                        List.of(new LocalizedInputField<>("1", new ImageInput("url", 1, 2)),
                                                new LocalizedInputField<>("2", new ImageInput("url_es", 1, 2)))
                                        );

        this.converter = new WaterFountainInputConverter();
    }
    
    /*
     * NOTE the following about the output water fountain type:
     *  - kioskLocale is always null
     *  -  the Status is always Published
     *  - the lastModified and createdOn values are always "now"
     *  - the collection id is always null
     */
    @Test
    public void givenWaterFountainInput_toLocalizedWaterFountains_returnWaterFountainsTypeList() {
        List<WaterFountainType> waterFountainTypes = converter.toLocalizedWaterFountains(input);

        Assertions.assertEquals(2, waterFountainTypes.size());

        // select by lang:
        WaterFountainType waterFountainTypeEn = waterFountainTypes.get(0).kioskCollectionMetadata().localeId().equals(1L) ? waterFountainTypes.get(0) : waterFountainTypes.get(1);
        WaterFountainType waterFountainTypeEs = waterFountainTypes.get(0).kioskCollectionMetadata().localeId().equals(2L) ? waterFountainTypes.get(0) : waterFountainTypes.get(1);

        Assertions.assertNotNull(waterFountainTypeEn);
        Assertions.assertNotNull(waterFountainTypeEs);

        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url", 1, 2), true), waterFountainTypeEn.featImg());
        Assertions.assertEquals(new KioskCollectionField<>("6", true), waterFountainTypeEn.svgElemId());
        Assertions.assertEquals(new KioskCollectionField<>(true, false), waterFountainTypeEn.isWheelChairAccessible());
        Assertions.assertEquals(new KioskCollectionField<>("name_fieldValue", true), waterFountainTypeEn.name());
        Assertions.assertEquals(new KioskCollectionField<>("note_fieldValue", true), waterFountainTypeEn.note());
        Assertions.assertEquals(new KioskCollectionField<>(new LinkedCollectionReference(5L), false), waterFountainTypeEn.location());
        Assertions.assertEquals(null, waterFountainTypeEn.collectionId());
        Assertions.assertEquals(CollectionType.WATER_FOUNTAIN, waterFountainTypeEn.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("WATER_FOUNTAIN", false), waterFountainTypeEn.kioskCollectionNameField());
        Assertions.assertNotNull(waterFountainTypeEn.kioskCollectionMetadata());
        Assertions.assertEquals(1L, waterFountainTypeEn.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, waterFountainTypeEn.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, waterFountainTypeEn.kioskCollectionMetadata().status());
        Assertions.assertNotNull(waterFountainTypeEn.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(waterFountainTypeEn.kioskCollectionMetadata().lastModified());

        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url_es", 1, 2), true), waterFountainTypeEs.featImg());
        Assertions.assertEquals(new KioskCollectionField<>("6_es", true), waterFountainTypeEs.svgElemId());
        Assertions.assertEquals(new KioskCollectionField<>(true, false), waterFountainTypeEs.isWheelChairAccessible());
        Assertions.assertEquals(new KioskCollectionField<>("name_fieldValue_es", true), waterFountainTypeEs.name());
        Assertions.assertEquals(new KioskCollectionField<>("note_fieldValue_es", true), waterFountainTypeEs.note());
        Assertions.assertEquals(new KioskCollectionField<>(new LinkedCollectionReference(5L), false), waterFountainTypeEs.location());
        Assertions.assertEquals(null, waterFountainTypeEs.collectionId());
        Assertions.assertEquals(CollectionType.WATER_FOUNTAIN, waterFountainTypeEs.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("WATER_FOUNTAIN", false), waterFountainTypeEs.kioskCollectionNameField());
        Assertions.assertNotNull(waterFountainTypeEs.kioskCollectionMetadata());
        Assertions.assertEquals(2L, waterFountainTypeEs.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, waterFountainTypeEs.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, waterFountainTypeEs.kioskCollectionMetadata().status());
        Assertions.assertNotNull(waterFountainTypeEs.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(waterFountainTypeEs.kioskCollectionMetadata().lastModified());


    }
}
