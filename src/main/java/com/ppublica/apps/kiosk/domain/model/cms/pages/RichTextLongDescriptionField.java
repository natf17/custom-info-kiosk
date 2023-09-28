package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class RichTextLongDescriptionField extends PageField<String> {

    public RichTextLongDescriptionField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }

    public RichTextLongDescriptionField(String fieldType, String fieldName, String fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}
