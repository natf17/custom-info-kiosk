package com.ppublica.apps.kiosk.domain.model.cms.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageField;

public class CollectionNameField extends PageField<String> {
    public static String FIELD_TYPE = "COLL_NAME";

    public CollectionNameField(String fieldName) {
        this(FIELD_TYPE, fieldName);
    }

    public CollectionNameField(String fieldName, String fieldValue) {
        super(FIELD_TYPE, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}