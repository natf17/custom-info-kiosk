package com.ppublica.apps.kiosk.domain.model.cms.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageField;

public class LinkedCollectionField extends PageField<Long> {

    public LinkedCollectionField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }

    public LinkedCollectionField(String fieldType, String fieldName, Long id) {
        super(fieldType, fieldName, id);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}