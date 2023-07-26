package com.ppublica.apps.kiosk.domain.model.pages;

/*
 * Represents a page title field on a page.
 */
public class PageTitleField extends PageField<String>{

    public PageTitleField(String fieldName) {
        super (fieldName);
    }

    public PageTitleField(String fieldName, String fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
    
}
