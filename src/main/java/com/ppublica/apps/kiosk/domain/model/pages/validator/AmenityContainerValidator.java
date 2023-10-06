package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.AmenityContainer;

public class AmenityContainerValidator {
    private String widgetLabelFieldType;
    private String headingLabelFieldType;

    public AmenityContainerValidator(String widgetLabelFieldType, String headingLabelFieldType) {
        this.widgetLabelFieldType = widgetLabelFieldType;
        this.headingLabelFieldType = headingLabelFieldType;
    }

    public boolean isValid(AmenityContainer container) {
        if (!KioskPageFieldTypeValidator.isValid(container.getWidgetLabelField(), widgetLabelFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getHeadingLabelField(), headingLabelFieldType)) {
            return false;
        }

        return true;
    }



}
