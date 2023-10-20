package com.ppublica.apps.kiosk.domain.model.cms.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageField;

public class BooleanField extends PageField<Boolean> {

    public BooleanField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }

    public BooleanField(String fieldType, String fieldName, Boolean fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}
