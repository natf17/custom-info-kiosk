package com.ppublica.apps.kiosk.service.util.converter;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DonationsType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.payloads.ImageInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.payloads.donations.DonationInput;

public class DonationInputConverterTest {
    DonationInputConverter converter;
    DonationInput input;
    
    @BeforeEach
    public void setup() {

        this.input = new DonationInput(List.of(new LocalizedInputField<>("1", "name_fieldValue"),
                                                new LocalizedInputField<>("2", "name_fieldValue_es")),
                                        "payment_field_value",
                                        true,
                                        List.of(new LocalizedInputField<>("1", "6"),
                                                new LocalizedInputField<>("2", "6_es")),
                                        List.of(new LocalizedInputField<>("1", "note_fieldValue"),
                                                new LocalizedInputField<>("2", "note_fieldValue_es")),
                                        5L,
                                        List.of(new LocalizedInputField<>("1", new ImageInput("url", 1, 2)),
                                                new LocalizedInputField<>("2", new ImageInput("url_es", 1, 2)))
                                        );

        this.converter = new DonationInputConverter();
    }
    
    /*
     * NOTE the following about the output donation type:
     *  - kioskLocale is always null
     *  -  the Status is always Published
     *  - the lastModified and createdOn values are always "now"
     *  - the collection id is always null
     */
    @Test
    public void givenDonationInput_toLocalizedDonations_returnsDonationsTypeList() {
        List<DonationsType> donationTypes = converter.toLocalizedDonations(input);

        Assertions.assertEquals(2, donationTypes.size());

        // select by lang:
        DonationsType donationTypeEn = donationTypes.get(0).kioskCollectionMetadata().localeId().equals(1L) ? donationTypes.get(0) : donationTypes.get(1);
        DonationsType donationTypeEs = donationTypes.get(0).kioskCollectionMetadata().localeId().equals(2L) ? donationTypes.get(0) : donationTypes.get(1);

        Assertions.assertNotNull(donationTypeEn);
        Assertions.assertNotNull(donationTypeEs);

        Assertions.assertEquals(new KioskCollectionField<>("payment_field_value", false), donationTypeEn.paymentTypesAccepted());
        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url", 1, 2), true), donationTypeEn.featImg());
        Assertions.assertEquals(new KioskCollectionField<>("6", true), donationTypeEn.svgElemId());
        Assertions.assertEquals(new KioskCollectionField<>(true, false), donationTypeEn.isWheelChairAccessible());
        Assertions.assertEquals(new KioskCollectionField<>("name_fieldValue", true), donationTypeEn.name());
        Assertions.assertEquals(new KioskCollectionField<>("note_fieldValue", true), donationTypeEn.note());
        Assertions.assertEquals(new KioskCollectionField<>(new LinkedCollectionReference(5L), false), donationTypeEn.location());
        Assertions.assertEquals(null, donationTypeEn.collectionId());
        Assertions.assertEquals(CollectionType.DONATIONS, donationTypeEn.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("DONATIONS", false), donationTypeEn.kioskCollectionNameField());
        Assertions.assertNotNull(donationTypeEn.kioskCollectionMetadata());
        Assertions.assertEquals(1L, donationTypeEn.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, donationTypeEn.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, donationTypeEn.kioskCollectionMetadata().status());
        Assertions.assertNotNull(donationTypeEn.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(donationTypeEn.kioskCollectionMetadata().lastModified());

        Assertions.assertEquals(new KioskCollectionField<>("payment_field_value", false), donationTypeEs.paymentTypesAccepted());
        Assertions.assertEquals(new KioskCollectionField<>(new KioskImage("url_es", 1, 2), true), donationTypeEs.featImg());
        Assertions.assertEquals(new KioskCollectionField<>("6_es", true), donationTypeEs.svgElemId());
        Assertions.assertEquals(new KioskCollectionField<>(true, false), donationTypeEs.isWheelChairAccessible());
        Assertions.assertEquals(new KioskCollectionField<>("name_fieldValue_es", true), donationTypeEs.name());
        Assertions.assertEquals(new KioskCollectionField<>("note_fieldValue_es", true), donationTypeEs.note());
        Assertions.assertEquals(new KioskCollectionField<>(new LinkedCollectionReference(5L), false), donationTypeEs.location());
        Assertions.assertEquals(null, donationTypeEs.collectionId());
        Assertions.assertEquals(CollectionType.DONATIONS, donationTypeEs.kioskCollectionType());
        Assertions.assertEquals(new KioskCollectionField<>("DONATIONS", false), donationTypeEs.kioskCollectionNameField());
        Assertions.assertNotNull(donationTypeEs.kioskCollectionMetadata());
        Assertions.assertEquals(2L, donationTypeEs.kioskCollectionMetadata().localeId());
        Assertions.assertEquals(null, donationTypeEs.kioskCollectionMetadata().kioskLocale());
        Assertions.assertEquals(Status.PUBLISHED, donationTypeEs.kioskCollectionMetadata().status());
        Assertions.assertNotNull(donationTypeEs.kioskCollectionMetadata().createdOn());
        Assertions.assertNotNull(donationTypeEs.kioskCollectionMetadata().lastModified());


    }
}
