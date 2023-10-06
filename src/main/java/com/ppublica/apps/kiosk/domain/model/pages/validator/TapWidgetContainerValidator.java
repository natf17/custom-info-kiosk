package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.TapWidgetContainer;

public class TapWidgetContainerValidator {

    private String instructionsFieldType;
    private String brLabelFieldType;
    private String waterLabelFieldType;
    private String firstAidLabelFieldType;
    private String donationsLabelFieldType;
    private String noResultsFoundFieldType;
    
    public TapWidgetContainerValidator(String instructionsFieldType, String brLabelFieldType, String waterLabelFieldType, String firstAidLabelFieldType,
                                String donationsLabelFieldType, String noResultsFoundFieldType) {

        this.instructionsFieldType = instructionsFieldType;
        this.brLabelFieldType = brLabelFieldType;
        this.waterLabelFieldType = waterLabelFieldType;
        this.firstAidLabelFieldType = firstAidLabelFieldType;
        this.donationsLabelFieldType = donationsLabelFieldType;
        this.noResultsFoundFieldType = noResultsFoundFieldType;

    }

    public boolean isValid(TapWidgetContainer container) {
        if (!KioskPageFieldTypeValidator.isValid(container.getInstructionsField(), instructionsFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getBrLabelField(), brLabelFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getWaterLabelField(), waterLabelFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getFirstAidLabelField(), firstAidLabelFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getDonationsLabelField(), donationsLabelFieldType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getNoResultsFoundField(), noResultsFoundFieldType)) {
            return false;
        }

        return true;
    }
}
