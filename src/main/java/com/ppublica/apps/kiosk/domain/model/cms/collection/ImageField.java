package com.ppublica.apps.kiosk.domain.model.cms.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageField;

public class ImageField extends PageField<Image> {

    public ImageField(String fieldType, String fieldName) {
        super(fieldType, fieldName);
    }

    public ImageField(String fieldType, String fieldName, Image fieldValue) {
        super(fieldType, fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}