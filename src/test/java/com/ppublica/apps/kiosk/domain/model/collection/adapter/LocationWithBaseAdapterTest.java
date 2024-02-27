package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.collection.LocationType;
import com.ppublica.apps.kiosk.domain.model.collection.LocationTypeImpl;

public class LocationWithBaseAdapterTest {

    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testMap;
    SimpleCollectionType cmsObj;

    LocationType kioskObj;
/*
    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2023, 9, 12);
        this.testDateTime = LocalDateTime.of(2023, 1, 9, 9, 30);
        this.testMap = new Image("url", 1, 2);

        this.cmsObj = new SimpleCollectionTypeImpl.Builder()
                                                .collectionNameField(new CollectionNameField("coll_nf_fieldName", "coll_nf_fieldValue"))
                                                .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                                .addTextField(new TextField(LocationConverter.FULLNAME_FIELD_TYPE, "fullName_fieldName", "fullName_fieldValue"))
                                                .addTextField(new TextField(LocationConverter.LEVELNAME_FIELD_TYPE, "levelName_fieldName", "levelName_fieldValue"))
                                                .addImageField(new ImageField(LocationConverter.MAP_FIELD_NAME_TYPE, "map_fieldName", testMap))
                                                .addNumericField(new NumericField(LocationConverter.LEVELNUM_FIELD_TYPE, "levelNum_fieldName", 3L))
                                                .type(LocationTypeImpl.KIOSK_COLLECTION_TYPE_NAME.toString())
                                                .withId(45L)
                                                .build();

        this.kioskObj = new LocationTypeImpl.Builder()
                                            .id(45L)
                                            .collectionNameField(new KioskCollectionField<String>("coll_nf_fieldName", "coll_nf_fieldValue", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                            .levelName(new KioskCollectionField<String>("levelName_fieldName", "levelName_fieldValue", true))
                                            .levelNum(new KioskCollectionField<Long>("levelNum_fieldName", 3L, true))
                                            .fullName(new KioskCollectionField<String>("fullName_fieldName", "fullName_fieldValue", true))
                                            .map(new KioskCollectionField<Image>("map_fieldName", testMap, true))
                                            .build();

    }
    
    @Test
    public void givenValidSimpleCollectionType_correctCmsGetters() {

        LocationWithBaseAdapter locationAdapter = new LocationWithBaseAdapter(cmsObj);


        Assertions.assertEquals(cmsObj.getId(), locationAdapter.getId());
        Assertions.assertEquals(cmsObj.getType(), locationAdapter.getType());

        CollectionInternals adapterCollInternals = locationAdapter.getCollectionInternals();
        CollectionInternals cmsObjCollInternals = cmsObj.getCollectionInternals();

        Assertions.assertEquals(cmsObjCollInternals.getKioskLocaleId(), adapterCollInternals.getKioskLocaleId());
        Assertions.assertEquals(cmsObjCollInternals.getStatus(), adapterCollInternals.getStatus());
        Assertions.assertEquals(cmsObjCollInternals.getCreatedOn(), adapterCollInternals.getCreatedOn());
        Assertions.assertEquals(cmsObjCollInternals.getLastModified(), adapterCollInternals.getLastModified());

        Assertions.assertEquals(cmsObj.getCollectionNameField().getFieldType(), locationAdapter.getCollectionNameField().getFieldType());
        Assertions.assertEquals(cmsObj.getCollectionNameField().getFieldName(), locationAdapter.getCollectionNameField().getFieldName());
        Assertions.assertEquals(cmsObj.getCollectionNameField().getFieldValue(), locationAdapter.getCollectionNameField().getFieldValue());

        Assertions.assertEquals(cmsObj.getImageFields().size(), locationAdapter.getImageFields().size());
        Assertions.assertEquals(cmsObj.getLinkedCollectionFields().size(), locationAdapter.getLinkedCollectionFields().size());
        Assertions.assertEquals(cmsObj.getNumericFields().size(), locationAdapter.getNumericFields().size());
        Assertions.assertEquals(cmsObj.getBooleanFields().size(), locationAdapter.getBooleanFields().size());
        Assertions.assertEquals(cmsObj.getTextFields().size(), locationAdapter.getTextFields().size());

    }

    @Test
    public void givenValidSimpleCollectionType_correctKioskGetters() {

        LocationWithBaseAdapter locationAdapter = new LocationWithBaseAdapter(cmsObj);

        Assertions.assertEquals(LocationTypeImpl.KIOSK_COLLECTION_TYPE_NAME, locationAdapter.getKioskCollectionTypeName());

        Assertions.assertEquals("coll_nf_fieldName", locationAdapter.getKioskCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_nf_fieldValue", locationAdapter.getKioskCollectionNameField().getFieldValue());

        KioskCollectionMetadata kioskMetadata = locationAdapter.getKioskCollectionMetadata();
        Assertions.assertEquals(enLocaleId, kioskMetadata.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, kioskMetadata.getStatus());
        Assertions.assertEquals(testDate, kioskMetadata.getCreatedOn());
        Assertions.assertEquals(testDateTime, kioskMetadata.getLastModified());

        Assertions.assertEquals("levelName_fieldName", locationAdapter.getLevelNameField().getFieldName());
        Assertions.assertEquals("levelName_fieldValue", locationAdapter.getLevelNameField().getFieldValue());

        Assertions.assertEquals("levelNum_fieldName", locationAdapter.getLevelNumField().getFieldName());
        Assertions.assertEquals(3L, locationAdapter.getLevelNumField().getFieldValue());

        Assertions.assertEquals("fullName_fieldName", locationAdapter.getFullNameField().getFieldName());
        Assertions.assertEquals("fullName_fieldValue", locationAdapter.getFullNameField().getFieldValue());

        Assertions.assertEquals("map_fieldName", locationAdapter.getMapField().getFieldName());
        Assertions.assertEquals(testMap, locationAdapter.getMapField().getFieldValue());


    }

    @Test
    public void givenValidLocation_correctCmsGetters() {

        LocationWithBaseAdapter locationAdapter = new LocationWithBaseAdapter(kioskObj);


        Assertions.assertEquals(45L, locationAdapter.getId());
        Assertions.assertEquals(LocationTypeImpl.KIOSK_COLLECTION_TYPE_NAME.toString(), locationAdapter.getType());

        CollectionInternals adapterCollInternals = locationAdapter.getCollectionInternals();

        Assertions.assertEquals(enLocaleId, adapterCollInternals.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, adapterCollInternals.getStatus());
        Assertions.assertEquals(testDate, adapterCollInternals.getCreatedOn());
        Assertions.assertEquals(testDateTime, adapterCollInternals.getLastModified());

        Assertions.assertNotNull(locationAdapter.getCollectionNameField().getFieldType());
        Assertions.assertEquals("coll_nf_fieldName", locationAdapter.getCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_nf_fieldValue", locationAdapter.getCollectionNameField().getFieldValue());

        List<ImageField> imageFields = locationAdapter.getImageFields();
        Assertions.assertEquals(1, imageFields.size());
        Assertions.assertEquals(LocationConverter.MAP_FIELD_NAME_TYPE, imageFields.get(0).getFieldType());
        Assertions.assertEquals("map_fieldName", imageFields.get(0).getFieldName());
        Assertions.assertEquals(testMap, imageFields.get(0).getFieldValue());

        List<TextField> textFields = locationAdapter.getTextFields();
        Assertions.assertEquals(2, textFields.size());
        
        TextField levelNameTextField = textFields.get(0).getFieldType().equals(LocationConverter.LEVELNAME_FIELD_TYPE) ? textFields.get(0) : textFields.get(1);
        Assertions.assertEquals(LocationConverter.LEVELNAME_FIELD_TYPE, levelNameTextField.getFieldType());
        Assertions.assertEquals("levelName_fieldName", levelNameTextField.getFieldName());
        Assertions.assertEquals("levelName_fieldValue", levelNameTextField.getFieldValue());

        TextField fullNameTextField = textFields.get(0).getFieldType().equals(LocationConverter.FULLNAME_FIELD_TYPE) ? textFields.get(0) : textFields.get(1);
        Assertions.assertEquals(LocationConverter.FULLNAME_FIELD_TYPE, fullNameTextField.getFieldType());
        Assertions.assertEquals("fullName_fieldName", fullNameTextField.getFieldName());
        Assertions.assertEquals("fullName_fieldValue", fullNameTextField.getFieldValue());

        List<NumericField> numericFields = locationAdapter.getNumericFields();
        Assertions.assertEquals(1, numericFields.size());
        Assertions.assertEquals(LocationConverter.LEVELNUM_FIELD_TYPE, numericFields.get(0).getFieldType());
        Assertions.assertEquals("levelNum_fieldName", numericFields.get(0).getFieldName());
        Assertions.assertEquals(3L, numericFields.get(0).getFieldValue());

        Assertions.assertTrue(locationAdapter.getLinkedCollectionFields().isEmpty());
        Assertions.assertTrue(locationAdapter.getBooleanFields().isEmpty());

    }

    @Test
    public void givenValidLocation_correctKioskGetters() {
        LocationWithBaseAdapter locationAdapter = new LocationWithBaseAdapter(kioskObj);

        Assertions.assertEquals(kioskObj.getKioskCollectionTypeName(), locationAdapter.getKioskCollectionTypeName());

        Assertions.assertEquals(kioskObj.getKioskCollectionNameField().getFieldName(), locationAdapter.getKioskCollectionNameField().getFieldName());
        Assertions.assertEquals(kioskObj.getKioskCollectionNameField().getFieldValue(), locationAdapter.getKioskCollectionNameField().getFieldValue());

        KioskCollectionMetadata adapterKioskMetadata = locationAdapter.getKioskCollectionMetadata();
        KioskCollectionMetadata kioskRepKioskMetadata = kioskObj.getKioskCollectionMetadata();
        Assertions.assertEquals(kioskRepKioskMetadata.getKioskLocaleId(), adapterKioskMetadata.getKioskLocaleId());
        Assertions.assertEquals(kioskRepKioskMetadata.getStatus(), adapterKioskMetadata.getStatus());
        Assertions.assertEquals(kioskRepKioskMetadata.getCreatedOn(), adapterKioskMetadata.getCreatedOn());
        Assertions.assertEquals(kioskRepKioskMetadata.getLastModified(), adapterKioskMetadata.getLastModified());

        Assertions.assertEquals(kioskObj.getLevelNameField().getFieldName(), locationAdapter.getLevelNameField().getFieldName());
        Assertions.assertEquals(kioskObj.getLevelNameField().getFieldValue(), locationAdapter.getLevelNameField().getFieldValue());

        Assertions.assertEquals(kioskObj.getLevelNumField().getFieldName(), locationAdapter.getLevelNumField().getFieldName());
        Assertions.assertEquals(kioskObj.getLevelNumField().getFieldValue(), locationAdapter.getLevelNumField().getFieldValue());

        Assertions.assertEquals(kioskObj.getFullNameField().getFieldName(), locationAdapter.getFullNameField().getFieldName());
        Assertions.assertEquals(kioskObj.getFullNameField().getFieldValue(), locationAdapter.getFullNameField().getFieldValue());

        Assertions.assertEquals(kioskObj.getMapField().getFieldName(), locationAdapter.getMapField().getFieldName());
        Assertions.assertEquals(kioskObj.getMapField().getFieldValue(), locationAdapter.getMapField().getFieldValue());

    } */
}
