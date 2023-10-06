package com.ppublica.apps.kiosk.domain.model.pages;

public class EditedTapWidgetContainer {

    private EditedPageFieldPayload<String> instructions;
    private EditedPageFieldPayload<String> brLabel;
    private EditedPageFieldPayload<String> waterLabel;
    private EditedPageFieldPayload<String> firstAidLabel;
    private EditedPageFieldPayload<String> donationsLabel;
    private EditedPageFieldPayload<String> noResultsFound;

    public EditedTapWidgetContainer(EditedPageFieldPayload<String> instructions, EditedPageFieldPayload<String> brLabel, EditedPageFieldPayload<String> waterLabel, EditedPageFieldPayload<String> firstAidLabel,
                                EditedPageFieldPayload<String> donationsLabel, EditedPageFieldPayload<String> noResultsFound) {
        
        this.instructions = instructions;
        this.brLabel = brLabel;
        this.waterLabel = waterLabel;
        this.firstAidLabel = firstAidLabel;
        this.donationsLabel = donationsLabel;
        this.noResultsFound = noResultsFound;

    }

    public EditedPageFieldPayload<String> getInstructionsField() {
        return this.instructions;
    }

    public EditedPageFieldPayload<String> getBrLabelField() {
        return this.brLabel;
    }

    public EditedPageFieldPayload<String> getWaterLabelField() {
        return this.waterLabel;
    }

    public EditedPageFieldPayload<String> getFirstAidLabelField() {
        return this.firstAidLabel;
    }

    public EditedPageFieldPayload<String> getDonationsLabelField() {
        return this.donationsLabel;
    }

    public EditedPageFieldPayload<String> getNoResultsFoundField() {
        return this.noResultsFound;
    }


    public static EditedTapWidgetContainer empty(String instructionsFieldName, String brLabelFieldName, String waterLabelFieldName, 
                            String firstAidLabelFieldName, String donationsLabelFieldName, String noResultsFoundFieldName) {
        EditedPageFieldPayload<String> instructions = new EditedPageFieldPayload<String>(instructionsFieldName, null);
        EditedPageFieldPayload<String> brLabel = new EditedPageFieldPayload<String>(brLabelFieldName, null);
        EditedPageFieldPayload<String> waterLabel = new EditedPageFieldPayload<String>(waterLabelFieldName, null);
        EditedPageFieldPayload<String> firstAidLabel = new EditedPageFieldPayload<String>(firstAidLabelFieldName, null);
        EditedPageFieldPayload<String> donationsLabel = new EditedPageFieldPayload<String>(donationsLabelFieldName, null);
        EditedPageFieldPayload<String> noResultsFound = new EditedPageFieldPayload<String>(noResultsFoundFieldName, null);

        return new EditedTapWidgetContainer(instructions, brLabel, waterLabel, firstAidLabel, donationsLabel, noResultsFound);

    }


    public TapWidgetContainer toTapWidgetContainer(String instructionsFieldType, String brLabelFieldType, String waterLabelFieldType, String firstAidLabelFieldType,
                                                    String donationsLabelFieldType, String noResultsFoundFieldType) {
                                                    
        KioskPageField<String> instructionsField = instructions.toKioskPageField(instructionsFieldType, true);
        KioskPageField<String> brLabelField = brLabel.toKioskPageField(brLabelFieldType, true);
        KioskPageField<String> waterLabelField = waterLabel.toKioskPageField(waterLabelFieldType, true);
        KioskPageField<String> firstAidLabelField = firstAidLabel.toKioskPageField(firstAidLabelFieldType, true);
        KioskPageField<String> donationsLabelField = donationsLabel.toKioskPageField(donationsLabelFieldType, true);
        KioskPageField<String> noResultsFoundField = noResultsFound.toKioskPageField(noResultsFoundFieldType, true);

        return new TapWidgetContainer(instructionsField, brLabelField, waterLabelField, firstAidLabelField, donationsLabelField, noResultsFoundField);
    }
    
}
