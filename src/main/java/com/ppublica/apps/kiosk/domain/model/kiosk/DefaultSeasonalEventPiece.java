package com.ppublica.apps.kiosk.domain.model.kiosk;

public record DefaultSeasonalEventPiece(KioskCollectionField<String> seasonType, KioskCollectionField<Long> startDate, KioskCollectionField<String> eventLanguage, KioskCollectionField<Long> seasonId) implements SeasonalEvent {
    public static class Builder {
        protected KioskCollectionField<String> seasonType;
        protected KioskCollectionField<Long> startDate;
        protected KioskCollectionField<String> eventLanguage;
        protected KioskCollectionField<Long> seasonId;
            
        public Builder seasonType(KioskCollectionField<String> seasonType) {
            this.seasonType = seasonType;
            return this;
        }

        public Builder startDate(KioskCollectionField<Long> startDate) {
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


        public SeasonalEvent build() {
            validateAndPrepare();

            return new DefaultSeasonalEventPiece(seasonType, startDate, eventLanguage, seasonId);
        }
            
        protected void validateAndPrepare() {
    
            if(seasonType == null) {   
                seasonType = new KioskCollectionField<String>(null, true);
            }
                
            if(startDate == null) {   
                startDate = new KioskCollectionField<Long>(null, true);
            }

            if(eventLanguage == null) {   
                eventLanguage = new KioskCollectionField<String>(null, true);
            }

            if(seasonId == null) {   
                seasonId = new KioskCollectionField<Long>(null, true);
            }

        }
    }
}
