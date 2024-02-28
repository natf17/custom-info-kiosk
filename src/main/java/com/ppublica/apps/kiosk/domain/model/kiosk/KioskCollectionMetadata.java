package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public record KioskCollectionMetadata(Long localeId, String kioskLocale, Status status, LocalDate createdOn, LocalDateTime lastModified) {

    public CollectionInternals getCollectionInternals() {
        
        return new CollectionInternals(localeId, kioskLocale, PageStatus.valueOf(this.status.toString()), createdOn, lastModified);
    }

    public static KioskCollectionMetadata fromCollectionInternals(CollectionInternals collectionInternals) {
        return new KioskCollectionMetadata(collectionInternals.getKioskLocaleId(), collectionInternals.getKioskLocale(), Status.valueOf(collectionInternals.getStatus().toString()), 
                        collectionInternals.getCreatedOn(), collectionInternals.getLastModified());
        
    }
}
