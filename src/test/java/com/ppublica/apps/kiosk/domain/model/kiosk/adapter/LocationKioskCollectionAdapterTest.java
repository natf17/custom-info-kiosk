package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultLocationType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LocationType;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.LocationInfoConverter;

public class LocationKioskCollectionAdapterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testMapImg;
    CollectionLocalizedProperties cmsLocObj;
    CollectionSharedProperties cmsSharedObj;

    LocationType kioskObj;



    

    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);
        this.testMapImg = new Image("url", 1, 2);

        this.cmsSharedObj = new CollectionSharedPropertiesImpl.Builder("LOCATION")
                                .collectionNameField(new CollectionNameField("", "Location"))
                                .addNumericField(new NumericField(LocationInfoConverter.LEVELNUM_FIELD_TYPE, "", 5L))
                                .withId(1L)
                                .collectionSharedInternals(new CollectionSharedInternals(PageStatus.PUBLISHED, testDate, testDateTime))
                                .build();
        
        this.cmsLocObj = new CollectionLocalizedPropertiesImpl.Builder(1L)
                                .addTextField(new TextField(LocationInfoConverter.LEVELNAME_FIELD_TYPE, "", "levelName_fieldValue"))
                                .addTextField(new TextField(LocationInfoConverter.FULLNAME_FIELD_TYPE, "", "fullName_fieldValue"))
                                .addImageField(new ImageField(LocationInfoConverter.MAP_FIELD_NAME_TYPE, "", testMapImg))
                                .collectionInternals(new CollectionInternals(enLocaleId, "en", PageStatus.PUBLISHED, testDate, testDateTime))
                                .build();
        


        this.kioskObj = new DefaultLocationType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Location", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .map(new KioskCollectionField<KioskImage>(new KioskImage("url", 1, 2), true))
                                            .level_name(new KioskCollectionField<String>("levelName_fieldValue", true))
                                            .level_num(new KioskCollectionField<Integer>(5, false))
                                            .fullName(new KioskCollectionField<String>("fullName_fieldValue", true))
                                            .build();

    }

    @Test
    public void givenValidCms_correctCmsGetters() {

        LocationKioskCollectionAdapter locationAdapter = new LocationKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(cmsSharedObj.id(), locationAdapter.id());
        Assertions.assertEquals(cmsSharedObj.withId(1L), locationAdapter.withId(1L));
        Assertions.assertEquals(cmsSharedObj.type(), locationAdapter.type());
        Assertions.assertEquals(cmsSharedObj.subType(), locationAdapter.subType());
        Assertions.assertEquals(cmsSharedObj.collectionNameField(), locationAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.numericFields(), locationAdapter.numericFields());
        Assertions.assertEquals(cmsSharedObj.booleanFields(), locationAdapter.booleanFields());
        Assertions.assertEquals(cmsSharedObj.textFields(), locationAdapter.textFields());
        Assertions.assertEquals(cmsSharedObj.imageFields(), locationAdapter.imageFields());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields(), locationAdapter.linkedCollectionFields());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals(), locationAdapter.collectionSharedInternals());

        Assertions.assertEquals(cmsLocObj.locWithId(2L), locationAdapter.locWithId(2L));
        Assertions.assertEquals(cmsLocObj.locId(), locationAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), locationAdapter.locCollectionNameField());
        Assertions.assertEquals(cmsLocObj.locNumericFields(), locationAdapter.locNumericFields());
        Assertions.assertEquals(cmsLocObj.locBooleanFields(), locationAdapter.locBooleanFields());
        Assertions.assertEquals(cmsLocObj.locTextFields(), locationAdapter.locTextFields());
        Assertions.assertEquals(cmsLocObj.locImageFields(), locationAdapter.locImageFields());
        Assertions.assertEquals(cmsLocObj.parentId(), locationAdapter.parentId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals(), locationAdapter.locCollectionInternals());
 
    }

    @Test
    public void givenValidKiosk_correctCmsGetters() {

        LocationKioskCollectionAdapter locationAdapter = new LocationKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(1L, locationAdapter.id());
        Assertions.assertNotNull(locationAdapter.withId(1L));
        Assertions.assertEquals("LOCATION", locationAdapter.type());
        Assertions.assertEquals(null, locationAdapter.subType());
        Assertions.assertNotNull(locationAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldType(), locationAdapter.collectionNameField().getFieldType());
        Assertions.assertNotNull(locationAdapter.collectionNameField().getFieldName());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldValue(), locationAdapter.collectionNameField().getFieldValue());
        Assertions.assertEquals(cmsSharedObj.numericFields().size(), locationAdapter.numericFields().size());
        Assertions.assertEquals(cmsSharedObj.booleanFields().size(), locationAdapter.booleanFields().size());
        Assertions.assertEquals(cmsSharedObj.textFields().size(), locationAdapter.textFields().size());
        Assertions.assertEquals(cmsSharedObj.imageFields().size(), locationAdapter.imageFields().size());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields().size(), locationAdapter.linkedCollectionFields().size());
        Assertions.assertNotNull(locationAdapter.collectionSharedInternals());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getStatus(), locationAdapter.collectionSharedInternals().getStatus());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getCreatedOn(), locationAdapter.collectionSharedInternals().getCreatedOn());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getLastModified(), locationAdapter.collectionSharedInternals().getLastModified());

        Assertions.assertNotNull(locationAdapter.locWithId(2L));
        Assertions.assertNull(locationAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), locationAdapter.locCollectionNameField()); // both null
        Assertions.assertEquals(cmsLocObj.locNumericFields().size(), locationAdapter.locNumericFields().size());
        Assertions.assertEquals(cmsLocObj.locBooleanFields().size(), locationAdapter.locBooleanFields().size());
        Assertions.assertEquals(cmsLocObj.locTextFields().size(), locationAdapter.locTextFields().size());
        Assertions.assertEquals(cmsLocObj.locImageFields().size(), locationAdapter.locImageFields().size());
        Assertions.assertEquals(cmsLocObj.parentId(), locationAdapter.parentId());
        Assertions.assertNotNull(locationAdapter.locCollectionInternals());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocaleId(), locationAdapter.locCollectionInternals().getKioskLocaleId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocale(), locationAdapter.locCollectionInternals().getKioskLocale());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getCreatedOn(), locationAdapter.locCollectionInternals().getCreatedOn());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getLastModified(), locationAdapter.locCollectionInternals().getLastModified());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getStatus(), locationAdapter.locCollectionInternals().getStatus());
 

        
    }

    @Test
    public void givenValidCms_correctKioskGetters() {

        LocationKioskCollectionAdapter locationAdapter = new LocationKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(kioskObj.level_name(), locationAdapter.level_name());
        Assertions.assertEquals(kioskObj.level_num(), locationAdapter.level_num());
        Assertions.assertEquals(kioskObj.map(), locationAdapter.map());
        Assertions.assertEquals(kioskObj.fullName(), locationAdapter.fullName());
        Assertions.assertEquals(kioskObj.collectionId(), locationAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), locationAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), locationAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), locationAdapter.kioskCollectionMetadata());

    }

    @Test
    public void givenValidKiosk_correctKioskGetters() {
        LocationKioskCollectionAdapter locationAdapter = new LocationKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(kioskObj.level_name(), locationAdapter.level_name());
        Assertions.assertEquals(kioskObj.level_num(), locationAdapter.level_num());
        Assertions.assertEquals(kioskObj.map(), locationAdapter.map());
        Assertions.assertEquals(kioskObj.fullName(), locationAdapter.fullName());
        Assertions.assertEquals(kioskObj.collectionId(), locationAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), locationAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), locationAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), locationAdapter.kioskCollectionMetadata());
    }
}
