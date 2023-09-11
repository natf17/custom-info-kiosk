package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class PageTitleField extends PageField<String> {

    public PageTitleField(String fieldName, String fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}
