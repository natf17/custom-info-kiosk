package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
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
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.AmenityConverter;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.GenderConverter;

public class BathroomKioskCollectionAdapterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;
    CollectionLocalizedProperties cmsLocObj;
    CollectionSharedProperties cmsSharedObj;

    BathroomType kioskObj;



    

    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);
        this.testFeatImg = new Image("url", 1, 2);

        this.cmsSharedObj = new CollectionSharedPropertiesImpl.Builder("BATHROOM")
                                .collectionNameField(new CollectionNameField("", "Bathroom"))
                                .addTextField(new TextField(GenderConverter.GENDER_FIELD_TYPE, "", "gender_field_value"))
                                .addBooleanField(new BooleanField(AmenityConverter.ISWHEELCHAIRACC_FIELD_TYPE, "isWheelChairAccessible_fieldName", true))
                                .addLinkedCollectionField(new LinkedCollectionField(AmenityConverter.LOCATION_FIELD_TYPE, "location_fieldName", 5L))
                                .withId(1L)
                                .collectionSharedInternals(new CollectionSharedInternals(PageStatus.PUBLISHED, testDate, testDateTime))
                                .build();
        
        this.cmsLocObj = new CollectionLocalizedPropertiesImpl.Builder(1L)
                                .addTextField(new TextField(AmenityConverter.NAME_FIELD_TYPE, "", "name_fieldValue"))
                                .addTextField(new TextField(AmenityConverter.NOTE_FIELD_TYPE, "", "note_fieldValue"))
                                .addTextField(new TextField(AmenityConverter.SVGELEM_FIELD_TYPE, "", "6"))
                                .addImageField(new ImageField(AmenityConverter.FEATIMG_FIELD_TYPE, "", testFeatImg))
                                .collectionInternals(new CollectionInternals(enLocaleId, "en", PageStatus.PUBLISHED, testDate, testDateTime))
                                .build();
        


        AmenityType amenityType = new DefaultAmenityType.Builder()
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

        this.kioskObj = new DefaultBathroomType(amenityType, new GenderInfo(new KioskCollectionField<String>("gender_field_value", true)));

    }

    @Test
    public void givenValidCms_correctCmsGetters() {

        BathroomKioskCollectionAdapter brAdapter = new BathroomKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(cmsSharedObj.id(), brAdapter.id());
        Assertions.assertEquals(cmsSharedObj.withId(1L), brAdapter.withId(1L));
        Assertions.assertEquals(cmsSharedObj.type(), brAdapter.type());
        Assertions.assertEquals(cmsSharedObj.subType(), brAdapter.subType());
        Assertions.assertEquals(cmsSharedObj.collectionNameField(), brAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.numericFields(), brAdapter.numericFields());
        Assertions.assertEquals(cmsSharedObj.booleanFields(), brAdapter.booleanFields());
        Assertions.assertEquals(cmsSharedObj.textFields(), brAdapter.textFields());
        Assertions.assertEquals(cmsSharedObj.imageFields(), brAdapter.imageFields());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields(), brAdapter.linkedCollectionFields());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals(), brAdapter.collectionSharedInternals());

        Assertions.assertEquals(cmsLocObj.locWithId(2L), brAdapter.locWithId(2L));
        Assertions.assertEquals(cmsLocObj.locId(), brAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), brAdapter.locCollectionNameField());
        Assertions.assertEquals(cmsLocObj.locNumericFields(), brAdapter.locNumericFields());
        Assertions.assertEquals(cmsLocObj.locBooleanFields(), brAdapter.locBooleanFields());
        Assertions.assertEquals(cmsLocObj.locTextFields(), brAdapter.locTextFields());
        Assertions.assertEquals(cmsLocObj.locImageFields(), brAdapter.locImageFields());
        Assertions.assertEquals(cmsLocObj.parentId(), brAdapter.parentId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals(), brAdapter.locCollectionInternals());
 
    }

    @Test
    public void givenValidKiosk_correctCmsGetters() {

        BathroomKioskCollectionAdapter brAdapter = new BathroomKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(1L, brAdapter.id());
        Assertions.assertNotNull(brAdapter.withId(1L));
        Assertions.assertEquals("BATHROOM", brAdapter.type());
        Assertions.assertEquals(null, brAdapter.subType());
        Assertions.assertNotNull(brAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldType(), brAdapter.collectionNameField().getFieldType());
        Assertions.assertNotNull(brAdapter.collectionNameField().getFieldName());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldValue(), brAdapter.collectionNameField().getFieldValue());
        Assertions.assertEquals(cmsSharedObj.numericFields().size(), brAdapter.numericFields().size());
        Assertions.assertEquals(cmsSharedObj.booleanFields().size(), brAdapter.booleanFields().size());
        Assertions.assertEquals(cmsSharedObj.textFields().size(), brAdapter.textFields().size());
        Assertions.assertEquals(cmsSharedObj.imageFields().size(), brAdapter.imageFields().size());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields().size(), brAdapter.linkedCollectionFields().size());
        Assertions.assertNotNull(brAdapter.collectionSharedInternals());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getStatus(), brAdapter.collectionSharedInternals().getStatus());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getCreatedOn(), brAdapter.collectionSharedInternals().getCreatedOn());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getLastModified(), brAdapter.collectionSharedInternals().getLastModified());

        Assertions.assertNotNull(brAdapter.locWithId(2L));
        Assertions.assertNull(brAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), brAdapter.locCollectionNameField()); // both null
        Assertions.assertEquals(cmsLocObj.locNumericFields().size(), brAdapter.locNumericFields().size());
        Assertions.assertEquals(cmsLocObj.locBooleanFields().size(), brAdapter.locBooleanFields().size());
        Assertions.assertEquals(cmsLocObj.locTextFields().size(), brAdapter.locTextFields().size());
        Assertions.assertEquals(cmsLocObj.locImageFields().size(), brAdapter.locImageFields().size());
        Assertions.assertEquals(cmsLocObj.parentId(), brAdapter.parentId());
        Assertions.assertNotNull(brAdapter.locCollectionInternals());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocaleId(), brAdapter.locCollectionInternals().getKioskLocaleId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocale(), brAdapter.locCollectionInternals().getKioskLocale());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getCreatedOn(), brAdapter.locCollectionInternals().getCreatedOn());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getLastModified(), brAdapter.locCollectionInternals().getLastModified());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getStatus(), brAdapter.locCollectionInternals().getStatus());
 

        
    }

    @Test
    public void givenValidCms_correctKioskGetters() {

        BathroomKioskCollectionAdapter brAdapter = new BathroomKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(kioskObj.gender(), brAdapter.gender());
        Assertions.assertEquals(kioskObj.featImg(), brAdapter.featImg());
        Assertions.assertEquals(kioskObj.svgElemId(), brAdapter.svgElemId());
        Assertions.assertEquals(kioskObj.isWheelChairAccessible(), brAdapter.isWheelChairAccessible());
        Assertions.assertEquals(kioskObj.name(), brAdapter.name());
        Assertions.assertEquals(kioskObj.note(), brAdapter.note());
        Assertions.assertEquals(kioskObj.location(), brAdapter.location());
        Assertions.assertEquals(kioskObj.collectionId(), brAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), brAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), brAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), brAdapter.kioskCollectionMetadata());

    }

    @Test
    public void givenValidKiosk_correctKioskGetters() {
        BathroomKioskCollectionAdapter brAdapter = new BathroomKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(kioskObj.gender(), brAdapter.gender());
        Assertions.assertEquals(kioskObj.featImg(), brAdapter.featImg());
        Assertions.assertEquals(kioskObj.svgElemId(), brAdapter.svgElemId());
        Assertions.assertEquals(kioskObj.isWheelChairAccessible(), brAdapter.isWheelChairAccessible());
        Assertions.assertEquals(kioskObj.name(), brAdapter.name());
        Assertions.assertEquals(kioskObj.note(), brAdapter.note());
        Assertions.assertEquals(kioskObj.location(), brAdapter.location());
        Assertions.assertEquals(kioskObj.collectionId(), brAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), brAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), brAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), brAdapter.kioskCollectionMetadata());
    }



}
