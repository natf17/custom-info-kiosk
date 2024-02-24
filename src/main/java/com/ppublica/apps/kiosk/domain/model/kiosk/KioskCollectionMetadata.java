package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public record KioskCollectionMetadata(Long localeId, Status status, LocalDate createdOn, LocalDateTime lastModified) {

    public CollectionInternals getCollectionInternals() {
        return new CollectionInternals(localeId, null, PageStatus.DRAFT, createdOn, lastModified);
    }

    public static KioskCollectionMetadata fromCollectionInternals(CollectionInternals collectionInternals) {
        return new KioskCollectionMetadata(collectionInternals.getKioskLocaleId(), Status.DRAFT, 
                        collectionInternals.getCreatedOn(), collectionInternals.getLastModified());
        
    }
}
