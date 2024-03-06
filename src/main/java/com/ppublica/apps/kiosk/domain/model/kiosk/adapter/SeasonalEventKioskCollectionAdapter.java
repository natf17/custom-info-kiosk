package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import java.time.LocalDate;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEvent;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.SeasonalEventInfoConverter;

public class SeasonalEventKioskCollectionAdapter extends KioskCollectionTypeBaseAdapter implements SeasonalEventType {
    private SeasonalEvent seasonalEventInfo;

    private SeasonalEventInfoConverter seasonalEventInfoConverter = new SeasonalEventInfoConverter();


    public SeasonalEventKioskCollectionAdapter(SeasonalEvent seasonalEventInfo, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
        this.seasonalEventInfo = seasonalEventInfo;
    }

    public SeasonalEventKioskCollectionAdapter(SeasonalEventType seasonalEventType) {
        this(seasonalEventType, seasonalEventType, null, null);
    }

    public SeasonalEventKioskCollectionAdapter(SeasonalEvent seasonalEventInfo, KioskCollectionType baseKioskCollection) {
        this(seasonalEventInfo, baseKioskCollection, null, null);
    }

    public SeasonalEventKioskCollectionAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, localizedCmsPiece, sharedCmsPiece);
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
    protected void processCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder) {
        seasonalEventInfoConverter.transferKioskRepToCmsBuilders(sharedCmsBuilder, localizedCmsBuilder, this.seasonalEventInfo);

    }

    protected SeasonalEvent getSeasonalEventInfo() {
        if(this.seasonalEventInfo == null) {
            buildAndSetLocation();
        }

        return this.seasonalEventInfo;
    }

    protected void buildAndSetLocation() {
        this.seasonalEventInfo = seasonalEventInfoConverter.convert(getSharedCmsPiece(), getLocalizedCmsPiece());
    }

     public static class Builder extends CmsCollectionAdapterBuilder<Builder, SeasonalEventType, SeasonalEventKioskCollectionAdapter> {

        @Override
        protected SeasonalEventKioskCollectionAdapter buildChild() {
            return new SeasonalEventKioskCollectionAdapter(super.kioskCollection, super.kioskCollection, super.localizedCmsPiece, super.sharedCmsPiece);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }
}
