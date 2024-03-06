package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;

public record DefaultSeasonalEventPiece(KioskCollectionField<LocalDate> startDate, KioskCollectionField<String> eventLanguage, KioskCollectionField<Long> seasonId) implements SeasonalEvent {
    public static class Builder {
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


        public SeasonalEvent build() {
            validateAndPrepare();

            return new DefaultSeasonalEventPiece(startDate, eventLanguage, seasonId);
        }
            
        protected void validateAndPrepare() {
                
            if(startDate == null) {   
                startDate = new KioskCollectionField<LocalDate>(null, true);
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
