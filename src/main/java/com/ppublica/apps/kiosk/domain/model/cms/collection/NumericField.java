package com.ppublica.apps.kiosk.domain.model.cms.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageField;

public class NumericField extends PageField<Long> {

    public NumericField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }

    public NumericField(String fieldType, String fieldName, Long fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}
