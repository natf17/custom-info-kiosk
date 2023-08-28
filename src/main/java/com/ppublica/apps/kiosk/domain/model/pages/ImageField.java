package com.ppublica.apps.kiosk.domain.model.pages;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

/*
 * Represents an image field with a subfield representing alternative text.
 */
public class ImageField extends PageField<Image>{
    private static final String IMG_FIELD_ALTTEXT_FIELD_NAME = "alternativeText";

    @Id
    private Long id;
    
    private AlternateTextField altTextField;

    // for use by repository classes ONLY
    @PersistenceCreator
    public ImageField(String fieldName, Image fieldValue, AlternateTextField altTextField) {
        super(fieldName, fieldValue);
        this.altTextField = altTextField;
    }

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
