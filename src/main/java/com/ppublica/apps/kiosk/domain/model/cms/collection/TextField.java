package com.ppublica.apps.kiosk.domain.model.cms.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageField;

public class TextField extends PageField<String> {

    public TextField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }

    public TextField(String fieldType, String fieldName, String fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}
