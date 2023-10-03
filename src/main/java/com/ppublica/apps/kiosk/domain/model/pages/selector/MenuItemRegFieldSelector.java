package com.ppublica.apps.kiosk.domain.model.pages.selector;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class MenuItemRegFieldSelector {
    private final String idFieldType;
    private final String labelFieldType;

    public MenuItemRegFieldSelector(String idFieldType, String labelFieldType) {
        this.idFieldType = idFieldType;
        this.labelFieldType = labelFieldType;
    }

    public RegularTextLongDescriptionField selectIdField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(idFieldType);

    }
    
    public RegularTextLongDescriptionField selectLabelField(FieldContainer fieldContainer) {

        FieldSelector fieldSelector = new FieldSelector(fieldContainer);
        
        return fieldSelector.selectRegTextLongDescrField(labelFieldType);

    }
}
