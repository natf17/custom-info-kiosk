package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class KioskCollectionMetadata {
    private Long localeId;
    private String locale;
    private PageStatus status;
    private LocalDate createdOn;
    private LocalDateTime lastModified;

    public KioskCollectionMetadata(Long localeId, String locale, PageStatus status, LocalDate createdOn, LocalDateTime lastModified) {
        this.localeId = localeId;
        this.locale = locale;
        this.status = status;
        this.createdOn = createdOn;
        this.lastModified = lastModified;
    }

    public Long getKioskLocaleId() {
        return this.localeId;
    }

    public String getKioskLocale() {
        return this.locale;
    }

    public PageStatus getStatus() {
        return this.status;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public LocalDateTime getLastModified() {
        return this.lastModified;
    }

    public CollectionInternals getCollectionInternals() {
        return new CollectionInternals(localeId, locale, status, createdOn, lastModified);
    }

    public static KioskCollectionMetadata fromCollectionInternals(CollectionInternals collectionInternals) {
        return new KioskCollectionMetadata(collectionInternals.getKioskLocaleId(), collectionInternals.getKioskLocale(), collectionInternals.getStatus(), 
                        collectionInternals.getCreatedOn(), collectionInternals.getLastModified());
        
    }
}
