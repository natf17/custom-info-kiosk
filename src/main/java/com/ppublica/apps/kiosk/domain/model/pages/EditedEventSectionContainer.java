package com.ppublica.apps.kiosk.domain.model.pages;

public class EditedEventSectionContainer {
    private EditedPageFieldPayload<String> title;
    private EditedPageFieldPayload<String> btnText;

    public EditedEventSectionContainer(EditedPageFieldPayload<String> title, EditedPageFieldPayload<String> btnText) {
        this.title = title;
        this.btnText = btnText;
    }

    public EditedPageFieldPayload<String> getTitleField() {
        return this.title;
    }

    public EditedPageFieldPayload<String> getBtnTextField() {
        return this.btnText;
    }

    public static EditedEventSectionContainer empty(String titleFieldName, String btnTextFieldName) {
        EditedPageFieldPayload<String> title = new EditedPageFieldPayload<String>(titleFieldName, null);
        EditedPageFieldPayload<String> btnText = new EditedPageFieldPayload<String>(btnTextFieldName, null);

        return new EditedEventSectionContainer(title, btnText);

    }

    public EventSectionContainer toEventSectionContainer(String titleFieldType, String btnTextFieldType) {
        KioskPageField<String> titleField = title.toKioskPageField(titleFieldType, true);
        KioskPageField<String> btnTextField = btnText.toKioskPageField(btnTextFieldType, true);

        return new EventSectionContainer(titleField, btnTextField);
    }
}
