package com.ppublica.apps.kiosk.repository.collection;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;

public class SimpleCollectionTypeQueryResults {
    private Long id;
    private String type;
    private CollectionNameField collectionNameField;
    private CollectionInternals collectionInternals;
    

    public SimpleCollectionTypeQueryResults(Long id, String type, CollectionNameField collectionNameField, CollectionInternals collectionInternals) {
        
        this.id = id;
        this.type = type;
        this.collectionNameField = collectionNameField;
        this.collectionInternals = collectionInternals;
        
    }

    public Long getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public CollectionNameField getCollectionNameField() {
        return this.collectionNameField;
    }

    public CollectionInternals getCollectionInternals() {
        return this.collectionInternals;
    }
    
}
