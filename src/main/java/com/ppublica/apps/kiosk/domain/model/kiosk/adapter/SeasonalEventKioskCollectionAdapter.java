package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import java.time.LocalDate;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.NonLocalizableKioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEvent;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.SeasonalEventInfoConverter;

public class SeasonalEventKioskCollectionAdapter extends NonLocalizableKioskCollectionTypeBaseAdapter implements SeasonalEventType {
    private SeasonalEvent seasonalEventInfo;

    private SeasonalEventInfoConverter seasonalEventInfoConverter = new SeasonalEventInfoConverter();


    public SeasonalEventKioskCollectionAdapter(SeasonalEvent seasonalEventInfo, NonLocalizableKioskCollectionType baseKioskCollection, CollectionSharedProperties sharedCmsPiece) {
        super(baseKioskCollection, sharedCmsPiece);
        this.seasonalEventInfo = seasonalEventInfo;
    }

    public SeasonalEventKioskCollectionAdapter(SeasonalEventType seasonalEventType) {
        this(seasonalEventType, seasonalEventType, null);
    }

    public SeasonalEventKioskCollectionAdapter(SeasonalEvent seasonalEventInfo, NonLocalizableKioskCollectionType baseKioskCollection) {
        this(seasonalEventInfo, baseKioskCollection, null);
    }

    public SeasonalEventKioskCollectionAdapter(CollectionSharedProperties sharedCmsPiece) {
        this(null, null, sharedCmsPiece);
    }

    @Override
    public KioskCollectionField<LocalDate> startDate() {
        return getSeasonalEventInfo().startDate();
    }

    @Override
    public KioskCollectionField<String> eventLanguage() {
        return getSeasonalEventInfo().eventLanguage();
    }

    @Override
    public KioskCollectionField<Long> seasonId() {
        return getSeasonalEventInfo().seasonId();
    }

    @Override
    protected void processCmsBuilder(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder) {
        seasonalEventInfoConverter.transferKioskRepToCmsBuilders(sharedCmsBuilder, this.seasonalEventInfo);

    }

    protected SeasonalEvent getSeasonalEventInfo() {
        if(this.seasonalEventInfo == null) {
            buildAndSetLocation();
        }

        return this.seasonalEventInfo;
    }

    protected void buildAndSetLocation() {
        this.seasonalEventInfo = seasonalEventInfoConverter.convert(getSharedCmsPiece());
    }

     public static class Builder extends NonLocalizableCmsCollectionAdapterBuilder<Builder, SeasonalEventType, SeasonalEventKioskCollectionAdapter> {

        @Override
        protected SeasonalEventKioskCollectionAdapter buildChild() {
            return new SeasonalEventKioskCollectionAdapter(super.kioskCollection, super.kioskCollection, super.sharedCmsPiece);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }
}
