package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class KioskCollectionMetadata {
    private Long localeId;
    private PageStatus status;
    private LocalDate createdOn;
    private LocalDateTime lastModified;

    public KioskCollectionMetadata(Long localeId, PageStatus status, LocalDate createdOn, LocalDateTime lastModified) {
        this.localeId = localeId;
        this.status = status;
        this.createdOn = createdOn;
        this.lastModified = lastModified;
    }

    public CollectionInternals getCollectionInternals() {
        return new CollectionInternals(localeId, status, createdOn, lastModified);
    }

    public static KioskCollectionMetadata fromCollectionInternals(CollectionInternals collectionInternals) {
        return new KioskCollectionMetadata(collectionInternals.getKioskLocaleId(), collectionInternals.getStatus(), 
                        collectionInternals.getCreatedOn(), collectionInternals.getLastModified());
        
    }
}
