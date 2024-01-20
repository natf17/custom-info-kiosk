package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElement;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.collection.DefaultSeasonalEvent;
import com.ppublica.apps.kiosk.domain.model.collection.EventSeason;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionMetadata;

public class EventSeasonAdapter implements EventSeason, DataCollectionType {

    private EventSeason eventSeason;
    private DataCollectionType dataCollectionType;

    private EventSeasonConverter eventSeasonConverter = new EventSeasonConverter();

    public EventSeasonAdapter(EventSeason eventSeason) {
        this.eventSeason = eventSeason;
    }

    public EventSeasonAdapter(DataCollectionType dataCollectionType) {
        this.dataCollectionType = dataCollectionType;
    }

    @Override
    public DataCollectionType withId(Long id) {
        return getDataCollectionType().withId(id);
    }

    @Override
    public String getType() {
        return getDataCollectionType().getType();
    }

    @Override
    public String getSubType() {
        return getDataCollectionType().getSubType();
    }

    @Override
    public LocalizedFields getLocalizedFields() {
        return getDataCollectionType().getLocalizedFields();
    }

    @Override
    public CollectionInternals getCollectionInternals() {
        return getDataCollectionType().getCollectionInternals();
    }

    @Override
    public List<DataCollectionElement> getLinkedDataElements() {
        return getDataCollectionType().getLinkedDataElements();
    }

    @Override
    public Long getId() {
        return getEventSeason().getId();
    }

    @Override
    public KioskCollectionField<String> getKioskCollectionNameField() {
        return getEventSeason().getKioskCollectionNameField();
    }

    @Override
    public KioskCollectionMetadata getKioskCollectionMetadata() {
        return getEventSeason().getKioskCollectionMetadata();
    }

    @Override
    public KioskCollectionField<Long> getSeasonYear() {
        return getEventSeason().getSeasonYear();
    }

    @Override
    public KioskCollectionField<Long> getDurationDays() {
        return getEventSeason().getDurationDays();
    }

    @Override
    public KioskCollectionField<String> getTheme() {
        return getEventSeason().getTheme();
    }

    @Override
    public KioskCollectionField<String> getDurationText() {
        return getEventSeason().getDurationText();
    }

    @Override
    public KioskCollectionField<String> getAddSeasonDatesText() {
        return getEventSeason().getAddSeasonDatesText();
    }

    @Override
    public List<DefaultSeasonalEvent> getEvents() {
        return getEventSeason().getEvents();
    }

    public EventSeason getEventSeason() {
        if(this.eventSeason == null) {
            buildAndSetEventSeason();
        }

        return this.eventSeason;
    }

    protected final void buildAndSetEventSeason() {
        this.eventSeason = eventSeasonConverter.convert(this.dataCollectionType);
    }

    public DataCollectionType getDataCollectionType() {
        if(this.dataCollectionType == null) {
            buildAndSetDataCollectionType();
        }

        return this.dataCollectionType;
    }

    protected final void buildAndSetDataCollectionType() {
        DataCollectionTypeImpl.Builder builder = new DataCollectionTypeImpl.Builder();

        eventSeasonConverter.transferEventSeasonRep(builder, eventSeason);

        processDataCollectionTypeBuilder(builder);

        this.dataCollectionType = builder.build();
    }

    protected void processDataCollectionTypeBuilder(DataCollectionTypeImpl.Builder builder) { }
    
}
