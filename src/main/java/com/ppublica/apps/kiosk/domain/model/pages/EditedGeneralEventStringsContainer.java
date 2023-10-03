package com.ppublica.apps.kiosk.domain.model.pages;

public class EditedGeneralEventStringsContainer {
    private EditedPageFieldPayload<String> eventThemeLabel;
    private EditedPageFieldPayload<String> durationLabel;
    private EditedPageFieldPayload<String> yearsShowingLabel;
    private EditedPageFieldPayload<String> dateLabel;
    private EditedPageFieldPayload<String> eventLangLabel;
    private EditedPageFieldPayload<String> noEventsFound;

    public EditedGeneralEventStringsContainer(EditedPageFieldPayload<String> eventThemeLabel, EditedPageFieldPayload<String> durationLabel, EditedPageFieldPayload<String> yearsShowingLabel, 
                                            EditedPageFieldPayload<String> dateLabel, EditedPageFieldPayload<String> eventLangLabel, EditedPageFieldPayload<String> noEventsFound) {
        this.eventThemeLabel = eventThemeLabel;
        this.durationLabel = durationLabel;
        this.yearsShowingLabel = yearsShowingLabel;
        this.dateLabel = dateLabel;
        this.eventLangLabel = eventLangLabel;
        this.noEventsFound = noEventsFound;
    }

    public EditedPageFieldPayload<String> getEventThemeLabelField() {
        return this.eventThemeLabel;
    }

    public EditedPageFieldPayload<String> getDurationLabelField() {
        return this.durationLabel;
    }

    public EditedPageFieldPayload<String> getYearsShowingLabelField() {
        return this.yearsShowingLabel;
    }

    public EditedPageFieldPayload<String> getDateLabel() {
        return this.dateLabel;
    }

    public EditedPageFieldPayload<String> getEventLangLabelField() {
        return this.eventLangLabel;
    }

    public EditedPageFieldPayload<String> getNoEventsFoundField() {
        return this.noEventsFound;
    }

    public static EditedGeneralEventStringsContainer empty(String eventThemeLabelFieldName, String durationLabelFieldName, String yearsShowingLabelFieldName, 
                                                        String dateLabelFieldName, String eventLangLabelFieldName, String noEventsFoundFieldName) {
        
        EditedPageFieldPayload<String> eventThemeLabel = new EditedPageFieldPayload<String>(eventThemeLabelFieldName, null);
        EditedPageFieldPayload<String> durationLabel = new EditedPageFieldPayload<String>(durationLabelFieldName, null);
        EditedPageFieldPayload<String> yearsShowingLabel = new EditedPageFieldPayload<String>(yearsShowingLabelFieldName, null);
        EditedPageFieldPayload<String> dateLabel = new EditedPageFieldPayload<String>(dateLabelFieldName, null);
        EditedPageFieldPayload<String> eventLangLabel = new EditedPageFieldPayload<String>(eventLangLabelFieldName, null);
        EditedPageFieldPayload<String> noEventsFound = new EditedPageFieldPayload<String>(noEventsFoundFieldName, null);

        return new EditedGeneralEventStringsContainer(eventThemeLabel, durationLabel, yearsShowingLabel, dateLabel, eventLangLabel, noEventsFound);
    }

    public GeneralEventStringsContainer toGeneralEventStringsContainer(String eventThemeLabelType, String durationLabelType, String yearsShowingLabelType, 
                                                                    String dateLabelType, String eventLangLabelType, String noEventsFoundType) {
                                                    
        KioskPageField<String> eventThemeLabelField = eventThemeLabel.toKioskPageField(eventThemeLabelType, true);
        KioskPageField<String> durationLabelField = durationLabel.toKioskPageField(durationLabelType, true);
        KioskPageField<String> yearsShowingLabelField = yearsShowingLabel.toKioskPageField(yearsShowingLabelType, true);
        KioskPageField<String> dateLabelField = dateLabel.toKioskPageField(dateLabelType, true);
        KioskPageField<String> eventLangLabelField = eventLangLabel.toKioskPageField(eventLangLabelType, true);
        KioskPageField<String> noEventsFoundField = noEventsFound.toKioskPageField(noEventsFoundType, true);

        return new GeneralEventStringsContainer(eventThemeLabelField, durationLabelField, yearsShowingLabelField, dateLabelField, eventLangLabelField, noEventsFoundField);
    }
}
