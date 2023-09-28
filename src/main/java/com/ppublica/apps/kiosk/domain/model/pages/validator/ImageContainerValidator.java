package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.ImageContainer;

public class ImageContainerValidator {

    private final String imageFieldType;
    private final String imageAltTextFieldType;

    public ImageContainerValidator(String imageFieldType, String imageAltTextFieldType) {
        this.imageFieldType = imageFieldType;
        this.imageAltTextFieldType  = imageAltTextFieldType;
    }

    public boolean isValid(ImageContainer imageContainer) {
        boolean isValid = isValidImageField(imageContainer);
        isValid = isValidImageAltTextField(imageContainer);

        return isValid;

    }   

    private boolean isValidImageField(ImageContainer container) {
        if(container.getImageField().getFieldType().equals(imageFieldType)) {
            return true;
        }

        return false;
    }

    private  boolean isValidImageAltTextField(ImageContainer container) {
        if(container.getImageAltTextField().getFieldType().equals(imageAltTextFieldType)) {
            return true;
        }

        return false;
    }
}
