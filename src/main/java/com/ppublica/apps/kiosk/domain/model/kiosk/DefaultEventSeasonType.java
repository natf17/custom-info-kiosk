package com.ppublica.apps.kiosk.domain.model.kiosk;

public class DefaultEventSeasonType extends KioskCollectionTypeBase implements EventSeasonType{
    public static final CollectionType KIOSK_COLLECTION_TYPE_NAME = CollectionType.EVENT_SEASON;

    private EventSeason eventSeasonInfo;

    protected DefaultEventSeasonType(KioskCollectionType kioskCollectionType, EventSeason eventSeasonInfo) {
        super(kioskCollectionType);
        this.eventSeasonInfo = eventSeasonInfo;
    }

    @Override
    public KioskCollectionField<String> seasonType() {
        return this.eventSeasonInfo.seasonType();
    }

    @Override
    public KioskCollectionField<Integer> durationDays() {
        return this.eventSeasonInfo.durationDays();
    }

    @Override
    public KioskCollectionField<String> theme() {
        return this.eventSeasonInfo.theme();
    }

    @Override
    public KioskCollectionField<Integer> serviceYear() {
        return this.eventSeasonInfo.serviceYear();
    }

    @Override
    public KioskCollectionField<String> durationText() {
        return this.eventSeasonInfo.durationText();
    }

    public static class Builder extends KioskCollectionTypeBase.Builder<Builder, DefaultEventSeasonType> {

        protected KioskCollectionField<String> seasonType;
        protected KioskCollectionField<Integer> durationDays;
        protected KioskCollectionField<String> theme;
        protected KioskCollectionField<Integer> serviceYear;
        protected KioskCollectionField<String> durationText;
            
        public Builder seasonType(KioskCollectionField<String> seasonType) {
            this.seasonType = seasonType;
            return this;
        }

        public Builder durationDays(KioskCollectionField<Integer> durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public Builder theme(KioskCollectionField<String> theme) {
            this.theme = theme;
            return this;
        }

        public Builder serviceYear(KioskCollectionField<Integer> serviceYear) {
            this.serviceYear = serviceYear;
            return this;
        }

        public Builder durationText(KioskCollectionField<String> durationText) {
            this.durationText = durationText;
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
        protected DefaultEventSeasonType buildChild(KioskCollectionType kioskCollectionType) {
            EventSeason eventSeasonInfo = new DefaultEventSeasonPiece.Builder()
                                                        .seasonType(seasonType)
                                                        .durationDays(durationDays)
                                                        .theme(theme)
                                                        .serviceYear(serviceYear)
                                                        .durationText(durationText)
                                                        .build();
            return new DefaultEventSeasonType(kioskCollectionType, eventSeasonInfo);
        }

    }
}
