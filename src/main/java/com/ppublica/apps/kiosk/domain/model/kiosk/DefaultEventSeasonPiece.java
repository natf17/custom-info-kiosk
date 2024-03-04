package com.ppublica.apps.kiosk.domain.model.kiosk;

public record DefaultEventSeasonPiece(KioskCollectionField<String> seasonType, KioskCollectionField<Integer> durationDays, KioskCollectionField<String> theme, KioskCollectionField<Integer> serviceYear, KioskCollectionField<String> durationText) implements EventSeason {
    
    public static class Builder {
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

        public EventSeason build() {
            validateAndPrepare();

            return new DefaultEventSeasonPiece(seasonType, durationDays, theme, serviceYear, durationText);
        }
            
        protected void validateAndPrepare() {
    
            if(seasonType == null) {   
                seasonType = new KioskCollectionField<String>(null, true);
            }
                
            if(durationDays == null) {   
                durationDays = new KioskCollectionField<Integer>(null, true);
            }

            if(theme == null) {   
                theme = new KioskCollectionField<String>(null, true);
            }

            if(serviceYear == null) {   
                serviceYear = new KioskCollectionField<Integer>(null, true);
            }

            if(durationText == null) {   
                durationText = new KioskCollectionField<String>(null, true);
            }

        }
    }
}
