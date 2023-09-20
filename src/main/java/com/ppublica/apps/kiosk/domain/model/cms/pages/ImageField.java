package com.ppublica.apps.kiosk.domain.model.cms.pages;

public class ImageField extends PageField<Image>{

    public ImageField(String fieldName) {
        super(fieldName, new Image());
    }

    public ImageField(String fieldName, Image fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
}
