package com.ppublica.apps.kiosk.domain.model.pages.selector;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class MapViewConfigContainerFieldSelector {
    private String enableFsCustomMapsFieldType;
    private String clearResultsFieldType;
    private String levelSelectFieldType;
    private String mapNotAvailableFieldType;

    public MapViewConfigContainerFieldSelector(String enableFsCustomMapsFieldType, String clearResultsFieldType, String levelSelectFieldType, String mapNotAvailableFieldType) {
        this.enableFsCustomMapsFieldType = enableFsCustomMapsFieldType;
        this.clearResultsFieldType = clearResultsFieldType;
        this.levelSelectFieldType = levelSelectFieldType;
        this.mapNotAvailableFieldType = mapNotAvailableFieldType;
    }

    public ButtonField enableFsCustomMapsField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectButtonField(enableFsCustomMapsFieldType);
    }

    public RegularTextLongDescriptionField clearResultsField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(clearResultsFieldType);
    }

    public RegularTextLongDescriptionField levelSelectField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(levelSelectFieldType);
    }

    public RegularTextLongDescriptionField mapNotAvailableField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(mapNotAvailableFieldType);
    }
    
}
