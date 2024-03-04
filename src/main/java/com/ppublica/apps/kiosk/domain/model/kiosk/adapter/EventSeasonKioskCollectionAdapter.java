package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.EventSeason;
import com.ppublica.apps.kiosk.domain.model.kiosk.EventSeasonType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.EventSeasonInfoConverter;

public class EventSeasonKioskCollectionAdapter extends KioskCollectionTypeBaseAdapter implements EventSeasonType {
    private EventSeason eventSeasonInfo;

    private EventSeasonInfoConverter eventSeasonInfoConverter = new EventSeasonInfoConverter();


    public EventSeasonKioskCollectionAdapter(EventSeason eventSeasonInfo, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
        this.eventSeasonInfo = eventSeasonInfo;
    }

    public EventSeasonKioskCollectionAdapter(EventSeasonType eventSeasonType) {
        this(eventSeasonType, eventSeasonType, null, null);
    }

    public EventSeasonKioskCollectionAdapter(EventSeason eventSeasonInfo, KioskCollectionType baseKioskCollection) {
        this(eventSeasonInfo, baseKioskCollection, null, null);
    }

    public EventSeasonKioskCollectionAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, localizedCmsPiece, sharedCmsPiece);
    }

    @Override
    public KioskCollectionField<String> seasonType() {
        return getEventSeasonInfo().seasonType();
    }

    @Override
    public KioskCollectionField<Integer> durationDays() {
        return getEventSeasonInfo().durationDays();
    }

    @Override
    public KioskCollectionField<String> theme() {
        return getEventSeasonInfo().theme();
    }

    @Override
    public KioskCollectionField<Integer> serviceYear() {
        return getEventSeasonInfo().serviceYear();
    }

    @Override
    public KioskCollectionField<String> durationText() {
        return getEventSeasonInfo().durationText();
    }

    @Override
    protected void processCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder) {
        eventSeasonInfoConverter.transferKioskRepToCmsBuilders(sharedCmsBuilder, localizedCmsBuilder, this.eventSeasonInfo);

    }

    protected EventSeason getEventSeasonInfo() {
        if(this.eventSeasonInfo == null) {
            buildAndSetLocation();
        }

        return this.eventSeasonInfo;
    }

    protected void buildAndSetLocation() {
        this.eventSeasonInfo = eventSeasonInfoConverter.convert(getSharedCmsPiece(), getLocalizedCmsPiece());
    }

     public static class Builder extends CmsCollectionAdapterBuilder<Builder, EventSeasonType, EventSeasonKioskCollectionAdapter> {

        @Override
        protected EventSeasonKioskCollectionAdapter buildChild() {
            return new EventSeasonKioskCollectionAdapter(super.kioskCollection, super.kioskCollection, super.localizedCmsPiece, super.sharedCmsPiece);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }
}
