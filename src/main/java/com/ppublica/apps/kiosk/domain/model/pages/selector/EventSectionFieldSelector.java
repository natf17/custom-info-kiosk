package com.ppublica.apps.kiosk.domain.model.pages.selector;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class EventSectionFieldSelector {

    private final String titleType;
    private final String btnTextType;

    public EventSectionFieldSelector(String titleType, String btnTextType) {
        this.titleType = titleType;
        this.btnTextType = btnTextType;
    }

    public RegularTextLongDescriptionField titleField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(titleType);
    }

    public RegularTextLongDescriptionField btnTextField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(btnTextType);

    }
    
}
