package com.ppublica.apps.kiosk.domain.model.kiosk;

/*
 * A localizable kiosk collection type.
 */
public interface KioskCollectionType {

    Long collectionId();

    CollectionType kioskCollectionType();

    KioskCollectionField<String> kioskCollectionNameField();

    KioskCollectionMetadata kioskCollectionMetadata();

}
