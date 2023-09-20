package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class UrlField extends PageField<String> {

    public UrlField(String fieldName) {
        super(fieldName);
    }
    
    public UrlField(String fieldName, String fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return false;
    }
}
