package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class DefaultEventSeason implements EventSeason {
    private Long id;
    private KioskCollectionMetadata kioskCollectionMetadata;
    private KioskCollectionField<String> collectionNameField;
    private KioskCollectionField<Long> seasonYear;
    private KioskCollectionField<Long> durationDays;
    private KioskCollectionField<String> theme;
    private KioskCollectionField<String> durationText;
    private KioskCollectionField<String> addSeasonDatesText;
    private List<DefaultSeasonalEvent> events;

    public DefaultEventSeason(Long id, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata, KioskCollectionField<Long> seasonYear,
                                 KioskCollectionField<Long> durationDays, KioskCollectionField<String> theme, KioskCollectionField<String> durationText, KioskCollectionField<String> addSeasonDatesText, List<DefaultSeasonalEvent> events) {
        this.id = id;
        this.collectionNameField = collectionNameField;
        this.kioskCollectionMetadata = kioskCollectionMetadata;
        this.seasonYear = seasonYear;
        this.durationDays = durationDays;
        this.theme = theme;
        this.durationText = durationText;
        this.addSeasonDatesText = addSeasonDatesText;
        this.events = events;

        for (DefaultSeasonalEvent event : events) {
            event.setEventSeason(this);
        }
    }

    public Long getId() {
        return this.id;
    }

    public KioskCollectionField<String> getKioskCollectionNameField() {
        return this.collectionNameField;
    }

    public KioskCollectionMetadata getKioskCollectionMetadata() {
        return this.kioskCollectionMetadata;
    }

    public KioskCollectionField<Long> getSeasonYear() {
        return this.seasonYear;
    }

    public KioskCollectionField<Long> getDurationDays() {
        return this.durationDays;
    }

    public KioskCollectionField<String> getTheme() {
        return this.theme;
    }

    public KioskCollectionField<String> getDurationText() {
        return this.durationText;
    }

    public KioskCollectionField<String> getAddSeasonDatesText() {
        return this.addSeasonDatesText;
    }

    public List<DefaultSeasonalEvent> getEvents() {
        return this.events;
    }

    public static class Builder {
        protected Long id;
        protected KioskCollectionField<String> collectionNameField;

        protected KioskCollectionMetadata kioskCollectionMetadata;
        private PageStatus pageStatus;
        private LocalDate createdOn;
        private LocalDateTime lastModified;
        private Long kioskLocaleId;

        protected KioskCollectionField<Long> seasonYear;
        protected KioskCollectionField<Long> durationDays;
        protected KioskCollectionField<String> theme;
        protected KioskCollectionField<String> durationText;
        protected KioskCollectionField<String> addSeasonDatesText;
        protected List<DefaultSeasonalEvent> events = new ArrayList<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder collectionNameField(KioskCollectionField<String> collectionNameField) {
            this.collectionNameField = collectionNameField;
            return this;
        }

        public Builder kioskCollectionMetadata(KioskCollectionMetadata kioskCollectionMetadata) {
            this.kioskCollectionMetadata = kioskCollectionMetadata;
            return this;
        }

        public Builder pageStatus(PageStatus pageStatus) {
            this.pageStatus = pageStatus;
            return this;
        }

        public Builder createdOn(LocalDate createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Builder lastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder seasonYear(KioskCollectionField<Long> seasonYear) {
            this.seasonYear = seasonYear;
            return this;
        }

        public Builder durationDays(KioskCollectionField<Long> durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public Builder theme(KioskCollectionField<String> theme) {
            this.theme = theme;
            return this;
        }

        public Builder durationText(KioskCollectionField<String> durationText) {
            this.durationText = durationText;
            return this;
        }

        public Builder addSeasonDatesText(KioskCollectionField<String> addSeasonDatesText) {
            this.addSeasonDatesText = addSeasonDatesText;
            return this;
        }

        public Builder events(List<DefaultSeasonalEvent> events) {
            if(events == null) {
                throw new RuntimeException("A null value is not allowed");
            }
            this.events = events;
            return this;
        }

        public Builder addEvent(DefaultSeasonalEvent event) {
            this.events.add(event);
            return this;
        }

        public DefaultEventSeason build() {

            if(kioskCollectionMetadata == null) {
                if(this.pageStatus == null) {
                    throw new RuntimeException("PageStatus is required");
                }

                if(this.createdOn == null) {
                    throw new RuntimeException("CreatedOn is required");
                }

                if(this.lastModified == null) {
                    throw new RuntimeException("LastModified is required");
                }

                LocalDateTime createdOnTime = this.createdOn.atStartOfDay();
                if(createdOnTime.isAfter(this.lastModified)) {
                    throw new RuntimeException("CreatedOn cannot be after lastModified");
                }

                if(this.kioskLocaleId == null) {
                    throw new RuntimeException("Locale id is required");
                }

                kioskCollectionMetadata = new KioskCollectionMetadata(kioskLocaleId, pageStatus, createdOn, lastModified);

            }

            return new DefaultEventSeason(id, collectionNameField, kioskCollectionMetadata, seasonYear, durationDays, theme, durationText, addSeasonDatesText, events);
        }

    }

}
