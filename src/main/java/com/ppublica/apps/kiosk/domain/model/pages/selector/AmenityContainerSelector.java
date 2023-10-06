package com.ppublica.apps.kiosk.domain.model.pages.selector;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class AmenityContainerSelector {
    private String widgetLabelFieldType;
    private String headingLabelFieldType;

    public AmenityContainerSelector(String widgetLabelFieldType, String headingLabelFieldType) {
        this.widgetLabelFieldType = widgetLabelFieldType;
        this.headingLabelFieldType = headingLabelFieldType;
    }

    public RegularTextLongDescriptionField widgetLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(widgetLabelFieldType);
    }
    
    public RegularTextLongDescriptionField headingLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(headingLabelFieldType);
    }

}
