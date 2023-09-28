package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;

public class ImageContainer {
    private KioskPageField<Image> image;
    private KioskPageField<String> imageAltText;

    public ImageContainer(KioskPageField<Image> image, KioskPageField<String> imageAltText) {
        this.image = image;
        this.imageAltText = imageAltText;
    }

    public KioskPageField<Image> getImageField() {
        return this.image;
    }

    public KioskPageField<String> getImageAltTextField() {
        return this.imageAltText;
    }

    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {
        FieldContainer imageFC = new FieldContainer.Builder()
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(imageAltText))
                            .addImageField(converter.toImageField(image))
                            .fieldContainerName(containerName)
                            .build();

        return imageFC;
    }

    /*
     * Expects a fieldContainer with a single ImageField and RegularTextLongDescriptionField
     */
    public static ImageContainer fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter) {
        ImageField cmsImageField = fieldContainer.getImageFields().get(0);
        RegularTextLongDescriptionField cmsImageAltTextField = fieldContainer.getRegularTextLongDescriptionFields().get(0);
        
        KioskPageField<Image> imageField = converter.toImageField(cmsImageField);
        KioskPageField<String> imageAltTextField = converter.toStringField(cmsImageAltTextField);
  
        return new ImageContainer(imageField, imageAltTextField);
    }
    
}
