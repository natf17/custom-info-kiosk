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
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultDonationsPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultDonationsType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DonationsType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.AmenityConverter;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.DonationsInfoConverter;

public class DonationsKioskCollectionAdapterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;
    CollectionLocalizedProperties cmsLocObj;
    CollectionSharedProperties cmsSharedObj;

    DonationsType kioskObj;



    

    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);
        this.testFeatImg = new Image("url", 1, 2);

        this.cmsSharedObj = new CollectionSharedPropertiesImpl.Builder("DONATIONS")
                                .collectionNameField(new CollectionNameField("", "Donations"))
                                .addTextField(new TextField(DonationsInfoConverter.PAYMENT_TYPES_FIELD_TYPE, "", "payments_field_value"))
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
                                            .collectionNameField(new KioskCollectionField<String>("Donations", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .featImg(new KioskCollectionField<KioskImage>(new KioskImage("url", 1, 2), true))
                                            .svgElemId(new KioskCollectionField<String>("6", true))
                                            .isWheelChairAccessible(new KioskCollectionField<Boolean>(true, true))
                                            .note(new KioskCollectionField<String>("note_fieldValue", true))
                                            .name(new KioskCollectionField<String>("name_fieldValue", true))
                                            .location(new KioskCollectionField<LinkedCollectionReference>(new LinkedCollectionReference(5L), true))
                                            .build();

        this.kioskObj = new DefaultDonationsType(amenityType, new DefaultDonationsPiece(new KioskCollectionField<String>("payments_field_value", false)));

    }

    @Test
    public void givenValidCms_correctCmsGetters() {

        DonationsKioskCollectionAdapter donAdapter = new DonationsKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(cmsSharedObj.id(), donAdapter.id());
        Assertions.assertEquals(cmsSharedObj.withId(1L), donAdapter.withId(1L));
        Assertions.assertEquals(cmsSharedObj.type(), donAdapter.type());
        Assertions.assertEquals(cmsSharedObj.subType(), donAdapter.subType());
        Assertions.assertEquals(cmsSharedObj.collectionNameField(), donAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.numericFields(), donAdapter.numericFields());
        Assertions.assertEquals(cmsSharedObj.booleanFields(), donAdapter.booleanFields());
        Assertions.assertEquals(cmsSharedObj.textFields(), donAdapter.textFields());
        Assertions.assertEquals(cmsSharedObj.imageFields(), donAdapter.imageFields());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields(), donAdapter.linkedCollectionFields());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals(), donAdapter.collectionSharedInternals());

        Assertions.assertEquals(cmsLocObj.locWithId(2L), donAdapter.locWithId(2L));
        Assertions.assertEquals(cmsLocObj.locId(), donAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), donAdapter.locCollectionNameField());
        Assertions.assertEquals(cmsLocObj.locNumericFields(), donAdapter.locNumericFields());
        Assertions.assertEquals(cmsLocObj.locBooleanFields(), donAdapter.locBooleanFields());
        Assertions.assertEquals(cmsLocObj.locTextFields(), donAdapter.locTextFields());
        Assertions.assertEquals(cmsLocObj.locImageFields(), donAdapter.locImageFields());
        Assertions.assertEquals(cmsLocObj.parentId(), donAdapter.parentId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals(), donAdapter.locCollectionInternals());
 
    }

    @Test
    public void givenValidKiosk_correctCmsGetters() {

        DonationsKioskCollectionAdapter donAdapter = new DonationsKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(1L, donAdapter.id());
        Assertions.assertNotNull(donAdapter.withId(1L));
        Assertions.assertEquals("DONATIONS", donAdapter.type());
        Assertions.assertEquals(null, donAdapter.subType());
        Assertions.assertNotNull(donAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldType(), donAdapter.collectionNameField().getFieldType());
        Assertions.assertNotNull(donAdapter.collectionNameField().getFieldName());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldValue(), donAdapter.collectionNameField().getFieldValue());
        Assertions.assertEquals(cmsSharedObj.numericFields().size(), donAdapter.numericFields().size());
        Assertions.assertEquals(cmsSharedObj.booleanFields().size(), donAdapter.booleanFields().size());
        Assertions.assertEquals(cmsSharedObj.textFields().size(), donAdapter.textFields().size());
        Assertions.assertEquals(cmsSharedObj.imageFields().size(), donAdapter.imageFields().size());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields().size(), donAdapter.linkedCollectionFields().size());
        Assertions.assertNotNull(donAdapter.collectionSharedInternals());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getStatus(), donAdapter.collectionSharedInternals().getStatus());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getCreatedOn(), donAdapter.collectionSharedInternals().getCreatedOn());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getLastModified(), donAdapter.collectionSharedInternals().getLastModified());

        Assertions.assertNotNull(donAdapter.locWithId(2L));
        Assertions.assertNull(donAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), donAdapter.locCollectionNameField()); // both null
        Assertions.assertEquals(cmsLocObj.locNumericFields().size(), donAdapter.locNumericFields().size());
        Assertions.assertEquals(cmsLocObj.locBooleanFields().size(), donAdapter.locBooleanFields().size());
        Assertions.assertEquals(cmsLocObj.locTextFields().size(), donAdapter.locTextFields().size());
        Assertions.assertEquals(cmsLocObj.locImageFields().size(), donAdapter.locImageFields().size());
        Assertions.assertEquals(cmsLocObj.parentId(), donAdapter.parentId());
        Assertions.assertNotNull(donAdapter.locCollectionInternals());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocaleId(), donAdapter.locCollectionInternals().getKioskLocaleId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocale(), donAdapter.locCollectionInternals().getKioskLocale());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getCreatedOn(), donAdapter.locCollectionInternals().getCreatedOn());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getLastModified(), donAdapter.locCollectionInternals().getLastModified());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getStatus(), donAdapter.locCollectionInternals().getStatus());
 

        
    }

    @Test
    public void givenValidCms_correctKioskGetters() {

        DonationsKioskCollectionAdapter donAdapter = new DonationsKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(kioskObj.paymentTypesAccepted(), donAdapter.paymentTypesAccepted());
        Assertions.assertEquals(kioskObj.featImg(), donAdapter.featImg());
        Assertions.assertEquals(kioskObj.svgElemId(), donAdapter.svgElemId());
        Assertions.assertEquals(kioskObj.isWheelChairAccessible(), donAdapter.isWheelChairAccessible());
        Assertions.assertEquals(kioskObj.name(), donAdapter.name());
        Assertions.assertEquals(kioskObj.note(), donAdapter.note());
        Assertions.assertEquals(kioskObj.location(), donAdapter.location());
        Assertions.assertEquals(kioskObj.collectionId(), donAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), donAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), donAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), donAdapter.kioskCollectionMetadata());

    }

    @Test
    public void givenValidKiosk_correctKioskGetters() {
        DonationsKioskCollectionAdapter donAdapter = new DonationsKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(kioskObj.paymentTypesAccepted(), donAdapter.paymentTypesAccepted());
        Assertions.assertEquals(kioskObj.featImg(), donAdapter.featImg());
        Assertions.assertEquals(kioskObj.svgElemId(), donAdapter.svgElemId());
        Assertions.assertEquals(kioskObj.isWheelChairAccessible(), donAdapter.isWheelChairAccessible());
        Assertions.assertEquals(kioskObj.name(), donAdapter.name());
        Assertions.assertEquals(kioskObj.note(), donAdapter.note());
        Assertions.assertEquals(kioskObj.location(), donAdapter.location());
        Assertions.assertEquals(kioskObj.collectionId(), donAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), donAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), donAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), donAdapter.kioskCollectionMetadata());
    }
    
}
