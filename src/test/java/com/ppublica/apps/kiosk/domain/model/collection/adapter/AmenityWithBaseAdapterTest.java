package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
import com.ppublica.apps.kiosk.domain.model.collection.AmenityType;
import com.ppublica.apps.kiosk.domain.model.collection.DonationTypeImpl;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.collection.LinkedCollectionReference;

public class AmenityWithBaseAdapterTest {

    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;
    SimpleCollectionType cmsObj;

    AmenityType kioskObj;

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

    }
    
    @Test
    public void givenValidSimpleCollectionType_correctCmsGetters() {

        AmenityWithBaseAdapter amenityAdapter = new AmenityWithBaseAdapter(cmsObj);

        Assertions.assertEquals(cmsObj.getId(), amenityAdapter.getId());
        Assertions.assertEquals(cmsObj.getType(), amenityAdapter.getType());

        CollectionInternals adapterCollInternals = amenityAdapter.getCollectionInternals();
        CollectionInternals cmsObjCollInternals = cmsObj.getCollectionInternals();

        Assertions.assertEquals(cmsObjCollInternals.getKioskLocaleId(), adapterCollInternals.getKioskLocaleId());
        Assertions.assertEquals(cmsObjCollInternals.getStatus(), adapterCollInternals.getStatus());
        Assertions.assertEquals(cmsObjCollInternals.getCreatedOn(), adapterCollInternals.getCreatedOn());
        Assertions.assertEquals(cmsObjCollInternals.getLastModified(), adapterCollInternals.getLastModified());

        Assertions.assertEquals(cmsObj.getCollectionNameField().getFieldType(), amenityAdapter.getCollectionNameField().getFieldType());
        Assertions.assertEquals(cmsObj.getCollectionNameField().getFieldName(), amenityAdapter.getCollectionNameField().getFieldName());
        Assertions.assertEquals(cmsObj.getCollectionNameField().getFieldValue(), amenityAdapter.getCollectionNameField().getFieldValue());

        Assertions.assertEquals(cmsObj.getImageFields().size(), amenityAdapter.getImageFields().size());
        Assertions.assertEquals(cmsObj.getLinkedCollectionFields().size(), amenityAdapter.getLinkedCollectionFields().size());
        Assertions.assertEquals(cmsObj.getNumericFields().size(), amenityAdapter.getNumericFields().size());
        Assertions.assertEquals(cmsObj.getBooleanFields().size(), amenityAdapter.getBooleanFields().size());
        Assertions.assertEquals(cmsObj.getTextFields().size(), amenityAdapter.getTextFields().size());

    }

    
    @Test
    public void givenValidSimpleCollectionType_correctKioskGetters() {

        AmenityWithBaseAdapter amenityAdapter = new AmenityWithBaseAdapter(cmsObj);

        Assertions.assertEquals(DonationTypeImpl.KIOSK_COLLECTION_TYPE_NAME, amenityAdapter.getKioskCollectionTypeName());

        Assertions.assertEquals("coll_name_fieldName", amenityAdapter.getKioskCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_name_fieldValue", amenityAdapter.getKioskCollectionNameField().getFieldValue());

        KioskCollectionMetadata kioskMetadata = amenityAdapter.getKioskCollectionMetadata();
        Assertions.assertEquals(enLocaleId, kioskMetadata.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, kioskMetadata.getStatus());
        Assertions.assertEquals(testDate, kioskMetadata.getCreatedOn());
        Assertions.assertEquals(testDateTime, kioskMetadata.getLastModified());

        Assertions.assertEquals("featImg_fieldName", amenityAdapter.getFeatImg().getFieldName());
        Assertions.assertEquals(testFeatImg, amenityAdapter.getFeatImg().getFieldValue());

        Assertions.assertEquals("svgElemId_fieldName", amenityAdapter.getSvgElemId().getFieldName());
        Assertions.assertEquals(6L, amenityAdapter.getSvgElemId().getFieldValue());

        Assertions.assertEquals("isWheelChairAccessible_fieldName", amenityAdapter.isWheelChairAccessible().getFieldName());
        Assertions.assertEquals(true, amenityAdapter.isWheelChairAccessible().getFieldValue());

        Assertions.assertEquals("note_fieldName", amenityAdapter.getNote().getFieldName());
        Assertions.assertEquals("note_fieldValue", amenityAdapter.getNote().getFieldValue());

        Assertions.assertEquals("name_fieldName", amenityAdapter.getName().getFieldName());
        Assertions.assertEquals("name_fieldValue", amenityAdapter.getName().getFieldValue());

        Assertions.assertEquals("location_fieldName", amenityAdapter.getLocation().getFieldName());
        Assertions.assertEquals(5L, amenityAdapter.getLocation().getFieldValue().getLinkedCollectionId());

    }

    @Test
    public void givenValidAmenity_correctCmsGetters() {

        AmenityWithBaseAdapter amenityAdapter = new AmenityWithBaseAdapter(kioskObj);

        Assertions.assertEquals(2L, amenityAdapter.getId());
        Assertions.assertEquals(DonationTypeImpl.KIOSK_COLLECTION_TYPE_NAME.toString(), amenityAdapter.getType());

        CollectionInternals adapterCollInternals = amenityAdapter.getCollectionInternals();

        Assertions.assertEquals(enLocaleId, adapterCollInternals.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, adapterCollInternals.getStatus());
        Assertions.assertEquals(testDate, adapterCollInternals.getCreatedOn());
        Assertions.assertEquals(testDateTime, adapterCollInternals.getLastModified());
    

        Assertions.assertNotNull(amenityAdapter.getCollectionNameField().getFieldType());
        Assertions.assertEquals("coll_name_fieldName", amenityAdapter.getCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_name_fieldValue", amenityAdapter.getCollectionNameField().getFieldValue());

        
        List<ImageField> imageFields = amenityAdapter.getImageFields();
        Assertions.assertEquals(1, imageFields.size());
        Assertions.assertEquals(AmenityConverter.FEATIMG_FIELD_TYPE, imageFields.get(0).getFieldType());
        Assertions.assertEquals("featImg_fieldName", imageFields.get(0).getFieldName());
        Assertions.assertEquals(testFeatImg, imageFields.get(0).getFieldValue());

        List<TextField> textFields = amenityAdapter.getTextFields();
        Assertions.assertEquals(2, textFields.size());
        
        TextField levelNameTextField = textFields.get(0).getFieldType().equals(AmenityConverter.NAME_FIELD_TYPE) ? textFields.get(0) : textFields.get(1);
        Assertions.assertEquals(AmenityConverter.NAME_FIELD_TYPE, levelNameTextField.getFieldType());
        Assertions.assertEquals("name_fieldName", levelNameTextField.getFieldName());
        Assertions.assertEquals("name_fieldValue", levelNameTextField.getFieldValue());

        TextField fullNameTextField = textFields.get(0).getFieldType().equals(AmenityConverter.NOTE_FIELD_TYPE) ? textFields.get(0) : textFields.get(1);
        Assertions.assertEquals(AmenityConverter.NOTE_FIELD_TYPE, fullNameTextField.getFieldType());
        Assertions.assertEquals("note_fieldName", fullNameTextField.getFieldName());
        Assertions.assertEquals("note_fieldValue", fullNameTextField.getFieldValue());

        List<NumericField> numericFields = amenityAdapter.getNumericFields();
        Assertions.assertEquals(1, numericFields.size());
        Assertions.assertEquals(AmenityConverter.SVGELEM_FIELD_TYPE, numericFields.get(0).getFieldType());
        Assertions.assertEquals("svgElemId_fieldName", numericFields.get(0).getFieldName());
        Assertions.assertEquals(6L, numericFields.get(0).getFieldValue());

        List<BooleanField> booleanFields = amenityAdapter.getBooleanFields();
        Assertions.assertEquals(1, booleanFields.size());
        Assertions.assertEquals(AmenityConverter.ISWHEELCHAIRACC_FIELD_TYPE, booleanFields.get(0).getFieldType());
        Assertions.assertEquals("isWheelChairAccessible_fieldName", booleanFields.get(0).getFieldName());
        Assertions.assertEquals(true, booleanFields.get(0).getFieldValue());

        List<LinkedCollectionField> linkedCollectionFields = amenityAdapter.getLinkedCollectionFields();
        Assertions.assertEquals(1, linkedCollectionFields.size());
        Assertions.assertEquals(AmenityConverter.LOCATION_FIELD_TYPE, linkedCollectionFields.get(0).getFieldType());
        Assertions.assertEquals("location_fieldName", linkedCollectionFields.get(0).getFieldName());
        Assertions.assertEquals(5L, linkedCollectionFields.get(0).getFieldValue());


    }

    @Test
    public void givenValidAmenity_correctKioskGetters() {
        AmenityWithBaseAdapter amenityAdapter = new AmenityWithBaseAdapter(kioskObj);

        Assertions.assertEquals(kioskObj.getKioskCollectionTypeName(), amenityAdapter.getKioskCollectionTypeName());

        Assertions.assertEquals(kioskObj.getKioskCollectionNameField().getFieldName(), amenityAdapter.getKioskCollectionNameField().getFieldName());
        Assertions.assertEquals(kioskObj.getKioskCollectionNameField().getFieldValue(), amenityAdapter.getKioskCollectionNameField().getFieldValue());

        KioskCollectionMetadata adapterKioskMetadata = amenityAdapter.getKioskCollectionMetadata();
        KioskCollectionMetadata kioskRepKioskMetadata = kioskObj.getKioskCollectionMetadata();
        Assertions.assertEquals(kioskRepKioskMetadata.getKioskLocaleId(), adapterKioskMetadata.getKioskLocaleId());
        Assertions.assertEquals(kioskRepKioskMetadata.getStatus(), adapterKioskMetadata.getStatus());
        Assertions.assertEquals(kioskRepKioskMetadata.getCreatedOn(), adapterKioskMetadata.getCreatedOn());
        Assertions.assertEquals(kioskRepKioskMetadata.getLastModified(), adapterKioskMetadata.getLastModified());

        Assertions.assertEquals(kioskObj.getFeatImg().getFieldName(), amenityAdapter.getFeatImg().getFieldName());
        Assertions.assertEquals(kioskObj.getFeatImg().getFieldValue(), amenityAdapter.getFeatImg().getFieldValue());

        Assertions.assertEquals(kioskObj.getSvgElemId().getFieldName(), amenityAdapter.getSvgElemId().getFieldName());
        Assertions.assertEquals(kioskObj.getSvgElemId().getFieldValue(), amenityAdapter.getSvgElemId().getFieldValue());

        Assertions.assertEquals(kioskObj.isWheelChairAccessible().getFieldName(), amenityAdapter.isWheelChairAccessible().getFieldName());
        Assertions.assertEquals(kioskObj.isWheelChairAccessible().getFieldValue(), amenityAdapter.isWheelChairAccessible().getFieldValue());

        Assertions.assertEquals(kioskObj.getNote().getFieldName(), amenityAdapter.getNote().getFieldName());
        Assertions.assertEquals(kioskObj.getNote().getFieldValue(), amenityAdapter.getNote().getFieldValue());

        Assertions.assertEquals(kioskObj.getName().getFieldName(), amenityAdapter.getName().getFieldName());
        Assertions.assertEquals(kioskObj.getName().getFieldValue(), amenityAdapter.getName().getFieldValue());

        Assertions.assertEquals(kioskObj.getLocation().getFieldName(), amenityAdapter.getLocation().getFieldName());
        Assertions.assertEquals(kioskObj.getLocation().getFieldValue(), amenityAdapter.getLocation().getFieldValue());

    }
    
}
