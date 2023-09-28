package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.selector.RedirectUrlContainerRegFieldSelector;

public class RedirectUrlContainer {
    private KioskPageField<String> redirectUrl;
    private KioskPageField<String> redirectDisplayText;
    private KioskPageField<String> redirectDescription;

    public RedirectUrlContainer(KioskPageField<String> redirectUrl, KioskPageField<String> redirectDisplayText, KioskPageField<String> redirectDescription) {
        this.redirectUrl = redirectUrl;
        this.redirectDisplayText = redirectDisplayText;
        this.redirectDescription = redirectDescription;
    }

    public KioskPageField<String> getRedirectUrlField() {
        return this.redirectUrl;
    }

    public KioskPageField<String> getRedirectDisplayTextField() {
        return this.redirectDisplayText;
    }

    public KioskPageField<String> getRedirectDescriptionField() {
        return this.redirectDescription;
    }

    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {
        FieldContainer imageFC = new FieldContainer.Builder()
                            .addUrlField(converter.toUrlField(redirectUrl))
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(redirectDisplayText))
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(redirectDescription))
                            .fieldContainerName(containerName)
                            .build();

        return imageFC;
    }

    /*
     * Expects a fieldContainer with two RegularTextLongDescriptionFields and one UrlField
     */
    public static RedirectUrlContainer fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter, RedirectUrlContainerRegFieldSelector fieldSelector) {
        UrlField cmsRedirectUrlField = fieldContainer.getUrlFields().get(0);
        RegularTextLongDescriptionField cmsRedirectDisplayTextField = fieldSelector.selectRedirectDisplayTextField(fieldContainer);
        RegularTextLongDescriptionField cmsRedirectDescriptionField = fieldSelector.selectRedirectDescriptionField(fieldContainer);

        
        KioskPageField<String> redirectUrlField = converter.toStringField(cmsRedirectUrlField);
        KioskPageField<String> redirectDisplayTextField = converter.toStringField(cmsRedirectDisplayTextField);
        KioskPageField<String> redirectDescriptionField = converter.toStringField(cmsRedirectDescriptionField);
  
        return new RedirectUrlContainer(redirectUrlField, redirectDisplayTextField, redirectDescriptionField);
    }

    
}
