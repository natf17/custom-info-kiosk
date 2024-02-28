package com.ppublica.apps.kiosk.service.util.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.kiosk.AmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultBathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.GenderInfo;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomImage;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;

public class BathroomViewsConverterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;

    BathroomType bathroomTypeEn;
    BathroomType bathroomTypeEs;
    BathroomView bathroomViewEn;
    BathroomView bathroomViewEs;
    BathroomAdminView bathroomAdminView;

    BathroomViewsConverter converter = new BathroomViewsConverter();


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

        this.bathroomTypeEn = new DefaultBathroomType(amenityTypeEn, new GenderInfo(new KioskCollectionField<String>("gender_field_value", true)));
        this.bathroomViewEn = new BathroomView("1", "name_fieldValue", "gender_field_value", true, "6", "note_fieldValue", 5L, new BathroomImage("url", 1, 2));

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

        this.bathroomTypeEs = new DefaultBathroomType(amenityTypeEs, new GenderInfo(new KioskCollectionField<String>("gender_field_value", true)));


        bathroomAdminView = new BathroomAdminView("1",
                                                    List.of(new LocalizedField("en", "name_fieldValue"),
                                                            new LocalizedField("es", "name_fieldValue_es")),
                                                    "gender_field_value",
                                                    true,
                                                    List.of(new LocalizedField("en", "6"),
                                                            new LocalizedField("es", "6_es")),
                                                    List.of(new LocalizedField("en", "note_fieldValue"),
                                                            new LocalizedField("es", "note_fieldValue_es")),
                                                    5L,
                                                    List.of(new LocalizedView<>("en", new BathroomImage("url", 1, 2)),
                                                            new LocalizedView<>("es", new BathroomImage("url_es", 1, 2)))
                                                );

    }
    
    @Test
    public void givenBathroomType_buildView_returnsBathroomView() {
        BathroomView viewActual = converter.buildView(bathroomTypeEn);

        Assertions.assertEquals(bathroomViewEn, viewActual);
    }

    @Test
    public void givenBathroomTypeList_buildAdminView_returnsBathroomAdminView() {
        BathroomAdminView adminViewActual = converter.buildAdminView(List.of(bathroomTypeEn, bathroomTypeEs));

        Assertions.assertEquals(bathroomAdminView, adminViewActual);

    }
}
