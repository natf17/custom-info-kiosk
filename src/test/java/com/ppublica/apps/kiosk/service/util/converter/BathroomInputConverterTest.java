package com.ppublica.apps.kiosk.service.util.converter;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.payloads.ImageInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;

public class BathroomInputConverterTest {

    BathroomInputConverter converter;
    BathroomInput input;
    
    @BeforeEach
    public void setup() {
        ///BathroomInput(List<LocalizedInputField<String>> name, String gender, Boolean isWheelchairAccessible, List<LocalizedInputField<String>> svgElemId, List<LocalizedInputField<String>> note, Long locationId, List<LocalizedInputField<BathroomImage>> featImg) 

        this.input = new BathroomInput(List.of(new LocalizedInputField<>("1", "name_fieldValue"),
                                                new LocalizedInputField<>("2", "name_fieldValue_es")),
                                        "gender_field_value",
                                        true,
                                        List.of(new LocalizedInputField<>("1", "6"),
                                                new LocalizedInputField<>("2", "6_es")),
                                        List.of(new LocalizedInputField<>("1", "note_fieldValue"),
                                                new LocalizedInputField<>("2", "note_fieldValue_es")),
                                        5L,
                                        List.of(new LocalizedInputField<>("1", new ImageInput("url", 1, 2)),
                                                new LocalizedInputField<>("2", new ImageInput("url_es", 1, 2)))
                                        );

        this.converter = new BathroomInputConverter();
    }
    
    /*
     * NOTE the following about the output BathroomType:
     *  - kioskLocale is always null
     *  -  the Status is always Published
     *  - the lastModified and createdOn values are always "now"
     *  - the collection id is always null
     */
    @Test
    public void givenBathroomInput_toLocalizedBathrooms_returnsBathroomTypeList() {
        List<BathroomType> bathroomTypes = converter.toLocalizedBathrooms(input);

        Assertions.assertEquals(2, bathroomTypes.size());

        // select by lang:
        BathroomType bathroomTypeEn = bathroomTypes.get(0).kioskCollectionMetadata().localeId().equals(1L) ? bathroomTypes.get(0) : bathroomTypes.get(1);
        BathroomType bathroomTypeEs = bathroomTypes.get(0).kioskCollectionMetadata().localeId().equals(2L) ? bathroomTypes.get(0) : bathroomTypes.get(1);

        Assertions.assertNotNull(bathroomTypeEn);
        Assertions.assertNotNull(bathroomTypeEs);

        Assertions.assertEquals(new KioskCollectionField<>("gender_field_value", false), bathroomTypeEn.gender());
        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url", 1, 2), true), bathroomTypeEn.featImg());
        Assertions.assertEquals(new KioskCollectionField<>("6", true), bathroomTypeEn.svgElemId());
        Assertions.assertEquals(new KioskCollectionField<>(true, false), bathroomTypeEn.isWheelChairAccessible());
        Assertions.assertEquals(new KioskCollectionField<>("name_fieldValue", true), bathroomTypeEn.name());
        Assertions.assertEquals(new KioskCollectionField<>("note_fieldValue", true), bathroomTypeEn.note());
        Assertions.assertEquals(new KioskCollectionField<>(new LinkedCollectionReference(5L), false), bathroomTypeEn.location());
        Assertions.assertEquals(null, bathroomTypeEn.collectionId());
        Assertions.assertEquals(CollectionType.BATHROOM, bathroomTypeEn.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("BATHROOM", false), bathroomTypeEn.kioskCollectionNameField());
        Assertions.assertNotNull(bathroomTypeEn.kioskCollectionMetadata());
        Assertions.assertEquals(1L, bathroomTypeEn.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, bathroomTypeEn.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, bathroomTypeEn.kioskCollectionMetadata().status());
        Assertions.assertNotNull(bathroomTypeEn.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(bathroomTypeEn.kioskCollectionMetadata().lastModified());

        Assertions.assertEquals(new KioskCollectionField<>("gender_field_value", false), bathroomTypeEs.gender());
        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url_es", 1, 2), true), bathroomTypeEs.featImg());
        Assertions.assertEquals(new KioskCollectionField<>("6_es", true), bathroomTypeEs.svgElemId());
        Assertions.assertEquals(new KioskCollectionField<>(true, false), bathroomTypeEs.isWheelChairAccessible());
        Assertions.assertEquals(new KioskCollectionField<>("name_fieldValue_es", true), bathroomTypeEs.name());
        Assertions.assertEquals(new KioskCollectionField<>("note_fieldValue_es", true), bathroomTypeEs.note());
        Assertions.assertEquals(new KioskCollectionField<>(new LinkedCollectionReference(5L), false), bathroomTypeEs.location());
        Assertions.assertEquals(null, bathroomTypeEs.collectionId());
        Assertions.assertEquals(CollectionType.BATHROOM, bathroomTypeEs.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("BATHROOM", false), bathroomTypeEs.kioskCollectionNameField());
        Assertions.assertNotNull(bathroomTypeEs.kioskCollectionMetadata());
        Assertions.assertEquals(2L, bathroomTypeEs.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, bathroomTypeEs.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, bathroomTypeEs.kioskCollectionMetadata().status());
        Assertions.assertNotNull(bathroomTypeEs.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(bathroomTypeEs.kioskCollectionMetadata().lastModified());


    }
    
}
