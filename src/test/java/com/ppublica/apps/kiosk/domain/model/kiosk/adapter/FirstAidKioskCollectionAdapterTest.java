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
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultFirstAidType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EmptyFirstAidPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.FirstAidType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.AmenityConverter;

public class FirstAidKioskCollectionAdapterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;
    CollectionLocalizedProperties cmsLocObj;
    CollectionSharedProperties cmsSharedObj;

    FirstAidType kioskObj;



    

    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);
        this.testFeatImg = new Image("url", 1, 2);

        this.cmsSharedObj = new CollectionSharedPropertiesImpl.Builder("FIRST_AID")
                                .collectionNameField(new CollectionNameField("", "First Aid"))
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
                                            .collectionNameField(new KioskCollectionField<String>("First Aid", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .featImg(new KioskCollectionField<KioskImage>(new KioskImage("url", 1, 2), true))
                                            .svgElemId(new KioskCollectionField<String>("6", true))
                                            .isWheelChairAccessible(new KioskCollectionField<Boolean>(true, true))
                                            .note(new KioskCollectionField<String>("note_fieldValue", true))
                                            .name(new KioskCollectionField<String>("name_fieldValue", true))
                                            .location(new KioskCollectionField<LinkedCollectionReference>(new LinkedCollectionReference(5L), true))
                                            .build();

        this.kioskObj = new DefaultFirstAidType(amenityType, new EmptyFirstAidPiece());

    }

    @Test
    public void givenValidCms_correctCmsGetters() {

        FirstAidKioskCollectionAdapter fAdapter = new FirstAidKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(cmsSharedObj.id(), fAdapter.id());
        Assertions.assertEquals(cmsSharedObj.withId(1L), fAdapter.withId(1L));
        Assertions.assertEquals(cmsSharedObj.type(), fAdapter.type());
        Assertions.assertEquals(cmsSharedObj.subType(), fAdapter.subType());
        Assertions.assertEquals(cmsSharedObj.collectionNameField(), fAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.numericFields(), fAdapter.numericFields());
        Assertions.assertEquals(cmsSharedObj.booleanFields(), fAdapter.booleanFields());
        Assertions.assertEquals(cmsSharedObj.textFields(), fAdapter.textFields());
        Assertions.assertEquals(cmsSharedObj.imageFields(), fAdapter.imageFields());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields(), fAdapter.linkedCollectionFields());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals(), fAdapter.collectionSharedInternals());

        Assertions.assertEquals(cmsLocObj.locWithId(2L), fAdapter.locWithId(2L));
        Assertions.assertEquals(cmsLocObj.locId(), fAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), fAdapter.locCollectionNameField());
        Assertions.assertEquals(cmsLocObj.locNumericFields(), fAdapter.locNumericFields());
        Assertions.assertEquals(cmsLocObj.locBooleanFields(), fAdapter.locBooleanFields());
        Assertions.assertEquals(cmsLocObj.locTextFields(), fAdapter.locTextFields());
        Assertions.assertEquals(cmsLocObj.locImageFields(), fAdapter.locImageFields());
        Assertions.assertEquals(cmsLocObj.parentId(), fAdapter.parentId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals(), fAdapter.locCollectionInternals());
 
    }

    @Test
    public void givenValidKiosk_correctCmsGetters() {

        FirstAidKioskCollectionAdapter fAdapter = new FirstAidKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(1L, fAdapter.id());
        Assertions.assertNotNull(fAdapter.withId(1L));
        Assertions.assertEquals("FIRST_AID", fAdapter.type());
        Assertions.assertEquals(null, fAdapter.subType());
        Assertions.assertNotNull(fAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldType(), fAdapter.collectionNameField().getFieldType());
        Assertions.assertNotNull(fAdapter.collectionNameField().getFieldName());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldValue(), fAdapter.collectionNameField().getFieldValue());
        Assertions.assertEquals(cmsSharedObj.numericFields().size(), fAdapter.numericFields().size());
        Assertions.assertEquals(cmsSharedObj.booleanFields().size(), fAdapter.booleanFields().size());
        Assertions.assertEquals(cmsSharedObj.textFields().size(), fAdapter.textFields().size());
        Assertions.assertEquals(cmsSharedObj.imageFields().size(), fAdapter.imageFields().size());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields().size(), fAdapter.linkedCollectionFields().size());
        Assertions.assertNotNull(fAdapter.collectionSharedInternals());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getStatus(), fAdapter.collectionSharedInternals().getStatus());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getCreatedOn(), fAdapter.collectionSharedInternals().getCreatedOn());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getLastModified(), fAdapter.collectionSharedInternals().getLastModified());

        Assertions.assertNotNull(fAdapter.locWithId(2L));
        Assertions.assertNull(fAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), fAdapter.locCollectionNameField()); // both null
        Assertions.assertEquals(cmsLocObj.locNumericFields().size(), fAdapter.locNumericFields().size());
        Assertions.assertEquals(cmsLocObj.locBooleanFields().size(), fAdapter.locBooleanFields().size());
        Assertions.assertEquals(cmsLocObj.locTextFields().size(), fAdapter.locTextFields().size());
        Assertions.assertEquals(cmsLocObj.locImageFields().size(), fAdapter.locImageFields().size());
        Assertions.assertEquals(cmsLocObj.parentId(), fAdapter.parentId());
        Assertions.assertNotNull(fAdapter.locCollectionInternals());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocaleId(), fAdapter.locCollectionInternals().getKioskLocaleId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocale(), fAdapter.locCollectionInternals().getKioskLocale());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getCreatedOn(), fAdapter.locCollectionInternals().getCreatedOn());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getLastModified(), fAdapter.locCollectionInternals().getLastModified());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getStatus(), fAdapter.locCollectionInternals().getStatus());
 

        
    }

    @Test
    public void givenValidCms_correctKioskGetters() {

        FirstAidKioskCollectionAdapter fAdapter = new FirstAidKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(kioskObj.featImg(), fAdapter.featImg());
        Assertions.assertEquals(kioskObj.svgElemId(), fAdapter.svgElemId());
        Assertions.assertEquals(kioskObj.isWheelChairAccessible(), fAdapter.isWheelChairAccessible());
        Assertions.assertEquals(kioskObj.name(), fAdapter.name());
        Assertions.assertEquals(kioskObj.note(), fAdapter.note());
        Assertions.assertEquals(kioskObj.location(), fAdapter.location());
        Assertions.assertEquals(kioskObj.collectionId(), fAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), fAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), fAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), fAdapter.kioskCollectionMetadata());

    }

    @Test
    public void givenValidKiosk_correctKioskGetters() {
        FirstAidKioskCollectionAdapter fAdapter = new FirstAidKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(kioskObj.featImg(), fAdapter.featImg());
        Assertions.assertEquals(kioskObj.svgElemId(), fAdapter.svgElemId());
        Assertions.assertEquals(kioskObj.isWheelChairAccessible(), fAdapter.isWheelChairAccessible());
        Assertions.assertEquals(kioskObj.name(), fAdapter.name());
        Assertions.assertEquals(kioskObj.note(), fAdapter.note());
        Assertions.assertEquals(kioskObj.location(), fAdapter.location());
        Assertions.assertEquals(kioskObj.collectionId(), fAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), fAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), fAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), fAdapter.kioskCollectionMetadata());
    }
}
