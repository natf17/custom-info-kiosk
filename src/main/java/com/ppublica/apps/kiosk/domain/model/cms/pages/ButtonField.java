package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class ButtonField extends PageField<Boolean> {
    
    public ButtonField(String fieldName, Boolean fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return false;
    }
    
}
