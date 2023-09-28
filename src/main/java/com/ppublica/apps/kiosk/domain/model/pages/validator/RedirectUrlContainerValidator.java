package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.RedirectUrlContainer;

public class RedirectUrlContainerValidator {

    private String redirectUrlFieldType;
    private String redirectDisplayTextFieldType;
    private String redirectDescriptionFieldType;

    public RedirectUrlContainerValidator(String redirectUrlFieldType, String redirectDisplayTextFieldType, String redirectDescriptionFieldType) {
        this.redirectUrlFieldType = redirectUrlFieldType;
        this.redirectDisplayTextFieldType = redirectDisplayTextFieldType;
        this.redirectDescriptionFieldType = redirectDescriptionFieldType;
    }

    public boolean isValid(RedirectUrlContainer container) {
        boolean isValid = container.getRedirectUrlField().getFieldType().equals(redirectUrlFieldType);
        isValid = container.getRedirectDisplayTextField().getFieldType().equals(redirectDisplayTextFieldType);
        isValid = container.getRedirectDescriptionField().getFieldType().equals(redirectDescriptionFieldType);

        return isValid;
    }
}
