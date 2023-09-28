package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class RegularTextLongDescriptionField extends PageField<String> {

    public RegularTextLongDescriptionField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }

    public RegularTextLongDescriptionField(String fieldType, String fieldName, String fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
}
