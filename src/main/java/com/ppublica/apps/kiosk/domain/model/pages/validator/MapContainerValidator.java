package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.MapContainer;

public class MapContainerValidator {

    private String defaultMapFieldType;
    private String bathroomsMapFieldType;
    private String waterFountainsMapFieldType;
    private String firstAidMapFieldType;
    private String donationsMapFieldType;

    public MapContainerValidator(String defaultMapFieldType, String bathroomsMapFieldType, String waterFountainsMapFieldType,
                        String firstAidMapFieldType, String donationsMapFieldType) {
        this.defaultMapFieldType = defaultMapFieldType;
        this.bathroomsMapFieldType = bathroomsMapFieldType;
        this.waterFountainsMapFieldType = waterFountainsMapFieldType;
        this.firstAidMapFieldType = firstAidMapFieldType;
        this.donationsMapFieldType = donationsMapFieldType;

    }
    public boolean isValid(MapContainer container) {
        if (!KioskPageFieldTypeValidator.isValid(container.getDefaultMapField(), defaultMapFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getBathroomsMapField(), bathroomsMapFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getWaterFountainsMapField(), waterFountainsMapFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getFirstAidMapField(), firstAidMapFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getDonationsMapField(), donationsMapFieldType)) {
            return false;
        }

        return true;
    }
}
