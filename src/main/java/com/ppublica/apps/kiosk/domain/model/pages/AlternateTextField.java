package com.ppublica.apps.kiosk.domain.model.pages;

public class AlternateTextField extends PageField<String> {
    public AlternateTextField(String fieldName) {
        super(fieldName);
    }

    public AlternateTextField(String fieldName, String altText) {
        super(fieldName, altText);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }

}
