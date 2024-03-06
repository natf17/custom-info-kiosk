package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;

public class DefaultSeasonalEventType extends KioskCollectionTypeBase implements SeasonalEventType{
    public static final CollectionType KIOSK_COLLECTION_TYPE_NAME = CollectionType.SEASONAL_EVENT;

    private SeasonalEvent seasonalEventInfo;

    protected DefaultSeasonalEventType(KioskCollectionType kioskCollectionType, SeasonalEvent seasonalEventInfo) {
        super(kioskCollectionType);
        this.seasonalEventInfo = seasonalEventInfo;
    }

    @Override
    public KioskCollectionField<String> seasonType() {
        return this.seasonalEventInfo.seasonType();
    }

    @Override
    public KioskCollectionField<LocalDate> startDate() {
        return this.seasonalEventInfo.startDate();
    }

    @Override
    public KioskCollectionField<String> eventLanguage() {
        return this.seasonalEventInfo.eventLanguage();
    }

    @Override
    public KioskCollectionField<Long> seasonId() {
        return this.seasonalEventInfo.seasonId();
    }

    public static class Builder extends KioskCollectionTypeBase.Builder<Builder, DefaultSeasonalEventType> {
        protected KioskCollectionField<String> seasonType;
        protected KioskCollectionField<LocalDate> startDate;
        protected KioskCollectionField<String> eventLanguage;
        protected KioskCollectionField<Long> seasonId;
            
        public Builder seasonType(KioskCollectionField<String> seasonType) {
            this.seasonType = seasonType;
            return this;
        }

        public Builder startDate(KioskCollectionField<LocalDate> startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder eventLanguage(KioskCollectionField<String> eventLanguage) {
            this.eventLanguage = eventLanguage;
            return this;
        }

        public Builder seasonId(KioskCollectionField<Long> seasonId) {
            this.seasonId = seasonId;
            return this;
        }

        public Builder() {
            super(KIOSK_COLLECTION_TYPE_NAME);
        }

        @Override
        protected void validateAndPrepareChild() { }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected DefaultSeasonalEventType buildChild(KioskCollectionType kioskCollectionType) {
            SeasonalEvent eventSeasonInfo = new DefaultSeasonalEventPiece.Builder()
                                                        .seasonType(seasonType)
                                                        .startDate(startDate)
                                                        .eventLanguage(eventLanguage)
                                                        .seasonId(seasonId)
                                                        .build();
            return new DefaultSeasonalEventType(kioskCollectionType, eventSeasonInfo);
        }

    }
}