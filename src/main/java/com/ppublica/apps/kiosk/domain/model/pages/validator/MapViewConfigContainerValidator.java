package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.MapViewConfigContainer;

public class MapViewConfigContainerValidator {
    private String enableFsCustomMapsFieldType;
    private String clearResultsFieldType;
    private String levelSelectFieldType;
    private String mapNotAvailableFieldType;

    public MapViewConfigContainerValidator(String enableFsCustomMapsFieldType, String clearResultsFieldType, String levelSelectFieldType, String mapNotAvailableFieldType) {
        this.enableFsCustomMapsFieldType = enableFsCustomMapsFieldType;
        this.clearResultsFieldType = clearResultsFieldType;
        this.levelSelectFieldType = levelSelectFieldType;
        this.mapNotAvailableFieldType = mapNotAvailableFieldType;
    }

    public boolean isValid(MapViewConfigContainer container) {
        if (!KioskPageFieldTypeValidator.isValid(container.enableFsCustomMapsField(), enableFsCustomMapsFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getClearResultsField(), clearResultsFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getLevelSelectField(), levelSelectFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getMapNotAvailableField(), mapNotAvailableFieldType)) {
            return false;
        }

        return true;
    }
}
