package com.ppublica.apps.kiosk.domain.model.pages.selector;

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
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(redirectDisplayTextFieldType);

    }
    
    public RegularTextLongDescriptionField selectRedirectDescriptionField(FieldContainer fieldContainer) {

        FieldSelector fieldSelector = new FieldSelector(fieldContainer);
        
        return fieldSelector.selectRegTextLongDescrField(redirectDescriptionFieldType);

    }
    
}
