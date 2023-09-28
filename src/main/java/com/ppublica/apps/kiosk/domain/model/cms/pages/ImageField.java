package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class ImageField extends PageField<Image>{

    public ImageField(String fieldType, String fieldName) {
        super(fieldType, fieldName, new Image());
    }

    public ImageField(String fieldType, String fieldName, Image fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
}
