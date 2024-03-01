package com.ppublica.apps.kiosk.service.util.converter;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.FirstAidType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.payloads.ImageInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.payloads.firstaid.FirstAidInput;

public class FirstAidInputConverterTest {
    FirstAidInputConverter converter;
    FirstAidInput input;
    
    @BeforeEach
    public void setup() {
        this.input = new FirstAidInput(List.of(new LocalizedInputField<>("1", "name_fieldValue"),
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

        this.converter = new FirstAidInputConverter();
    }
    
    /*
     * NOTE the following about the output first aid type:
     *  - kioskLocale is always null
     *  -  the Status is always Published
     *  - the lastModified and createdOn values are always "now"
     *  - the collection id is always null
     */
    @Test
    public void givenDonationInput_toLocalizedDonations_returnsDonationsTypeList() {
        List<FirstAidType> firstAidTypes = converter.toLocalizedFirstAids(input);

        Assertions.assertEquals(2, firstAidTypes.size());

        // select by lang:
        FirstAidType firstAidTypeEn = firstAidTypes.get(0).kioskCollectionMetadata().localeId().equals(1L) ? firstAidTypes.get(0) : firstAidTypes.get(1);
        FirstAidType firstAidTypeEs = firstAidTypes.get(0).kioskCollectionMetadata().localeId().equals(2L) ? firstAidTypes.get(0) : firstAidTypes.get(1);

        Assertions.assertNotNull(firstAidTypeEn);
        Assertions.assertNotNull(firstAidTypeEs);

        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url", 1, 2), true), firstAidTypeEn.featImg());
        Assertions.assertEquals(new KioskCollectionField<>("6", true), firstAidTypeEn.svgElemId());
        Assertions.assertEquals(new KioskCollectionField<>(true, false), firstAidTypeEn.isWheelChairAccessible());
        Assertions.assertEquals(new KioskCollectionField<>("name_fieldValue", true), firstAidTypeEn.name());
        Assertions.assertEquals(new KioskCollectionField<>("note_fieldValue", true), firstAidTypeEn.note());
        Assertions.assertEquals(new KioskCollectionField<>(new LinkedCollectionReference(5L), false), firstAidTypeEn.location());
        Assertions.assertEquals(null, firstAidTypeEn.collectionId());
        Assertions.assertEquals(CollectionType.FIRST_AID, firstAidTypeEn.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("FIRST_AID", false), firstAidTypeEn.kioskCollectionNameField());
        Assertions.assertNotNull(firstAidTypeEn.kioskCollectionMetadata());
        Assertions.assertEquals(1L, firstAidTypeEn.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, firstAidTypeEn.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, firstAidTypeEn.kioskCollectionMetadata().status());
        Assertions.assertNotNull(firstAidTypeEn.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(firstAidTypeEn.kioskCollectionMetadata().lastModified());

        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url_es", 1, 2), true), firstAidTypeEs.featImg());
        Assertions.assertEquals(new KioskCollectionField<>("6_es", true), firstAidTypeEs.svgElemId());
        Assertions.assertEquals(new KioskCollectionField<>(true, false), firstAidTypeEs.isWheelChairAccessible());
        Assertions.assertEquals(new KioskCollectionField<>("name_fieldValue_es", true), firstAidTypeEs.name());
        Assertions.assertEquals(new KioskCollectionField<>("note_fieldValue_es", true), firstAidTypeEs.note());
        Assertions.assertEquals(new KioskCollectionField<>(new LinkedCollectionReference(5L), false), firstAidTypeEs.location());
        Assertions.assertEquals(null, firstAidTypeEs.collectionId());
        Assertions.assertEquals(CollectionType.FIRST_AID, firstAidTypeEs.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("FIRST_AID", false), firstAidTypeEs.kioskCollectionNameField());
        Assertions.assertNotNull(firstAidTypeEs.kioskCollectionMetadata());
        Assertions.assertEquals(2L, firstAidTypeEs.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, firstAidTypeEs.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, firstAidTypeEs.kioskCollectionMetadata().status());
        Assertions.assertNotNull(firstAidTypeEs.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(firstAidTypeEs.kioskCollectionMetadata().lastModified());


    }
}