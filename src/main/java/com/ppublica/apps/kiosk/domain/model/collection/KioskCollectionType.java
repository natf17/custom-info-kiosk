package com.ppublica.apps.kiosk.domain.model.collection;

public interface KioskCollectionType {

    CollectionTypeName getKioskCollectionTypeName();

    KioskCollectionField<String> getKioskCollectionNameField();

    KioskCollectionMetadata getKioskCollectionMetadata();
    

}
