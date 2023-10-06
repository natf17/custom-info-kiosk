package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.selector.AmenityContainerSelector;

public class AmenityContainer {
    private KioskPageField<String> widgetLabel;
    private KioskPageField<String> headingLabel;

    public AmenityContainer(KioskPageField<String> widgetLabel, KioskPageField<String> headingLabel) {
        this.widgetLabel = widgetLabel;
        this.headingLabel = headingLabel;
    }

    public KioskPageField<String> getWidgetLabelField() {
        return this.widgetLabel;
    }
    
    public KioskPageField<String> getHeadingLabelField() {
        return this.headingLabel;
    }

    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {

        FieldContainer amenityContainer = new FieldContainer.Builder()
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(widgetLabel))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(headingLabel))
            .fieldContainerName(containerName)
            .build();

        return amenityContainer;
    }

    /*
     * Expects a fieldContainer with two RegularTextLongDescriptionFields
     */
    public static AmenityContainer fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter, AmenityContainerSelector fieldSelector) {
        

        RegularTextLongDescriptionField cmsWidgetLabelField = fieldSelector.widgetLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsHeadingLabelField = fieldSelector.headingLabelField(fieldContainer);

        KioskPageField<String> widgetLabelField = converter.toStringField(cmsWidgetLabelField);
        KioskPageField<String> headingLabelField = converter.toStringField(cmsHeadingLabelField);
  
        return new AmenityContainer(widgetLabelField, headingLabelField);
    }

    
}
