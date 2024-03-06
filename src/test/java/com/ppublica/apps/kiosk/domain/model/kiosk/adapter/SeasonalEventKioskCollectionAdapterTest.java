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
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionRelationship;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultSeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.SeasonalEventInfoConverter;

public class SeasonalEventKioskCollectionAdapterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    CollectionLocalizedProperties cmsLocObj;
    CollectionSharedProperties cmsSharedObj;

    SeasonalEventType kioskObj;
    LocalDate seasonalEventDate;
    Long seasonalLong;
    Long seasonId = 10L;
    

    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 3, 6);
        this.testDateTime = LocalDateTime.of(2024, 3, 6, 9, 30);

        this.seasonalEventDate = LocalDate.of(2024, 7, 18);
        this.seasonalLong = seasonalEventDate.toEpochDay();

        this.cmsSharedObj = new CollectionSharedPropertiesImpl.Builder("SEASONAL_EVENT")
                                .collectionNameField(new CollectionNameField("", "Seasonal event"))
                                .addTextField(new TextField(SeasonalEventInfoConverter.EVENT_LANG_FIELD_TYPE, "", "eventLang_fieldValue"))
                                .addNumericField(new NumericField(SeasonalEventInfoConverter.START_DATE_FIELD_TYPE, "", seasonalLong))
                                .addCollectionRelationship(new CollectionRelationship(seasonId, SeasonalEventInfoConverter.SEASON_TO_EVENT_REL_FIELD_TYPE))
                                .withId(1L)
                                .collectionSharedInternals(new CollectionSharedInternals(PageStatus.PUBLISHED, testDate, testDateTime))
                                .build();
        
        this.cmsLocObj = new CollectionLocalizedPropertiesImpl.Builder(1L)
                                .collectionInternals(new CollectionInternals(enLocaleId, "en", PageStatus.PUBLISHED, testDate, testDateTime))
                                .build();
        


        this.kioskObj = new DefaultSeasonalEventType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Seasonal event", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .eventLanguage(new KioskCollectionField<String>("eventLang_fieldValue", false))
                                            .startDate(new KioskCollectionField<LocalDate>(seasonalEventDate, false))
                                            .seasonId(new KioskCollectionField<Long>(seasonId, false))
                                            .build();

    }

    @Test
    public void givenValidCms_correctCmsGetters() {

        SeasonalEventKioskCollectionAdapter seasonalEventAdapter = new SeasonalEventKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(cmsSharedObj.id(), seasonalEventAdapter.id());
        Assertions.assertEquals(cmsSharedObj.withId(1L), seasonalEventAdapter.withId(1L));
        Assertions.assertEquals(cmsSharedObj.type(), seasonalEventAdapter.type());
        Assertions.assertEquals(cmsSharedObj.subType(), seasonalEventAdapter.subType());
        Assertions.assertEquals(cmsSharedObj.collectionNameField(), seasonalEventAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.numericFields(), seasonalEventAdapter.numericFields());
        Assertions.assertEquals(cmsSharedObj.booleanFields(), seasonalEventAdapter.booleanFields());
        Assertions.assertEquals(cmsSharedObj.textFields(), seasonalEventAdapter.textFields());
        Assertions.assertEquals(cmsSharedObj.imageFields(), seasonalEventAdapter.imageFields());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields(), seasonalEventAdapter.linkedCollectionFields());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals(), seasonalEventAdapter.collectionSharedInternals());
        Assertions.assertEquals(cmsSharedObj.collectionRelationships(), seasonalEventAdapter.collectionRelationships());

        Assertions.assertEquals(cmsLocObj.locWithId(2L), seasonalEventAdapter.locWithId(2L));
        Assertions.assertEquals(cmsLocObj.locId(), seasonalEventAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), seasonalEventAdapter.locCollectionNameField());
        Assertions.assertEquals(cmsLocObj.locNumericFields(), seasonalEventAdapter.locNumericFields());
        Assertions.assertEquals(cmsLocObj.locBooleanFields(), seasonalEventAdapter.locBooleanFields());
        Assertions.assertEquals(cmsLocObj.locTextFields(), seasonalEventAdapter.locTextFields());
        Assertions.assertEquals(cmsLocObj.locImageFields(), seasonalEventAdapter.locImageFields());
        Assertions.assertEquals(cmsLocObj.parentId(), seasonalEventAdapter.parentId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals(), seasonalEventAdapter.locCollectionInternals());
 
    }

    @Test
    public void givenValidKiosk_correctCmsGetters() {

        SeasonalEventKioskCollectionAdapter seasonalEventAdapter = new SeasonalEventKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(1L, seasonalEventAdapter.id());
        Assertions.assertNotNull(seasonalEventAdapter.withId(1L));
        Assertions.assertEquals("SEASONAL_EVENT", seasonalEventAdapter.type());
        Assertions.assertEquals(null, seasonalEventAdapter.subType());
        Assertions.assertNotNull(seasonalEventAdapter.collectionNameField());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldType(), seasonalEventAdapter.collectionNameField().getFieldType());
        Assertions.assertNotNull(seasonalEventAdapter.collectionNameField().getFieldName());
        Assertions.assertEquals(cmsSharedObj.collectionNameField().getFieldValue(), seasonalEventAdapter.collectionNameField().getFieldValue());
        Assertions.assertEquals(cmsSharedObj.numericFields().size(), seasonalEventAdapter.numericFields().size());
        Assertions.assertEquals(cmsSharedObj.booleanFields().size(), seasonalEventAdapter.booleanFields().size());
        Assertions.assertEquals(cmsSharedObj.textFields().size(), seasonalEventAdapter.textFields().size());
        Assertions.assertEquals(cmsSharedObj.imageFields().size(), seasonalEventAdapter.imageFields().size());
        Assertions.assertEquals(cmsSharedObj.linkedCollectionFields().size(), seasonalEventAdapter.linkedCollectionFields().size());
        Assertions.assertNotNull(seasonalEventAdapter.collectionSharedInternals());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getStatus(), seasonalEventAdapter.collectionSharedInternals().getStatus());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getCreatedOn(), seasonalEventAdapter.collectionSharedInternals().getCreatedOn());
        Assertions.assertEquals(cmsSharedObj.collectionSharedInternals().getLastModified(), seasonalEventAdapter.collectionSharedInternals().getLastModified());
        Assertions.assertEquals(cmsSharedObj.collectionRelationships().size(), seasonalEventAdapter.collectionRelationships().size());

        Assertions.assertNotNull(seasonalEventAdapter.locWithId(2L));
        Assertions.assertNull(seasonalEventAdapter.locId());
        Assertions.assertEquals(cmsLocObj.locCollectionNameField(), seasonalEventAdapter.locCollectionNameField()); // both null
        Assertions.assertEquals(cmsLocObj.locNumericFields().size(), seasonalEventAdapter.locNumericFields().size());
        Assertions.assertEquals(cmsLocObj.locBooleanFields().size(), seasonalEventAdapter.locBooleanFields().size());
        Assertions.assertEquals(cmsLocObj.locTextFields().size(), seasonalEventAdapter.locTextFields().size());
        Assertions.assertEquals(cmsLocObj.locImageFields().size(), seasonalEventAdapter.locImageFields().size());
        Assertions.assertEquals(cmsLocObj.parentId(), seasonalEventAdapter.parentId());
        Assertions.assertNotNull(seasonalEventAdapter.locCollectionInternals());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocaleId(), seasonalEventAdapter.locCollectionInternals().getKioskLocaleId());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getKioskLocale(), seasonalEventAdapter.locCollectionInternals().getKioskLocale());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getCreatedOn(), seasonalEventAdapter.locCollectionInternals().getCreatedOn());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getLastModified(), seasonalEventAdapter.locCollectionInternals().getLastModified());
        Assertions.assertEquals(cmsLocObj.locCollectionInternals().getStatus(), seasonalEventAdapter.locCollectionInternals().getStatus());
 

        
    }

    @Test
    public void givenValidCms_correctKioskGetters() {

        SeasonalEventKioskCollectionAdapter seasonalEventAdapter = new SeasonalEventKioskCollectionAdapter.Builder().sharedCmsPiece(cmsSharedObj).localizedCmsPiece(cmsLocObj).build();

        Assertions.assertEquals(kioskObj.eventLanguage(), seasonalEventAdapter.eventLanguage());
        Assertions.assertEquals(kioskObj.startDate(), seasonalEventAdapter.startDate());
        Assertions.assertEquals(kioskObj.seasonId(), seasonalEventAdapter.seasonId());
        Assertions.assertEquals(kioskObj.collectionId(), seasonalEventAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), seasonalEventAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), seasonalEventAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), seasonalEventAdapter.kioskCollectionMetadata());

    }

    @Test
    public void givenValidKiosk_correctKioskGetters() {
        SeasonalEventKioskCollectionAdapter seasonalEventAdapter = new SeasonalEventKioskCollectionAdapter.Builder().kioskCollection(kioskObj).build();

        Assertions.assertEquals(kioskObj.eventLanguage(), seasonalEventAdapter.eventLanguage());
        Assertions.assertEquals(kioskObj.startDate(), seasonalEventAdapter.startDate());
        Assertions.assertEquals(kioskObj.seasonId(), seasonalEventAdapter.seasonId());
        Assertions.assertEquals(kioskObj.collectionId(), seasonalEventAdapter.collectionId());
        Assertions.assertEquals(kioskObj.kioskCollectionType(), seasonalEventAdapter.kioskCollectionType());
        Assertions.assertEquals(kioskObj.kioskCollectionNameField(), seasonalEventAdapter.kioskCollectionNameField());
        Assertions.assertEquals(kioskObj.kioskCollectionMetadata(), seasonalEventAdapter.kioskCollectionMetadata());
    }
}
