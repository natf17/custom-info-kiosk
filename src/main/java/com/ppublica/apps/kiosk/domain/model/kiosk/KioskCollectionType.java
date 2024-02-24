package com.ppublica.apps.kiosk.domain.model.kiosk;

public interface KioskCollectionType {

    Long collectionId();

    CollectionType kioskCollectionType();

    KioskCollectionField<String> kioskCollectionNameField();

    KioskCollectionMetadata kioskCollectionMetadata();
    

}
