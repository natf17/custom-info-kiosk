package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public class EditedImageContainer {
    private final EditedPageFieldPayload<String> imageAltText;
    private final EditedPageFieldPayload<Image> featureImage;

    public EditedImageContainer(EditedPageFieldPayload<Image> featureImage, EditedPageFieldPayload<String> imageAltText) {
        this.imageAltText = imageAltText;
        this.featureImage = featureImage;
    }

    public EditedPageFieldPayload<String> getImageAltTextField() {
        return imageAltText; 
    }

    public EditedPageFieldPayload<Image> getImageField() {
        return featureImage;
    }

    public static EditedImageContainer empty(String featureImageFieldName, String imageAltTextFieldName) {
        EditedPageFieldPayload<Image> featureImage = new EditedPageFieldPayload<Image>(featureImageFieldName, null);
        EditedPageFieldPayload<String> imageAltText = new EditedPageFieldPayload<String>(imageAltTextFieldName, null);

        return new EditedImageContainer(featureImage, imageAltText);
    }

    public ImageContainer toImageContainer(String featureImageFieldType, String imageAltTextFieldType) {
        KioskPageField<Image> featureImageField = featureImage.toKioskPageField(featureImageFieldType, false);
        KioskPageField<String> featureImageAltTextField = imageAltText.toKioskPageField(imageAltTextFieldType, true);

        return new ImageContainer(featureImageField, featureImageAltTextField);
        
    }
}
