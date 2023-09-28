package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class PageTitleField extends PageField<String> {

    public PageTitleField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }

    public PageTitleField(String fieldType, String fieldName, String fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}
