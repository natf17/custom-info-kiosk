package com.ppublica.apps.kiosk.domain.model.pages;

/*
 * Represents an image field with a subfield representing alternative text.
 */
public class ImageField extends PageField<Image>{
    private static final String IMG_FIELD_ALTTEXT_FIELD_NAME = "alternativeText";

    private AlternateTextField altTextField;

    public ImageField(String fieldName) {
        this(fieldName, null);
     }

    public ImageField(String fieldName, Image image) {
        super(fieldName, image);
        this.altTextField = new AlternateTextField(IMG_FIELD_ALTTEXT_FIELD_NAME);
    }


    public ImageField(String fieldName, Image image, String altText) {
        super(fieldName, image);
        this.altTextField = new AlternateTextField(IMG_FIELD_ALTTEXT_FIELD_NAME, altText);

    }

    public AlternateTextField getAltTextField() {
        return this.altTextField;
    }

    @Override
    public boolean isLocalized() {
        return false;
    }

    
}
