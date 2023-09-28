package com.ppublica.apps.kiosk.domain.model.pages.selector;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

/*
 * Logic to select two RegularTextLongDescriptionFields from a container based on field name type.
 */
public class RedirectUrlContainerRegFieldSelector {
    private final String redirectDisplayTextFieldType;
    private final String redirectDescriptionFieldType;

    public RedirectUrlContainerRegFieldSelector(String redirectDisplayTextFieldType, String redirectDescriptionFieldType) {
        this.redirectDisplayTextFieldType = redirectDisplayTextFieldType;
        this.redirectDescriptionFieldType = redirectDescriptionFieldType;
    }

    public RegularTextLongDescriptionField selectRedirectDisplayTextField(FieldContainer fieldContainer) {
        List<RegularTextLongDescriptionField> fields = fieldContainer.getRegularTextLongDescriptionFields();
        
        for(RegularTextLongDescriptionField field : fields) {
            if(field.getFieldType().equals(redirectDisplayTextFieldType)) {
                return field;
            }
        }

        throw new RuntimeException("No RegularTextLongDescriptionField found matching RedirectDisplayTextField");

    }
    
    public RegularTextLongDescriptionField selectRedirectDescriptionField(FieldContainer fieldContainer) {

        List<RegularTextLongDescriptionField> fields = fieldContainer.getRegularTextLongDescriptionFields();
        
        for(RegularTextLongDescriptionField field : fields) {
            if(field.getFieldType().equals(redirectDescriptionFieldType)) {
                return field;
            }
        }

        throw new RuntimeException("No RegularTextLongDescriptionField found matching RedirectDescriptionField");
    }
    
}
