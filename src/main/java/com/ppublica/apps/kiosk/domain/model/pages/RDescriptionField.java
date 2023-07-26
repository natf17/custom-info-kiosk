package com.ppublica.apps.kiosk.domain.model.pages;

public class RDescriptionField extends PageField<String>{
    
    public RDescriptionField(String fieldName) {
        super(fieldName);
    }

    public RDescriptionField(String fieldName, String richText) {
        super(fieldName, richText);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
}
