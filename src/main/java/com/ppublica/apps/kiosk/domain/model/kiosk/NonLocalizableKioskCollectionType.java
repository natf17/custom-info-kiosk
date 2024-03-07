package com.ppublica.apps.kiosk.domain.model.kiosk;

/*
 * The non-localizable equivalent of KioskCollectionType
 */
public interface NonLocalizableKioskCollectionType {
    Long collectionId();

    CollectionType kioskCollectionType();

    KioskCollectionField<String> kioskCollectionNameField();

    NonLocalizableKioskCollectionMetadata kioskCollectionMetadata();
}
