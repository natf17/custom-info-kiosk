package com.ppublica.apps.kiosk.domain.model.pages.selector;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class TapWidgetContainerFieldSelector {
    private String instructionsFieldType;
    private String brLabelFieldType;
    private String waterLabelFieldType;
    private String firstAidLabelFieldType;
    private String donationsLabelFieldType;
    private String noResultsFoundFieldType;

    public TapWidgetContainerFieldSelector(String instructionsFieldType, String brLabelFieldType, String waterLabelFieldType, String firstAidLabelFieldType,
                                String donationsLabelFieldType, String noResultsFoundFieldType) {
        
        this.instructionsFieldType = instructionsFieldType;
        this.brLabelFieldType = brLabelFieldType;
        this.waterLabelFieldType = waterLabelFieldType;
        this.firstAidLabelFieldType = firstAidLabelFieldType;
        this.donationsLabelFieldType = donationsLabelFieldType;
        this.noResultsFoundFieldType = noResultsFoundFieldType;

    }

    public RegularTextLongDescriptionField instructionsField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(instructionsFieldType);
    }

    public RegularTextLongDescriptionField brLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(brLabelFieldType);
    }

    public RegularTextLongDescriptionField waterLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(waterLabelFieldType);
    }

    public RegularTextLongDescriptionField firstAidLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(firstAidLabelFieldType);
    }

    public RegularTextLongDescriptionField donationsLabelField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(donationsLabelFieldType);
    }

    public RegularTextLongDescriptionField noResultsFoundField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectRegTextLongDescrField(noResultsFoundFieldType);
    }
}
