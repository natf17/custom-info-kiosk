package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.collection.DonationTypeImpl;

public class BathroomKioskCollectionAdapterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;
    SimpleCollectionType cmsObj;

    AmenityType kioskObj;
/*
    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2023, 9, 12);
        this.testDateTime = LocalDateTime.of(2023, 1, 9, 9, 30);
        this.testFeatImg = new Image("url", 1, 2);

        this.cmsObj = new SimpleCollectionTypeImpl.Builder()
                                                .collectionNameField(new CollectionNameField("coll_name_fieldName", "coll_name_fieldValue"))
                                                .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                                .addTextField(new TextField(AmenityConverter.NOTE_FIELD_TYPE, "note_fieldName", "note_fieldValue"))
                                                .addTextField(new TextField(AmenityConverter.NAME_FIELD_TYPE, "name_fieldName", "name_fieldValue"))
                                                .addImageField(new ImageField(AmenityConverter.FEATIMG_FIELD_TYPE, "featImg_fieldName", testFeatImg))
                                                .addNumericField(new NumericField(AmenityConverter.SVGELEM_FIELD_TYPE, "svgElemId_fieldName", 6L))
                                                .addBooleanField(new BooleanField(AmenityConverter.ISWHEELCHAIRACC_FIELD_TYPE, "isWheelChairAccessible_fieldName", true))
                                                .addLinkedCollectionField(new LinkedCollectionField(AmenityConverter.LOCATION_FIELD_TYPE, "location_fieldName", 5L))
                                                .type(DonationTypeImpl.KIOSK_COLLECTION_TYPE_NAME.toString())
                                                .withId(2L)
                                                .build();

        this.kioskObj = new DonationTypeImpl.Builder()
                                            .id(2L)
                                            .collectionNameField(new KioskCollectionField<String>("coll_name_fieldName", "coll_name_fieldValue", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                            .featImg(new KioskCollectionField<Image>("featImg_fieldName", testFeatImg, true))
                                            .svgElemId(new KioskCollectionField<Long>("svgElemId_fieldName", 6L, true))
                                            .isWheelChairAccessible(new KioskCollectionField<Boolean>("isWheelChairAccessible_fieldName", true, true))
                                            .note(new KioskCollectionField<String>("note_fieldName", "note_fieldValue", true))
                                            .name(new KioskCollectionField<String>("name_fieldName", "name_fieldValue", true))
                                            .location(new KioskCollectionField<LinkedCollectionReference>("location_fieldName", new LinkedCollectionReference(5L), true))
                                            .build();

    } */
}
