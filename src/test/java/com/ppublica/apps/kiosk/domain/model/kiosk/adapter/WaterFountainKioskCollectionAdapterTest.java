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
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultWaterFountainType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EmptyWaterFountainPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.domain.model.kiosk.WaterFountainType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.AmenityConverter;

public class WaterFountainKioskCollectionAdapterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;
    CollectionLocalizedProperties cmsLocObj;
    CollectionSharedProperties cmsSharedObj;

    WaterFountainType kioskObj;



    

    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);
        this.testFeatImg = new Image("url", 1, 2);

        this.cmsSharedObj = new CollectionSharedPropertiesImpl.Builder("WATER_FOUNTAIN")
                                .collectionNameField(new CollectionNameField("", "Water fountain"))
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
                                            .collectionNameField(new KioskCollectionField<String>("Water fountain", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .featImg(new KioskCollectionField<KioskImage>(new KioskImage("url", 1, 2), true))
                                            .svgElemId(new KioskCollectionField<String>("6", true))
                                            .isWheelChairAccessible(new KioskCollectionField<Boolean>(true, true))
                                            .note(new KioskCollectionField<String>("note_fieldValue", true))
                                            .name(new KioskCollectionField<String>("name_fieldValue", true))
                                            .location(new KioskCollectionField<LinkedCollectionReference>(new LinkedCollectionReference(5L), true))
                                            .build();

        this.kioskObj = new DefaultWaterFountainType(amenityType, new EmptyWaterFountainPiece());

    }

    @Test
    public void givenValidCms_correctCmsGetters() {

        WaterFountainKioskCollectionAdapter wAdapter = new WaterFountainKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(cmsSharedObj.id(), wAdapter.id());
        Assertions.assertEquals(cmsSharedObj.withId(1L), wAdapter.withId(1L));
        Assertions.assertEquals(cmsSharedObj.type(), wAdapter.type());
        Assertions.assertEquals(cmsSharedObj.subType(), wAdapter.subType());
        Assertions.assertEquals(cmsSharedObj.collectionNameField(), wAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.numericFields(), wAdapter.numericFields());
        Assertions.assertEquals(cmsSharedObj.booleanFields(), wAdapter.booleanFields());
        Assertions.assertEquals(cmsSharedObj.textFields(), wAdapter.textFields());
        Assertions.assertEquals(cmsSharedObj.imageFields(), wAdapter.imageFields());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields(), wAdapter.linkedCollectionFields());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals(), wAdapter.collectionSharedInternals());

        Assertions.assertEquals(cmsLocObj.locWithId(2L), wAdapter.locWithId(2L));
        Assertions.assertEquals(cmsLocObj.locId(), wAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), wAdapter.locCollectionNameField());
        Assertions.assertEquals(cmsLocObj.locNumericFields(), wAdapter.locNumericFields());
        Assertions.assertEquals(cmsLocObj.locBooleanFields(), wAdapter.locBooleanFields());
        Assertions.assertEquals(cmsLocObj.locTextFields(), wAdapter.locTextFields());
        Assertions.assertEquals(cmsLocObj.locImageFields(), wAdapter.locImageFields());
        Assertions.assertEquals(cmsLocObj.parentId(), wAdapter.parentId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals(), wAdapter.locCollectionInternals());
 
    }

    @Test
    public void givenValidKiosk_correctCmsGetters() {

        WaterFountainKioskCollectionAdapter wAdapter = new WaterFountainKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(1L, wAdapter.id());
        Assertions.assertNotNull(wAdapter.withId(1L));
        Assertions.assertEquals("WATER_FOUNTAIN", wAdapter.type());
        Assertions.assertEquals(null, wAdapter.subType());
        Assertions.assertNotNull(wAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldType(), wAdapter.collectionNameField().getFieldType());
        Assertions.assertNotNull(wAdapter.collectionNameField().getFieldName());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldValue(), wAdapter.collectionNameField().getFieldValue());
        Assertions.assertEquals(cmsSharedObj.numericFields().size(), wAdapter.numericFields().size());
        Assertions.assertEquals(cmsSharedObj.booleanFields().size(), wAdapter.booleanFields().size());
        Assertions.assertEquals(cmsSharedObj.textFields().size(), wAdapter.textFields().size());
        Assertions.assertEquals(cmsSharedObj.imageFields().size(), wAdapter.imageFields().size());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields().size(), wAdapter.linkedCollectionFields().size());
        Assertions.assertNotNull(wAdapter.collectionSharedInternals());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getStatus(), wAdapter.collectionSharedInternals().getStatus());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getCreatedOn(), wAdapter.collectionSharedInternals().getCreatedOn());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getLastModified(), wAdapter.collectionSharedInternals().getLastModified());

        Assertions.assertNotNull(wAdapter.locWithId(2L));
        Assertions.assertNull(wAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), wAdapter.locCollectionNameField()); // both null
        Assertions.assertEquals(cmsLocObj.locNumericFields().size(), wAdapter.locNumericFields().size());
        Assertions.assertEquals(cmsLocObj.locBooleanFields().size(), wAdapter.locBooleanFields().size());
        Assertions.assertEquals(cmsLocObj.locTextFields().size(), wAdapter.locTextFields().size());
        Assertions.assertEquals(cmsLocObj.locImageFields().size(), wAdapter.locImageFields().size());
        Assertions.assertEquals(cmsLocObj.parentId(), wAdapter.parentId());
        Assertions.assertNotNull(wAdapter.locCollectionInternals());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocaleId(), wAdapter.locCollectionInternals().getKioskLocaleId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocale(), wAdapter.locCollectionInternals().getKioskLocale());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getCreatedOn(), wAdapter.locCollectionInternals().getCreatedOn());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getLastModified(), wAdapter.locCollectionInternals().getLastModified());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getStatus(), wAdapter.locCollectionInternals().getStatus());
 

        
    }

    @Test
    public void givenValidCms_correctKioskGetters() {

        WaterFountainKioskCollectionAdapter wAdapter = new WaterFountainKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(kioskObj.featImg(), wAdapter.featImg());
        Assertions.assertEquals(kioskObj.svgElemId(), wAdapter.svgElemId());
        Assertions.assertEquals(kioskObj.isWheelChairAccessible(), wAdapter.isWheelChairAccessible());
        Assertions.assertEquals(kioskObj.name(), wAdapter.name());
        Assertions.assertEquals(kioskObj.note(), wAdapter.note());
        Assertions.assertEquals(kioskObj.location(), wAdapter.location());
        Assertions.assertEquals(kioskObj.collectionId(), wAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), wAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), wAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), wAdapter.kioskCollectionMetadata());

    }

    @Test
    public void givenValidKiosk_correctKioskGetters() {
        WaterFountainKioskCollectionAdapter wAdapter = new WaterFountainKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(kioskObj.featImg(), wAdapter.featImg());
        Assertions.assertEquals(kioskObj.svgElemId(), wAdapter.svgElemId());
        Assertions.assertEquals(kioskObj.isWheelChairAccessible(), wAdapter.isWheelChairAccessible());
        Assertions.assertEquals(kioskObj.name(), wAdapter.name());
        Assertions.assertEquals(kioskObj.note(), wAdapter.note());
        Assertions.assertEquals(kioskObj.location(), wAdapter.location());
        Assertions.assertEquals(kioskObj.collectionId(), wAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), wAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), wAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), wAdapter.kioskCollectionMetadata());
    }
}
