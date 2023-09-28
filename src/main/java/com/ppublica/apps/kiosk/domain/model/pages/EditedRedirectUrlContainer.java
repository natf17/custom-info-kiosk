package com.ppublica.apps.kiosk.domain.model.pages;

public class EditedRedirectUrlContainer {
    private final EditedPageFieldPayload<String> redirectUrl;
    private final EditedPageFieldPayload<String> redirectDisplayText;
    private final EditedPageFieldPayload<String> redirectDescription;

    public EditedRedirectUrlContainer(EditedPageFieldPayload<String> redirectUrl, EditedPageFieldPayload<String> redirectDisplayText, EditedPageFieldPayload<String> redirectDescription) {
        this.redirectUrl = redirectUrl;
        this.redirectDisplayText = redirectDisplayText;
        this.redirectDescription = redirectDescription;
    }

    public EditedPageFieldPayload<String> getRedirectUrlField() {
        return this.redirectUrl;
    }

    public EditedPageFieldPayload<String> getRedirectDisplayTextField() {
        return this.redirectDisplayText;
    }

    public EditedPageFieldPayload<String> getRedirectDescriptionField() {
        return this.redirectDescription;
    }

    public static EditedRedirectUrlContainer empty(String redirectUrlFieldName, String redirectDisplayTextFieldName, String redirectDescriptionFieldName) {
        EditedPageFieldPayload<String> redirectUrl = new EditedPageFieldPayload<String>(redirectUrlFieldName, null);
        EditedPageFieldPayload<String> redirectDisplayText = new EditedPageFieldPayload<String>(redirectDisplayTextFieldName, null);
        EditedPageFieldPayload<String> redirectDescription = new EditedPageFieldPayload<String>(redirectDescriptionFieldName, null);

        return new EditedRedirectUrlContainer(redirectUrl, redirectDisplayText, redirectDescription);
    }

    public RedirectUrlContainer toRedirectUrlContainer(String redirectUrlFieldType, String redirectDisplayTextFieldType, String redirectDescriptionFieldType) {
        KioskPageField<String> redirectUrlField = redirectUrl.toKioskPageField(redirectUrlFieldType, true);
        KioskPageField<String> redirectDisplayTextField = redirectDisplayText.toKioskPageField(redirectDisplayTextFieldType, true);
        KioskPageField<String> redirectDescriptionField = redirectDescription.toKioskPageField(redirectDescriptionFieldType, true);

        return new RedirectUrlContainer(redirectUrlField, redirectDisplayTextField, redirectDescriptionField);
        
    }

}
