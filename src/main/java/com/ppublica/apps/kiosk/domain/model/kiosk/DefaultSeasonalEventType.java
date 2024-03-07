package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;

public class DefaultSeasonalEventType extends NonLocalizableKioskCollectionTypeBase implements SeasonalEventType {
    public static final CollectionType KIOSK_COLLECTION_TYPE_NAME = CollectionType.SEASONAL_EVENT;

    private SeasonalEvent seasonalEventInfo;

    protected DefaultSeasonalEventType(NonLocalizableKioskCollectionType kioskCollectionType, SeasonalEvent seasonalEventInfo) {
        super(kioskCollectionType);
        this.seasonalEventInfo = seasonalEventInfo;
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

    public static class Builder extends NonLocalizableKioskCollectionTypeBase.Builder<Builder, DefaultSeasonalEventType> {
        protected KioskCollectionField<LocalDate> startDate;
        protected KioskCollectionField<String> eventLanguage;
        protected KioskCollectionField<Long> seasonId;
            
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
        protected DefaultSeasonalEventType buildChild(NonLocalizableKioskCollectionType kioskCollectionType) {
            SeasonalEvent eventSeasonInfo = new DefaultSeasonalEventPiece.Builder()
                                                        .startDate(startDate)
                                                        .eventLanguage(eventLanguage)
                                                        .seasonId(seasonId)
                                                        .build();
            return new DefaultSeasonalEventType(kioskCollectionType, eventSeasonInfo);
        }

    }
}