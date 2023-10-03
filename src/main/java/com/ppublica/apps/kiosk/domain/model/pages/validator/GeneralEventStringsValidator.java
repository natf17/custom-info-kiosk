package com.ppublica.apps.kiosk.domain.model.pages.validator;

import com.ppublica.apps.kiosk.domain.model.pages.GeneralEventStringsContainer;

public class GeneralEventStringsValidator {
    private final String eventThemeLabelType;
    private final String durationLabelType;
    private final String yearsShowingLabelType;
    private final String dateLabelType;
    private final String eventLangLabelType;
    private final String noEventsFoundType;


    public GeneralEventStringsValidator(String eventThemeLabelType, String durationLabelType, String yearsShowingLabelType, 
                                String dateLabelType, String eventLangLabelType, String noEventsFoundType) {
        this.eventThemeLabelType = eventThemeLabelType;
        this.durationLabelType = durationLabelType;
        this.yearsShowingLabelType = yearsShowingLabelType;
        this.dateLabelType = dateLabelType;
        this.eventLangLabelType = eventLangLabelType;
        this.noEventsFoundType = noEventsFoundType;
    }

    public boolean isValid(GeneralEventStringsContainer container) {
        if (!KioskPageFieldTypeValidator.isValid(container.getEventThemeLabelField(), eventThemeLabelType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getDurationLabelField(), durationLabelType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getYearsShowingLabelField(), yearsShowingLabelType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getDateLabel(), dateLabelType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getEventLangLabelField(), eventLangLabelType)) {
            return false;
        }

        if (!KioskPageFieldTypeValidator.isValid(container.getNoEventsFoundField(), noEventsFoundType)) {
            return false;
        }

        return true;
    }

}
