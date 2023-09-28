package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class UrlField extends PageField<String> {

    public UrlField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }
    
    public UrlField(String fieldType, String fieldName, String fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return false;
    }
}
