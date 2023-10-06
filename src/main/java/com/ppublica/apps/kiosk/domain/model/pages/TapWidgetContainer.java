package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.selector.TapWidgetContainerFieldSelector;

public class TapWidgetContainer {
    private KioskPageField<String> instructions;
    private KioskPageField<String> brLabel;
    private KioskPageField<String> waterLabel;
    private KioskPageField<String> firstAidLabel;
    private KioskPageField<String> donationsLabel;
    private KioskPageField<String> noResultsFound;

    public TapWidgetContainer(KioskPageField<String> instructions, KioskPageField<String> brLabel, KioskPageField<String> waterLabel, KioskPageField<String> firstAidLabel,
                                KioskPageField<String> donationsLabel, KioskPageField<String> noResultsFound) {
        
        this.instructions = instructions;
        this.brLabel = brLabel;
        this.waterLabel = waterLabel;
        this.firstAidLabel = firstAidLabel;
        this.donationsLabel = donationsLabel;
        this.noResultsFound = noResultsFound;

    }

    public KioskPageField<String> getInstructionsField() {
        return this.instructions;
    }

    public KioskPageField<String> getBrLabelField() {
        return this.brLabel;
    }

    public KioskPageField<String> getWaterLabelField() {
        return this.waterLabel;
    }

    public KioskPageField<String> getFirstAidLabelField() {
        return this.firstAidLabel;
    }

    public KioskPageField<String> getDonationsLabelField() {
        return this.donationsLabel;
    }

    public KioskPageField<String> getNoResultsFoundField() {
        return this.noResultsFound;
    }

    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {

        FieldContainer tapWidgetContainer = new FieldContainer.Builder()
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(instructions))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(brLabel))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(waterLabel))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(firstAidLabel))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(donationsLabel))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(noResultsFound))
            .fieldContainerName(containerName)
            .build();

        return tapWidgetContainer;
    }

    /*
     * Expects a fieldContainer with six RegularTextLongDescriptionFields
     */
    public static TapWidgetContainer fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter, TapWidgetContainerFieldSelector fieldSelector) {
        

        RegularTextLongDescriptionField cmsInstructionsField = fieldSelector.instructionsField(fieldContainer);
        RegularTextLongDescriptionField cmsBrLabelField = fieldSelector.brLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsWaterLabelField = fieldSelector.waterLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsFirstAidLabelField = fieldSelector.firstAidLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsDonationsLabelField = fieldSelector.donationsLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsNoResultsFoundField = fieldSelector.noResultsFoundField(fieldContainer);

        KioskPageField<String> instructionsField = converter.toStringField(cmsInstructionsField);
        KioskPageField<String> brLabelField = converter.toStringField(cmsBrLabelField);
        KioskPageField<String> waterLabelField = converter.toStringField(cmsWaterLabelField);
        KioskPageField<String> firstAidLabelField = converter.toStringField(cmsFirstAidLabelField);
        KioskPageField<String> donationsLabelField = converter.toStringField(cmsDonationsLabelField);
        KioskPageField<String> noResultsFoundField = converter.toStringField(cmsNoResultsFoundField);
  
        return new TapWidgetContainer(instructionsField, brLabelField, waterLabelField, firstAidLabelField, donationsLabelField, noResultsFoundField);
    }

}
