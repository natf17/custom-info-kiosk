package com.ppublica.apps.kiosk.service.util.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.kiosk.AmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultDonationsPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultDonationsType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DonationsType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.donations.DonationAdminView;
import com.ppublica.apps.kiosk.service.views.donations.DonationImage;
import com.ppublica.apps.kiosk.service.views.donations.DonationView;

public class DonationViewsConverterTest {
    
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;

    DonationsType donationTypeEn;
    DonationsType donationTypeEs;
    DonationView donationViewEn;
    DonationView donationViewEs;
    DonationAdminView donationAdminView;

    DonationViewsConverter converter = new DonationViewsConverter();


    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);
        this.testFeatImg = new Image("url", 1, 2);

        AmenityType amenityTypeEn = new DefaultAmenityType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Bathroom", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .featImg(new KioskCollectionField<KioskImage>(new KioskImage("url", 1, 2), true))
                                            .svgElemId(new KioskCollectionField<String>("6", true))
                                            .isWheelChairAccessible(new KioskCollectionField<Boolean>(true, true))
                                            .note(new KioskCollectionField<String>("note_fieldValue", true))
                                            .name(new KioskCollectionField<String>("name_fieldValue", true))
                                            .location(new KioskCollectionField<LinkedCollectionReference>(new LinkedCollectionReference(5L), true))
                                            .build();

        this.donationTypeEn = new DefaultDonationsType(amenityTypeEn, new DefaultDonationsPiece(new KioskCollectionField<String>("payment_field_value", false)));
        this.donationViewEn = new DonationView("1", "name_fieldValue", true, "6", "note_fieldValue", 5L, "payment_field_value", new DonationImage("url", 1, 2));

        AmenityType amenityTypeEs = new DefaultAmenityType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Bathroom", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "es", Status.PUBLISHED, testDate, testDateTime))
                                            .featImg(new KioskCollectionField<KioskImage>(new KioskImage("url_es", 1, 2), true))
                                            .svgElemId(new KioskCollectionField<String>("6_es", true))
                                            .isWheelChairAccessible(new KioskCollectionField<Boolean>(true, true))
                                            .note(new KioskCollectionField<String>("note_fieldValue_es", true))
                                            .name(new KioskCollectionField<String>("name_fieldValue_es", true))
                                            .location(new KioskCollectionField<LinkedCollectionReference>(new LinkedCollectionReference(5L), true))
                                            .build();

        this.donationTypeEs = new DefaultDonationsType(amenityTypeEs, new DefaultDonationsPiece(new KioskCollectionField<String>("payment_field_value", false)));


        donationAdminView = new DonationAdminView("1",
                                                    List.of(new LocalizedField("en", "name_fieldValue"),
                                                            new LocalizedField("es", "name_fieldValue_es")),
                                                    true,
                                                    List.of(new LocalizedField("en", "6"),
                                                            new LocalizedField("es", "6_es")),
                                                    List.of(new LocalizedField("en", "note_fieldValue"),
                                                            new LocalizedField("es", "note_fieldValue_es")),
                                                    5L,
                                                    "payment_field_value",
                                                    List.of(new LocalizedView<>("en", new DonationImage("url", 1, 2)),
                                                            new LocalizedView<>("es", new DonationImage("url_es", 1, 2)))
                                                );

    }
    
    @Test
    public void givenDonationsType_buildView_returnsDonationView() {
        DonationView viewActual = converter.buildView(donationTypeEn);

        Assertions.assertEquals(donationViewEn, viewActual);
    }

    @Test
    public void givenDonationsTypeList_buildAdminView_returnsDonationAdminView() {
        DonationAdminView adminViewActual = converter.buildAdminView(List.of(donationTypeEn, donationTypeEs));

        Assertions.assertEquals(donationAdminView, adminViewActual);

    }
}
