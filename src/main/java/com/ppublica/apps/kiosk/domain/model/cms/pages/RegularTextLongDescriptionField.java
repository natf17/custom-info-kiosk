package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class RegularTextLongDescriptionField extends PageField<String> {
    public RegularTextLongDescriptionField(String fieldName, String fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
}
