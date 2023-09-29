package com.ppublica.apps.kiosk.domain.model.util;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class FieldSelector {

    private final FieldContainer fieldContainer;

    public FieldSelector(FieldContainer fieldContainer) {
        this.fieldContainer = fieldContainer;
    }

    public RegularTextLongDescriptionField selectRegTextLongDescrField(String fieldType) {
        List<RegularTextLongDescriptionField> fields = fieldContainer.getRegularTextLongDescriptionFields();
        
        for(RegularTextLongDescriptionField field : fields) {
            if(field.getFieldType().equals(fieldType)) {
                return field;
            }
        }

        throw new RuntimeException("No RegularTextLongDescriptionField found matching " + fieldType);

    }

    
    
}
