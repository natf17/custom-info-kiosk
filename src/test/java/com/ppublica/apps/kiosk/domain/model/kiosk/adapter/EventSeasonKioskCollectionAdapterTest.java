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
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultEventSeasonType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EventSeasonType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.EventSeasonInfoConverter;

public class EventSeasonKioskCollectionAdapterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    CollectionLocalizedProperties cmsLocObj;
    CollectionSharedProperties cmsSharedObj;

    EventSeasonType kioskObj;
    

    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);

        this.cmsSharedObj = new CollectionSharedPropertiesImpl.Builder("EVENT_SEASON")
                                .collectionNameField(new CollectionNameField("", "Event season"))
                                .addTextField(new TextField(EventSeasonInfoConverter.SEASON_TYPE_FIELD_TYPE, "", "type_fieldValue"))
                                .addNumericField(new NumericField(EventSeasonInfoConverter.DURATION_DAYS_FIELD_TYPE, "", 3L))
                                .addNumericField(new NumericField(EventSeasonInfoConverter.SERVICE_YEAR_FIELD_TYPE, "", 2024L))
                                .withId(1L)
                                .collectionSharedInternals(new CollectionSharedInternals(PageStatus.PUBLISHED, testDate, testDateTime))
                                .build();
        
        this.cmsLocObj = new CollectionLocalizedPropertiesImpl.Builder(1L)
                                .addTextField(new TextField(EventSeasonInfoConverter.THEME_FIELD_TYPE, "", "theme_fieldValue"))
                                .addTextField(new TextField(EventSeasonInfoConverter.DURATION_TEXT_FIELD_TYPE, "", "durationText_fieldValue"))
                                .collectionInternals(new CollectionInternals(enLocaleId, "en", PageStatus.PUBLISHED, testDate, testDateTime))
                                .build();
        


        this.kioskObj = new DefaultEventSeasonType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Event season", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .theme(new KioskCollectionField<String>("theme_fieldValue", true))
                                            .seasonType(new KioskCollectionField<String>("type_fieldValue", false))
                                            .durationDays(new KioskCollectionField<Integer>(3, false))
                                            .serviceYear(new KioskCollectionField<Integer>(2024, false))
                                            .durationText(new KioskCollectionField<String>("durationText_fieldValue", true))
                                            .build();

    }

    @Test
    public void givenValidCms_correctCmsGetters() {

        EventSeasonKioskCollectionAdapter eventSeasonAdapter = new EventSeasonKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(cmsSharedObj.id(), eventSeasonAdapter.id());
        Assertions.assertEquals(cmsSharedObj.withId(1L), eventSeasonAdapter.withId(1L));
        Assertions.assertEquals(cmsSharedObj.type(), eventSeasonAdapter.type());
        Assertions.assertEquals(cmsSharedObj.subType(), eventSeasonAdapter.subType());
        Assertions.assertEquals(cmsSharedObj.collectionNameField(), eventSeasonAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.numericFields(), eventSeasonAdapter.numericFields());
        Assertions.assertEquals(cmsSharedObj.booleanFields(), eventSeasonAdapter.booleanFields());
        Assertions.assertEquals(cmsSharedObj.textFields(), eventSeasonAdapter.textFields());
        Assertions.assertEquals(cmsSharedObj.imageFields(), eventSeasonAdapter.imageFields());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields(), eventSeasonAdapter.linkedCollectionFields());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals(), eventSeasonAdapter.collectionSharedInternals());

        Assertions.assertEquals(cmsLocObj.locWithId(2L), eventSeasonAdapter.locWithId(2L));
        Assertions.assertEquals(cmsLocObj.locId(), eventSeasonAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), eventSeasonAdapter.locCollectionNameField());
        Assertions.assertEquals(cmsLocObj.locNumericFields(), eventSeasonAdapter.locNumericFields());
        Assertions.assertEquals(cmsLocObj.locBooleanFields(), eventSeasonAdapter.locBooleanFields());
        Assertions.assertEquals(cmsLocObj.locTextFields(), eventSeasonAdapter.locTextFields());
        Assertions.assertEquals(cmsLocObj.locImageFields(), eventSeasonAdapter.locImageFields());
        Assertions.assertEquals(cmsLocObj.parentId(), eventSeasonAdapter.parentId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals(), eventSeasonAdapter.locCollectionInternals());
 
    }

    @Test
    public void givenValidKiosk_correctCmsGetters() {

        EventSeasonKioskCollectionAdapter eventSeasonAdapter = new EventSeasonKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(1L, eventSeasonAdapter.id());
        Assertions.assertNotNull(eventSeasonAdapter.withId(1L));
        Assertions.assertEquals("EVENT_SEASON", eventSeasonAdapter.type());
        Assertions.assertEquals(null, eventSeasonAdapter.subType());
        Assertions.assertNotNull(eventSeasonAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldType(), eventSeasonAdapter.collectionNameField().getFieldType());
        Assertions.assertNotNull(eventSeasonAdapter.collectionNameField().getFieldName());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldValue(), eventSeasonAdapter.collectionNameField().getFieldValue());
        Assertions.assertEquals(cmsSharedObj.numericFields().size(), eventSeasonAdapter.numericFields().size());
        Assertions.assertEquals(cmsSharedObj.booleanFields().size(), eventSeasonAdapter.booleanFields().size());
        Assertions.assertEquals(cmsSharedObj.textFields().size(), eventSeasonAdapter.textFields().size());
        Assertions.assertEquals(cmsSharedObj.imageFields().size(), eventSeasonAdapter.imageFields().size());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields().size(), eventSeasonAdapter.linkedCollectionFields().size());
        Assertions.assertNotNull(eventSeasonAdapter.collectionSharedInternals());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getStatus(), eventSeasonAdapter.collectionSharedInternals().getStatus());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getCreatedOn(), eventSeasonAdapter.collectionSharedInternals().getCreatedOn());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getLastModified(), eventSeasonAdapter.collectionSharedInternals().getLastModified());

        Assertions.assertNotNull(eventSeasonAdapter.locWithId(2L));
        Assertions.assertNull(eventSeasonAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), eventSeasonAdapter.locCollectionNameField()); // both null
        Assertions.assertEquals(cmsLocObj.locNumericFields().size(), eventSeasonAdapter.locNumericFields().size());
        Assertions.assertEquals(cmsLocObj.locBooleanFields().size(), eventSeasonAdapter.locBooleanFields().size());
        Assertions.assertEquals(cmsLocObj.locTextFields().size(), eventSeasonAdapter.locTextFields().size());
        Assertions.assertEquals(cmsLocObj.locImageFields().size(), eventSeasonAdapter.locImageFields().size());
        Assertions.assertEquals(cmsLocObj.parentId(), eventSeasonAdapter.parentId());
        Assertions.assertNotNull(eventSeasonAdapter.locCollectionInternals());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocaleId(), eventSeasonAdapter.locCollectionInternals().getKioskLocaleId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocale(), eventSeasonAdapter.locCollectionInternals().getKioskLocale());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getCreatedOn(), eventSeasonAdapter.locCollectionInternals().getCreatedOn());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getLastModified(), eventSeasonAdapter.locCollectionInternals().getLastModified());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getStatus(), eventSeasonAdapter.locCollectionInternals().getStatus());
 

        
    }

    @Test
    public void givenValidCms_correctKioskGetters() {

        EventSeasonKioskCollectionAdapter eventSeasonAdapter = new EventSeasonKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(kioskObj.seasonType(), eventSeasonAdapter.seasonType());
        Assertions.assertEquals(kioskObj.durationDays(), eventSeasonAdapter.durationDays());
        Assertions.assertEquals(kioskObj.theme(), eventSeasonAdapter.theme());
        Assertions.assertEquals(kioskObj.serviceYear(), eventSeasonAdapter.serviceYear());
        Assertions.assertEquals(kioskObj.durationText(), eventSeasonAdapter.durationText());
        Assertions.assertEquals(kioskObj.collectionId(), eventSeasonAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), eventSeasonAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), eventSeasonAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), eventSeasonAdapter.kioskCollectionMetadata());

    }

    @Test
    public void givenValidKiosk_correctKioskGetters() {
        EventSeasonKioskCollectionAdapter eventSeasonAdapter = new EventSeasonKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(kioskObj.seasonType(), eventSeasonAdapter.seasonType());
        Assertions.assertEquals(kioskObj.durationDays(), eventSeasonAdapter.durationDays());
        Assertions.assertEquals(kioskObj.theme(), eventSeasonAdapter.theme());
        Assertions.assertEquals(kioskObj.serviceYear(), eventSeasonAdapter.serviceYear());
        Assertions.assertEquals(kioskObj.durationText(), eventSeasonAdapter.durationText());
        Assertions.assertEquals(kioskObj.collectionId(), eventSeasonAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), eventSeasonAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), eventSeasonAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), eventSeasonAdapter.kioskCollectionMetadata());
    }
}
