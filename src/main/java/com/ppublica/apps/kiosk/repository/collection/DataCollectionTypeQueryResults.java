package com.ppublica.apps.kiosk.repository.collection;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;

public class DataCollectionTypeQueryResults {
    private Long id;
    private String type;
    private String subType;
    private Long localizedFieldsId;
    private CollectionInternals collectionInternals;
    private CollectionNameField locCollNameField;
    private CollectionInternals localizedCollInternals;


    public DataCollectionTypeQueryResults(Long id, String type, String subType, LocalizedFieldsResults localizedFieldsResults, CollectionInternals collectionInternals) {
        this.id = id;
        this.type = type;
        this.subType = subType;
        this.localizedFieldsId = localizedFieldsResults.getLocalizedFieldsId();
        this.locCollNameField = localizedFieldsResults.getLocCollNameField();
        this.localizedCollInternals = localizedFieldsResults.getLocalizedCollInternals();
        this.collectionInternals = collectionInternals;
    }

    public Long getCollectionId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getSubType() {
        return this.subType;
    }

    public Long getLocalizedFieldsId() {
        return this.localizedFieldsId;
    }
    
    public CollectionInternals getCollectionInternals() {
        return this.collectionInternals;
    }

    public CollectionInternals getLocalizedCollectionInternals() {
        return this.localizedCollInternals;
    }

    public CollectionNameField getLocalizedCollectionNameField() {
        return this.locCollNameField;
    }


}
