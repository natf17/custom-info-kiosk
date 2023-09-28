package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class ButtonField extends PageField<Boolean> {

    public ButtonField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }
    
    public ButtonField(String fieldType, String fieldName, Boolean fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return false;
    }
    
}
