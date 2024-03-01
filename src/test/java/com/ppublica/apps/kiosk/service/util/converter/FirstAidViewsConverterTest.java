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
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultFirstAidType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EmptyFirstAidPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.FirstAidType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidAdminView;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidImage;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidView;

public class FirstAidViewsConverterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;

    FirstAidType firstAidTypeEn;
    FirstAidType firstAidTypeEs;
    FirstAidView firstAidViewEn;
    FirstAidView firstAidViewEs;
    FirstAidAdminView firstAidAdminView;

    FirstAidViewsConverter converter = new FirstAidViewsConverter();


    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);
        this.testFeatImg = new Image("url", 1, 2);

        AmenityType amenityTypeEn = new DefaultAmenityType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("First Aid", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .featImg(new KioskCollectionField<KioskImage>(new KioskImage("url", 1, 2), true))
                                            .svgElemId(new KioskCollectionField<String>("6", true))
                                            .isWheelChairAccessible(new KioskCollectionField<Boolean>(true, true))
                                            .note(new KioskCollectionField<String>("note_fieldValue", true))
                                            .name(new KioskCollectionField<String>("name_fieldValue", true))
                                            .location(new KioskCollectionField<LinkedCollectionReference>(new LinkedCollectionReference(5L), true))
                                            .build();

        this.firstAidTypeEn = new DefaultFirstAidType(amenityTypeEn, new EmptyFirstAidPiece());
        this.firstAidViewEn = new FirstAidView("1", "name_fieldValue", true, "6", "note_fieldValue", 5L, new FirstAidImage("url", 1, 2));

        AmenityType amenityTypeEs = new DefaultAmenityType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("First Aid", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "es", Status.PUBLISHED, testDate, testDateTime))
                                            .featImg(new KioskCollectionField<KioskImage>(new KioskImage("url_es", 1, 2), true))
                                            .svgElemId(new KioskCollectionField<String>("6_es", true))
                                            .isWheelChairAccessible(new KioskCollectionField<Boolean>(true, true))
                                            .note(new KioskCollectionField<String>("note_fieldValue_es", true))
                                            .name(new KioskCollectionField<String>("name_fieldValue_es", true))
                                            .location(new KioskCollectionField<LinkedCollectionReference>(new LinkedCollectionReference(5L), true))
                                            .build();

        this.firstAidTypeEs = new DefaultFirstAidType(amenityTypeEs, new EmptyFirstAidPiece());


        firstAidAdminView = new FirstAidAdminView("1",
                                                    List.of(new LocalizedField("en", "name_fieldValue"),
                                                            new LocalizedField("es", "name_fieldValue_es")),
                                                    true,
                                                    List.of(new LocalizedField("en", "6"),
                                                            new LocalizedField("es", "6_es")),
                                                    List.of(new LocalizedField("en", "note_fieldValue"),
                                                            new LocalizedField("es", "note_fieldValue_es")),
                                                    5L,
                                                    List.of(new LocalizedView<>("en", new FirstAidImage("url", 1, 2)),
                                                            new LocalizedView<>("es", new FirstAidImage("url_es", 1, 2)))
                                                );

    }
    
    @Test
    public void givenFirstAidType_buildView_returnsFirstAidView() {
        FirstAidView viewActual = converter.buildView(firstAidTypeEn);

        Assertions.assertEquals(firstAidViewEn, viewActual);
    }

    @Test
    public void givenFirstAidTypeList_buildAdminView_returnsFirstAidAdminView() {
        FirstAidAdminView adminViewActual = converter.buildAdminView(List.of(firstAidTypeEn, firstAidTypeEs));

        Assertions.assertEquals(firstAidAdminView, adminViewActual);

    }
}
