package com.ppublica.apps.kiosk.domain.model.collection;

public interface KioskCollectionType {

    Long getId();

    CollectionTypeName getKioskCollectionTypeName();

    KioskCollectionField<String> getKioskCollectionNameField();

    KioskCollectionMetadata getKioskCollectionMetadata();
    

}
