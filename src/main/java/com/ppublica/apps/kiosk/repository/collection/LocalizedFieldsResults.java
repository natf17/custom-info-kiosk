package com.ppublica.apps.kiosk.repository.collection;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;

public class LocalizedFieldsResults {
    
    private Long localizedFieldsId;
    private CollectionInternals localizedCollInternals;
    private CollectionNameField locCollNameField;

    public LocalizedFieldsResults(Long localizedFieldsId, CollectionInternals localizedCollInternals, CollectionNameField locCollNameField) {
        this.localizedFieldsId = localizedFieldsId;
        this.localizedCollInternals = localizedCollInternals;
        this.locCollNameField = locCollNameField;
    }

    public Long getLocalizedFieldsId() {
        return this.localizedFieldsId;
    }

    public CollectionInternals getLocalizedCollInternals() {
        return this.localizedCollInternals;
    }

    public CollectionNameField getLocCollNameField() {
        return this.locCollNameField;
    }
}
