package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public record NonLocalizableKioskCollectionMetadata(Status status, LocalDate createdOn, LocalDateTime lastModified) {

    public CollectionSharedInternals getCollectionInternals() {
        
        return new CollectionSharedInternals(PageStatus.valueOf(this.status.toString()), createdOn, lastModified);
    }

    public static NonLocalizableKioskCollectionMetadata fromCollectionInternals(CollectionSharedInternals collectionInternals) {
        return new NonLocalizableKioskCollectionMetadata(Status.valueOf(collectionInternals.getStatus().toString()), 
                        collectionInternals.getCreatedOn(), collectionInternals.getLastModified());
        
    }
}
