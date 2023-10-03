package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.MenuItem;

public class MenuItemValidator {

    private final String idFieldType;
    private final String isVisibleFieldType;
    private final String labelFieldType;
    private final String urlFieldType;
    private final String imageFieldType;

    public MenuItemValidator(String idFieldType, String isVisibleFieldType, String labelFieldType, String urlFieldType, String imageFieldType) {
        this.idFieldType = idFieldType;
        this.isVisibleFieldType = isVisibleFieldType;
        this.labelFieldType = labelFieldType;
        this.urlFieldType = urlFieldType;
        this.imageFieldType = imageFieldType;

    }

    public boolean isValid(MenuItem menuItem) {
        if (!KioskPageFieldTypeValidator.isValid(menuItem.getId(), idFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(menuItem.isVisible(), isVisibleFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(menuItem.getLabel(), labelFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(menuItem.getUrl(), urlFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(menuItem.getImage(), imageFieldType)) {
            return false;
        }

        return true;
    }


    
}
