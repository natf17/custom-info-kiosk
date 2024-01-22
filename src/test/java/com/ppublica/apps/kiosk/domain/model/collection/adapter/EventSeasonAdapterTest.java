package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElement;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElementImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.collection.DefaultEventSeason;
import com.ppublica.apps.kiosk.domain.model.collection.DefaultSeasonalEvent;
import com.ppublica.apps.kiosk.domain.model.collection.EventSeason;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionMetadata;

public class EventSeasonAdapterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;
    Image testFeatImg;

    DataCollectionType cmsObjWithElems;
    DataCollectionType cmsObjOneElem;
    EventSeason kioskObjWithEvents;
    EventSeason kioskObjNoEvents;

    @BeforeEach
    public void setup() {
        /*
        this.testDate = LocalDate.of(2023, 9, 12);
        this.testDateTime = LocalDateTime.of(2023, 1, 9, 9, 30);
        this.testFeatImg = new Image("url", 1, 2); */

        // prepare LocalizedFields components
        testDate = LocalDate.of(2023, 11, 15);
        testDateTime = LocalDateTime.of(2023, 11, 15, 13, 43);

        List<TextField> localizedTextFields = new ArrayList<>();
        localizedTextFields.add(new TextField(EventSeasonConverter.THEME_FIELD_TYPE, "theme_fieldName", "theme_fieldValue"));
        localizedTextFields.add(new TextField(EventSeasonConverter.DURATION_TEXT_FIELD_TYPE, "durationText_fieldName", "durationText_fieldValue"));
        localizedTextFields.add(new TextField(EventSeasonConverter.ADD_SEASON_DATES_TEXT_FIELD_TYPE, "addSeasonDatesText_fieldName", "addSeasonDatesText_fieldValue"));

        CollectionNameField localizedCollectionNameField = new CollectionNameField("coll_name_fieldName", "coll_name_fieldValue");
        CollectionInternals localizedCollectionInternals = new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime);

        List<DataCollectionElement> linkedDataElements = new ArrayList<>();
        linkedDataElements.add(new DataCollectionElementImpl.Builder()
                                                .addNumericField(new NumericField(SeasonalEventConverter.DURATION_DAYS_FIELD_TYPE, "durationDays_fieldName", 3L))
                                                .addNumericField(new NumericField(SeasonalEventConverter.SEASON_YEAR_FIELD_TYPE, "seasonYear_fieldName", 2023L))
                                                .addNumericField(new NumericField(SeasonalEventConverter.SEASONAL_EVENT_FIELD_TYPE, "", LocalDate.of(2023, 11, 15).toEpochDay()))
                                                .build()
                            );
        
        linkedDataElements.add(new DataCollectionElementImpl.Builder()
                                                .addNumericField(new NumericField(SeasonalEventConverter.DURATION_DAYS_FIELD_TYPE, "durationDays_fieldName", 3L))
                                                .addNumericField(new NumericField(SeasonalEventConverter.SEASON_YEAR_FIELD_TYPE, "seasonYear_fieldName", 2023L))
                                                .addNumericField(new NumericField(SeasonalEventConverter.SEASONAL_EVENT_FIELD_TYPE, "", LocalDate.of(2024, 11, 15).toEpochDay()))
                                                .build()
                            );

        this.cmsObjWithElems = new DataCollectionTypeImpl.Builder()
                                                    .withId(2L)
                                                    .type("TYPE")
                                                    .subType("SUBTYPE")
                                                    .localizedFields(new LocalizedFields(localizedCollectionNameField, localizedTextFields, null, null, localizedCollectionInternals))
                                                    .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                                    .linkedDataElements(linkedDataElements)
                                                    .build();

        List<DataCollectionElement> linkedDataElementsOneElement = new ArrayList<>();
        linkedDataElementsOneElement.add(new DataCollectionElementImpl.Builder()
                                                .addNumericField(new NumericField(SeasonalEventConverter.DURATION_DAYS_FIELD_TYPE, "durationDays_fieldName", 3L))
                                                .addNumericField(new NumericField(SeasonalEventConverter.SEASON_YEAR_FIELD_TYPE, "seasonYear_fieldName", 2023L))
                                                .addNumericField(new NumericField(SeasonalEventConverter.SEASONAL_EVENT_FIELD_TYPE, "", null))
                                                .build()
                            );

        this.cmsObjOneElem = new DataCollectionTypeImpl.Builder()
                                                    .withId(2L)
                                                    .type("TYPE")
                                                    .subType("SUBTYPE")
                                                    .localizedFields(new LocalizedFields(localizedCollectionNameField, localizedTextFields, null, null, localizedCollectionInternals))
                                                    .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                                    .linkedDataElements(linkedDataElementsOneElement)
                                                    .build();
        
        
        List<DefaultSeasonalEvent> events = new ArrayList<>();
        events.add(new DefaultSeasonalEvent(LocalDate.of(2023, 11, 15)));
        events.add(new DefaultSeasonalEvent(LocalDate.of(2024, 11, 15)));

        this.kioskObjWithEvents = new DefaultEventSeason.Builder()
                                            .id(2L)
                                            .collectionNameField(new KioskCollectionField<String>("coll_name_fieldName", "coll_name_fieldValue", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                            .seasonYear(new KioskCollectionField<Long>("seasonYear_fieldName", 2023L, true))
                                            .durationDays(new KioskCollectionField<Long>("durationDays_fieldName", 3L, true))
                                            .theme(new KioskCollectionField<String>("theme_fieldName", "theme_fieldValue", true))
                                            .durationText(new KioskCollectionField<String>("durationText_fieldName", "durationText_fieldValue", true))
                                            .addSeasonDatesText(new KioskCollectionField<String>("addSeasonDatesText_fieldName", "addSeasonDatesText_fieldValue", true))
                                            .events(events)
                                            .build();

        this.kioskObjNoEvents = new DefaultEventSeason.Builder()
                                            .id(2L)
                                            .collectionNameField(new KioskCollectionField<String>("coll_name_fieldName", "coll_name_fieldValue", true))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                            .seasonYear(new KioskCollectionField<Long>("seasonYear_fieldName", 2023L, true))
                                            .durationDays(new KioskCollectionField<Long>("durationDays_fieldName", 3L, true))
                                            .theme(new KioskCollectionField<String>("theme_fieldName", "theme_fieldValue", true))
                                            .durationText(new KioskCollectionField<String>("durationText_fieldName", "durationText_fieldValue", true))
                                            .addSeasonDatesText(new KioskCollectionField<String>("addSeasonDatesText_fieldName", "addSeasonDatesText_fieldValue", true))
                                            .build();

    }
    
    @Test
    public void givenValidDataCollectionType_correctCmsGetters() {

        EventSeasonAdapter eventSeasonAdapter = new EventSeasonAdapter(cmsObjWithElems);

        Assertions.assertEquals(cmsObjWithElems.getId(), eventSeasonAdapter.getId());
        Assertions.assertEquals(cmsObjWithElems.getType(), eventSeasonAdapter.getType());
        Assertions.assertEquals(cmsObjWithElems.getSubType(), eventSeasonAdapter.getSubType());


        LocalizedFields adapterLocalizedFields = eventSeasonAdapter.getLocalizedFields();
        LocalizedFields cmsObjLocalizedFields = cmsObjWithElems.getLocalizedFields();

        Assertions.assertEquals(cmsObjLocalizedFields.getBooleanFields().size(), adapterLocalizedFields.getBooleanFields().size());
        Assertions.assertEquals(cmsObjLocalizedFields.getNumericFields().size(), adapterLocalizedFields.getNumericFields().size());
        Assertions.assertEquals(cmsObjLocalizedFields.getTextFields().size(), adapterLocalizedFields.getTextFields().size());

        Assertions.assertEquals(cmsObjLocalizedFields.getCollectionNameField().getFieldType(), adapterLocalizedFields.getCollectionNameField().getFieldType());
        Assertions.assertEquals(cmsObjLocalizedFields.getCollectionNameField().getFieldName(), adapterLocalizedFields.getCollectionNameField().getFieldName());
        Assertions.assertEquals(cmsObjLocalizedFields.getCollectionNameField().getFieldValue(), adapterLocalizedFields.getCollectionNameField().getFieldValue());

        CollectionInternals adapterLocalCollInternals = adapterLocalizedFields.getCollectionLocalizedInternals();
        CollectionInternals cmsObjLocalCollInternals = cmsObjLocalizedFields.getCollectionLocalizedInternals();

        Assertions.assertEquals(cmsObjLocalCollInternals.getKioskLocaleId(), adapterLocalCollInternals.getKioskLocaleId());
        Assertions.assertEquals(cmsObjLocalCollInternals.getStatus(), adapterLocalCollInternals.getStatus());
        Assertions.assertEquals(cmsObjLocalCollInternals.getCreatedOn(), adapterLocalCollInternals.getCreatedOn());
        Assertions.assertEquals(cmsObjLocalCollInternals.getLastModified(), adapterLocalCollInternals.getLastModified());



        CollectionInternals adapterCollInternals = eventSeasonAdapter.getCollectionInternals();
        CollectionInternals cmsObjCollInternals = cmsObjWithElems.getCollectionInternals();

        Assertions.assertEquals(cmsObjCollInternals.getKioskLocaleId(), adapterCollInternals.getKioskLocaleId());
        Assertions.assertEquals(cmsObjCollInternals.getStatus(), adapterCollInternals.getStatus());
        Assertions.assertEquals(cmsObjCollInternals.getCreatedOn(), adapterCollInternals.getCreatedOn());
        Assertions.assertEquals(cmsObjCollInternals.getLastModified(), adapterCollInternals.getLastModified());

        List<DataCollectionElement> adapterCollectionElements = eventSeasonAdapter.getLinkedDataElements();
        List<DataCollectionElement> cmsObjCollectionElements = cmsObjWithElems.getLinkedDataElements();

        Assertions.assertEquals(cmsObjCollectionElements.size(), adapterCollectionElements.size());

    }

    
    @Test
    public void givenValidDataCollectionType_correctKioskGetters() {

        EventSeasonAdapter eventSeasonAdapter = new EventSeasonAdapter(cmsObjWithElems);

        Assertions.assertEquals(2L, eventSeasonAdapter.getId());
        Assertions.assertEquals("coll_name_fieldName", eventSeasonAdapter.getKioskCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_name_fieldValue", eventSeasonAdapter.getKioskCollectionNameField().getFieldValue());
        
        KioskCollectionMetadata kioskMetadata = eventSeasonAdapter.getKioskCollectionMetadata();
        Assertions.assertEquals(enLocaleId, kioskMetadata.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, kioskMetadata.getStatus());
        Assertions.assertEquals(testDate, kioskMetadata.getCreatedOn());
        Assertions.assertEquals(testDateTime, kioskMetadata.getLastModified());

        Assertions.assertEquals("seasonYear_fieldName", eventSeasonAdapter.getSeasonYear().getFieldName());
        Assertions.assertEquals(2023L, eventSeasonAdapter.getSeasonYear().getFieldValue());

        Assertions.assertEquals("durationDays_fieldName", eventSeasonAdapter.getDurationDays().getFieldName());
        Assertions.assertEquals(3L, eventSeasonAdapter.getDurationDays().getFieldValue());

        Assertions.assertEquals("theme_fieldName", eventSeasonAdapter.getTheme().getFieldName());
        Assertions.assertEquals("theme_fieldValue", eventSeasonAdapter.getTheme().getFieldValue());

        Assertions.assertEquals("addSeasonDatesText_fieldName", eventSeasonAdapter.getAddSeasonDatesText().getFieldName());
        Assertions.assertEquals("addSeasonDatesText_fieldValue", eventSeasonAdapter.getAddSeasonDatesText().getFieldValue());

        Assertions.assertEquals("durationText_fieldName", eventSeasonAdapter.getDurationText().getFieldName());
        Assertions.assertEquals("durationText_fieldValue", eventSeasonAdapter.getDurationText().getFieldValue());

        List<DefaultSeasonalEvent> events = eventSeasonAdapter.getEvents();
        Assertions.assertEquals(2, events.size());
        Assertions.assertTrue(events.get(0).getStartDate().equals(LocalDate.of(2023, 11, 15)) || events.get(1).getStartDate().equals(LocalDate.of(2023, 11, 15)));
        Assertions.assertTrue(events.get(0).getStartDate().equals(LocalDate.of(2024, 11, 15)) || events.get(1).getStartDate().equals(LocalDate.of(2024, 11, 15)));
        
         
    }

    @Test
    public void givenValidDataCollectionTypeOneNullElem_correctKioskGetters() {

        EventSeasonAdapter eventSeasonAdapter = new EventSeasonAdapter(cmsObjOneElem);

        Assertions.assertEquals(2L, eventSeasonAdapter.getId());
        Assertions.assertEquals("coll_name_fieldName", eventSeasonAdapter.getKioskCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_name_fieldValue", eventSeasonAdapter.getKioskCollectionNameField().getFieldValue());
        
        KioskCollectionMetadata kioskMetadata = eventSeasonAdapter.getKioskCollectionMetadata();
        Assertions.assertEquals(enLocaleId, kioskMetadata.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, kioskMetadata.getStatus());
        Assertions.assertEquals(testDate, kioskMetadata.getCreatedOn());
        Assertions.assertEquals(testDateTime, kioskMetadata.getLastModified());

        Assertions.assertEquals("seasonYear_fieldName", eventSeasonAdapter.getSeasonYear().getFieldName());
        Assertions.assertEquals(2023L, eventSeasonAdapter.getSeasonYear().getFieldValue());

        Assertions.assertEquals("durationDays_fieldName", eventSeasonAdapter.getDurationDays().getFieldName());
        Assertions.assertEquals(3L, eventSeasonAdapter.getDurationDays().getFieldValue());

        Assertions.assertEquals("theme_fieldName", eventSeasonAdapter.getTheme().getFieldName());
        Assertions.assertEquals("theme_fieldValue", eventSeasonAdapter.getTheme().getFieldValue());

        Assertions.assertEquals("addSeasonDatesText_fieldName", eventSeasonAdapter.getAddSeasonDatesText().getFieldName());
        Assertions.assertEquals("addSeasonDatesText_fieldValue", eventSeasonAdapter.getAddSeasonDatesText().getFieldValue());

        Assertions.assertEquals("durationText_fieldName", eventSeasonAdapter.getDurationText().getFieldName());
        Assertions.assertEquals("durationText_fieldValue", eventSeasonAdapter.getDurationText().getFieldValue());

        List<DefaultSeasonalEvent> events = eventSeasonAdapter.getEvents();
        Assertions.assertTrue(events.isEmpty());

    }

    @Test
    public void givenValidEventSeasonWithEvents_correctCmsGetters() {

        EventSeasonAdapter eventSeasonAdapter = new EventSeasonAdapter(kioskObjWithEvents);

        Assertions.assertEquals(2L, eventSeasonAdapter.getId());
        Assertions.assertEquals(EventSeasonConverter.DATA_COLLECTION_TYPE, eventSeasonAdapter.getType());
        Assertions.assertEquals("2023", eventSeasonAdapter.getSubType());


        LocalizedFields adapterLocalizedFields = eventSeasonAdapter.getLocalizedFields();

        Assertions.assertEquals(0, adapterLocalizedFields.getBooleanFields().size());
        Assertions.assertEquals(0, adapterLocalizedFields.getNumericFields().size());
        Assertions.assertEquals(3, adapterLocalizedFields.getTextFields().size());
        // didn't test all three fields

        Assertions.assertEquals("coll_name_fieldName", adapterLocalizedFields.getCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_name_fieldValue", adapterLocalizedFields.getCollectionNameField().getFieldValue());

        CollectionInternals adapterLocalCollInternals = adapterLocalizedFields.getCollectionLocalizedInternals();

        Assertions.assertEquals(enLocaleId, adapterLocalCollInternals.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, adapterLocalCollInternals.getStatus());
        Assertions.assertEquals(testDate, adapterLocalCollInternals.getCreatedOn());
        Assertions.assertEquals(testDateTime, adapterLocalCollInternals.getLastModified());



        CollectionInternals adapterCollInternals = eventSeasonAdapter.getCollectionInternals();

        Assertions.assertEquals(enLocaleId, adapterCollInternals.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, adapterCollInternals.getStatus());
        Assertions.assertEquals(testDate, adapterCollInternals.getCreatedOn());
        Assertions.assertEquals(testDateTime, adapterCollInternals.getLastModified());

        List<DataCollectionElement> adapterCollectionElements = eventSeasonAdapter.getLinkedDataElements();

        Assertions.assertEquals(2, adapterCollectionElements.size());
        // didn't test the two elements
 
    }

    @Test
    public void givenValidEventSeasonWithEvents_correctKioskGetters() {
        EventSeasonAdapter eventSeasonAdapter = new EventSeasonAdapter(kioskObjWithEvents);

        Assertions.assertEquals(2L, eventSeasonAdapter.getId());
        Assertions.assertEquals("EventSeason", eventSeasonAdapter.getType());
        Assertions.assertEquals("2023", eventSeasonAdapter.getSubType());

        Assertions.assertEquals("coll_name_fieldName", eventSeasonAdapter.getKioskCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_name_fieldValue", eventSeasonAdapter.getKioskCollectionNameField().getFieldValue());
        
        KioskCollectionMetadata kioskMetadata = eventSeasonAdapter.getKioskCollectionMetadata();
        Assertions.assertEquals(enLocaleId, kioskMetadata.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, kioskMetadata.getStatus());
        Assertions.assertEquals(testDate, kioskMetadata.getCreatedOn());
        Assertions.assertEquals(testDateTime, kioskMetadata.getLastModified());

        Assertions.assertEquals("seasonYear_fieldName", eventSeasonAdapter.getSeasonYear().getFieldName());
        Assertions.assertEquals(2023L, eventSeasonAdapter.getSeasonYear().getFieldValue());

        Assertions.assertEquals("durationDays_fieldName", eventSeasonAdapter.getDurationDays().getFieldName());
        Assertions.assertEquals(3L, eventSeasonAdapter.getDurationDays().getFieldValue());

        Assertions.assertEquals("theme_fieldName", eventSeasonAdapter.getTheme().getFieldName());
        Assertions.assertEquals("theme_fieldValue", eventSeasonAdapter.getTheme().getFieldValue());

        Assertions.assertEquals("addSeasonDatesText_fieldName", eventSeasonAdapter.getAddSeasonDatesText().getFieldName());
        Assertions.assertEquals("addSeasonDatesText_fieldValue", eventSeasonAdapter.getAddSeasonDatesText().getFieldValue());

        Assertions.assertEquals("durationText_fieldName", eventSeasonAdapter.getDurationText().getFieldName());
        Assertions.assertEquals("durationText_fieldValue", eventSeasonAdapter.getDurationText().getFieldValue());

        List<DefaultSeasonalEvent> events = eventSeasonAdapter.getEvents();
        Assertions.assertEquals(2, events.size());
        Assertions.assertTrue(events.get(0).getStartDate().equals(LocalDate.of(2023, 11, 15)) || events.get(1).getStartDate().equals(LocalDate.of(2023, 11, 15)));
        Assertions.assertTrue(events.get(0).getStartDate().equals(LocalDate.of(2024, 11, 15)) || events.get(1).getStartDate().equals(LocalDate.of(2024, 11, 15)));
    }

    @Test
    public void givenValidEventSeasonNoEvents_correctCmsGetters() {
        EventSeasonAdapter eventSeasonAdapter = new EventSeasonAdapter(kioskObjNoEvents);

        Assertions.assertEquals(2L, eventSeasonAdapter.getId());
        Assertions.assertEquals(EventSeasonConverter.DATA_COLLECTION_TYPE, eventSeasonAdapter.getType());
        Assertions.assertEquals("2023", eventSeasonAdapter.getSubType());


        LocalizedFields adapterLocalizedFields = eventSeasonAdapter.getLocalizedFields();

        Assertions.assertEquals(0, adapterLocalizedFields.getBooleanFields().size());
        Assertions.assertEquals(0, adapterLocalizedFields.getNumericFields().size());
        Assertions.assertEquals(3, adapterLocalizedFields.getTextFields().size());
        // didn't test all three fields

        Assertions.assertEquals("coll_name_fieldName", adapterLocalizedFields.getCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_name_fieldValue", adapterLocalizedFields.getCollectionNameField().getFieldValue());

        CollectionInternals adapterLocalCollInternals = adapterLocalizedFields.getCollectionLocalizedInternals();

        Assertions.assertEquals(enLocaleId, adapterLocalCollInternals.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, adapterLocalCollInternals.getStatus());
        Assertions.assertEquals(testDate, adapterLocalCollInternals.getCreatedOn());
        Assertions.assertEquals(testDateTime, adapterLocalCollInternals.getLastModified());



        CollectionInternals adapterCollInternals = eventSeasonAdapter.getCollectionInternals();

        Assertions.assertEquals(enLocaleId, adapterCollInternals.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, adapterCollInternals.getStatus());
        Assertions.assertEquals(testDate, adapterCollInternals.getCreatedOn());
        Assertions.assertEquals(testDateTime, adapterCollInternals.getLastModified());

        List<DataCollectionElement> adapterCollectionElements = eventSeasonAdapter.getLinkedDataElements();

        Assertions.assertEquals(1, adapterCollectionElements.size());
    }

    @Test
    public void givenValidEventSeasonNoEvents_correctKioskGetters() {
        EventSeasonAdapter eventSeasonAdapter = new EventSeasonAdapter(kioskObjNoEvents);

        Assertions.assertEquals(2L, eventSeasonAdapter.getId());
        Assertions.assertEquals("EventSeason", eventSeasonAdapter.getType());
        Assertions.assertEquals("2023", eventSeasonAdapter.getSubType());

        Assertions.assertEquals(2L, eventSeasonAdapter.getId());
        Assertions.assertEquals("coll_name_fieldName", eventSeasonAdapter.getKioskCollectionNameField().getFieldName());
        Assertions.assertEquals("coll_name_fieldValue", eventSeasonAdapter.getKioskCollectionNameField().getFieldValue());
        
        KioskCollectionMetadata kioskMetadata = eventSeasonAdapter.getKioskCollectionMetadata();
        Assertions.assertEquals(enLocaleId, kioskMetadata.getKioskLocaleId());
        Assertions.assertEquals(PageStatus.PUBLISHED, kioskMetadata.getStatus());
        Assertions.assertEquals(testDate, kioskMetadata.getCreatedOn());
        Assertions.assertEquals(testDateTime, kioskMetadata.getLastModified());

        Assertions.assertEquals("seasonYear_fieldName", eventSeasonAdapter.getSeasonYear().getFieldName());
        Assertions.assertEquals(2023L, eventSeasonAdapter.getSeasonYear().getFieldValue());

        Assertions.assertEquals("durationDays_fieldName", eventSeasonAdapter.getDurationDays().getFieldName());
        Assertions.assertEquals(3L, eventSeasonAdapter.getDurationDays().getFieldValue());

        Assertions.assertEquals("theme_fieldName", eventSeasonAdapter.getTheme().getFieldName());
        Assertions.assertEquals("theme_fieldValue", eventSeasonAdapter.getTheme().getFieldValue());

        Assertions.assertEquals("addSeasonDatesText_fieldName", eventSeasonAdapter.getAddSeasonDatesText().getFieldName());
        Assertions.assertEquals("addSeasonDatesText_fieldValue", eventSeasonAdapter.getAddSeasonDatesText().getFieldValue());

        Assertions.assertEquals("durationText_fieldName", eventSeasonAdapter.getDurationText().getFieldName());
        Assertions.assertEquals("durationText_fieldValue", eventSeasonAdapter.getDurationText().getFieldValue());

        List<DefaultSeasonalEvent> events = eventSeasonAdapter.getEvents();
        Assertions.assertTrue(events.isEmpty());
    }

}
