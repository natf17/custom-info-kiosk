package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class RichTextLongDescriptionField extends PageField<String> {

    public RichTextLongDescriptionField(String fieldName) {
        super(fieldName);
    }

    public RichTextLongDescriptionField(String fieldName, String fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}
