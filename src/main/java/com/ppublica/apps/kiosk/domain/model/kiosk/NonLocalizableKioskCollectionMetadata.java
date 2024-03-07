package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public record NonLocalizableKioskCollectionMetadata(Status status, LocalDate createdOn, LocalDateTime lastModified) {

    public CollectionInternals getCollectionInternals() {
        
        return new CollectionInternals(null, null, PageStatus.valueOf(this.status.toString()), createdOn, lastModified);
    }

    public static NonLocalizableKioskCollectionMetadata fromCollectionInternals(CollectionInternals collectionInternals) {
        return new NonLocalizableKioskCollectionMetadata(Status.valueOf(collectionInternals.getStatus().toString()), 
                        collectionInternals.getCreatedOn(), collectionInternals.getLastModified());
        
    }
}
