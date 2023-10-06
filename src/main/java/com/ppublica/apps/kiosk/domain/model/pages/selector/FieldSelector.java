package com.ppublica.apps.kiosk.domain.model.pages.selector;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
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

    public ButtonField selectButtonField(String fieldType) {
        List<ButtonField> fields = fieldContainer.getButtonFields();
        
        for(ButtonField field : fields) {
            if(field.getFieldType().equals(fieldType)) {
                return field;
            }
        }

        throw new RuntimeException("No RegularTextLongDescriptionField found matching " + fieldType);

    }

    public ImageField selectImageField(String fieldType) {
        List<ImageField> fields = fieldContainer.getImageFields();
        
        for(ImageField field : fields) {
            if(field.getFieldType().equals(fieldType)) {
                return field;
            }
        }

        throw new RuntimeException("No RegularTextLongDescriptionField found matching " + fieldType);

    }

    public FieldContainer selectChildContainer(String fieldContainerName) {
        List<FieldContainer> childContainers = fieldContainer.getChildContainers();

        for(FieldContainer fc : childContainers) {
            if(fc.getFieldContainerName().equals(fieldContainerName)) {
                return fc;
            }
        }

        throw new RuntimeException("No child FieldContainer found matching " + fieldContainerName);

    }

    
    
}
