package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.EventSectionContainer;

public class EventSectionContainerValidator {
    private final String titleType;
    private final String btnTextType;

    public EventSectionContainerValidator(String titleType, String btnTextType) {
        this.titleType = titleType;
        this.btnTextType = btnTextType;
    }

    public boolean isValid(EventSectionContainer container) {
        if (!KioskPageFieldTypeValidator.isValid(container.getTitleField(), titleType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getBtnTextField(), btnTextType)) {
            return false;
        }

        return true;
    }
}
