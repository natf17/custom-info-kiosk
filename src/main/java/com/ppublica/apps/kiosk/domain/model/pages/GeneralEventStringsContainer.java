package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.selector.GeneralEventStringsFieldSelector;

public class GeneralEventStringsContainer {
    private KioskPageField<String> eventThemeLabel;
    private KioskPageField<String> durationLabel;
    private KioskPageField<String> yearsShowingLabel;
    private KioskPageField<String> dateLabel;
    private KioskPageField<String> eventLangLabel;
    private KioskPageField<String> noEventsFound;

    public KioskPageField<String> getEventThemeLabelField() {
        return this.eventThemeLabel;
    }

    public KioskPageField<String> getDurationLabelField() {
        return this.durationLabel;
    }

    public KioskPageField<String> getYearsShowingLabelField() {
        return this.yearsShowingLabel;
    }

    public KioskPageField<String> getDateLabel() {
        return this.dateLabel;
    }

    public KioskPageField<String> getEventLangLabelField() {
        return this.eventLangLabel;
    }

    public KioskPageField<String> getNoEventsFoundField() {
        return this.noEventsFound;
    }

    

    public GeneralEventStringsContainer(KioskPageField<String> eventThemeLabel, KioskPageField<String> durationLabel, KioskPageField<String> yearsShowingLabel,
                                        KioskPageField<String> dateLabel, KioskPageField<String> eventLangLabel, KioskPageField<String> noEventsFound) {
        
        this.eventThemeLabel = eventThemeLabel;
        this.durationLabel = durationLabel;
        this.yearsShowingLabel = yearsShowingLabel;
        this.dateLabel = dateLabel;
        this.eventLangLabel = eventLangLabel;
        this.noEventsFound = noEventsFound;

    }
    
    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {
        FieldContainer genEventStrings = new FieldContainer.Builder()
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(eventThemeLabel))
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(durationLabel))
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(yearsShowingLabel))
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(dateLabel))
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(eventLangLabel))
                            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(noEventsFound))
                            .fieldContainerName(containerName)
                            .build();

        return genEventStrings;
    }

    /*
     * Expects a fieldContainer with six RegularTextLongDescriptionFields
     */
    public static GeneralEventStringsContainer fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter, GeneralEventStringsFieldSelector fieldSelector) {
        

        RegularTextLongDescriptionField cmsEventThemeLabelField = fieldSelector.eventThemeLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsDurationLabelField = fieldSelector.durationLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsYearsShowingLabelField = fieldSelector.yearsShowingLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsDateLabelField = fieldSelector.dateLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsEventLangLabelField = fieldSelector.eventLangLabelField(fieldContainer);
        RegularTextLongDescriptionField cmsNoEventsFoundField = fieldSelector.noEventsFoundField(fieldContainer);

        KioskPageField<String> eventThemeLabelField = converter.toStringField(cmsEventThemeLabelField);
        KioskPageField<String> durationLabelField = converter.toStringField(cmsDurationLabelField);
        KioskPageField<String> yearsShowingLabelField = converter.toStringField(cmsYearsShowingLabelField);
        KioskPageField<String> dateLabelField = converter.toStringField(cmsDateLabelField);
        KioskPageField<String> eventLangLabelField = converter.toStringField(cmsEventLangLabelField);
        KioskPageField<String> noEventsFoundField = converter.toStringField(cmsNoEventsFoundField);
  
        return new GeneralEventStringsContainer(eventThemeLabelField, durationLabelField, yearsShowingLabelField, dateLabelField, eventLangLabelField, noEventsFoundField);
    }

}
