package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.KioskPageField;

public class KioskPageFieldTypeValidator {

    public static boolean isValid(KioskPageField<?> kioskPageField, String pageTitleFieldType) {
        if(kioskPageField.getFieldType().equals(pageTitleFieldType)) {
            return true;
        }

        return false;

    }
}
