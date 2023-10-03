package com.ppublica.apps.kiosk.domain.model.pages.selector;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class GeneralEventStringsFieldSelector {
    private final String eventThemeLabelType;
    private final String durationLabelType;
    private final String yearsShowingLabelType;
    private final String dateLabelType;
    private final String eventLangLabelType;
    private final String noEventsFoundType;

    public GeneralEventStringsFieldSelector(String eventThemeLabelType, String durationLabelType, String yearsShowingLabelType, 
                                    String dateLabelType, String eventLangLabelType, String noEventsFoundType) {
        
        this.eventThemeLabelType = eventThemeLabelType;
        this.durationLabelType = durationLabelType;
        this.yearsShowingLabelType = yearsShowingLabelType;
        this.dateLabelType = dateLabelType;
        this.eventLangLabelType = eventLangLabelType;
        this.noEventsFoundType = noEventsFoundType;

    }
    

    public RegularTextLongDescriptionField eventThemeLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(eventThemeLabelType);

    }

    public RegularTextLongDescriptionField durationLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(durationLabelType);

    }

    public RegularTextLongDescriptionField yearsShowingLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(yearsShowingLabelType);

    }

    public RegularTextLongDescriptionField dateLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(dateLabelType);

    }

    public RegularTextLongDescriptionField eventLangLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(eventLangLabelType);

    }

    public RegularTextLongDescriptionField noEventsFoundField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(noEventsFoundType);

    }

}
