package com.ppublica.apps.kiosk.domain.model.pages;

public class EditedAmenityContainer {
    private EditedPageFieldPayload<String> widgetLabel;
    private EditedPageFieldPayload<String> headingLabel;

    public EditedAmenityContainer(EditedPageFieldPayload<String> widgetLabel, EditedPageFieldPayload<String> headingLabel) {
        this.widgetLabel = widgetLabel;
        this.headingLabel = headingLabel;
    }

    public EditedPageFieldPayload<String> getWidgetLabelField() {
        return this.widgetLabel;
    }
    
    public EditedPageFieldPayload<String> getHeadingLabelField() {
        return this.headingLabel;
    }
    
    public static EditedAmenityContainer empty(String widgetLabelFieldName, String headingLabelFieldName) {
        EditedPageFieldPayload<String> widgetLabel = new EditedPageFieldPayload<String>(widgetLabelFieldName, null);
        EditedPageFieldPayload<String> headingLabel = new EditedPageFieldPayload<String>(headingLabelFieldName, null);

        return new EditedAmenityContainer(widgetLabel, headingLabel);

    }


    public AmenityContainer toAmenityContainer(String widgetLabelFieldType, String headingLabelFieldType) {
                                                    
        KioskPageField<String> widgetLabelField = widgetLabel.toKioskPageField(widgetLabelFieldType, true);
        KioskPageField<String> headingLabelField = headingLabel.toKioskPageField(headingLabelFieldType, true);

        return new AmenityContainer(widgetLabelField, headingLabelField);
    }
}
