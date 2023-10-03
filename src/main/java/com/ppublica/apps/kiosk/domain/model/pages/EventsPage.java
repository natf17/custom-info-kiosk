package com.ppublica.apps.kiosk.domain.model.pages;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.selector.EventSectionFieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.selector.FieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.selector.GeneralEventStringsFieldSelector;
import com.ppublica.apps.kiosk.domain.model.pages.validator.EventSectionContainerValidator;
import com.ppublica.apps.kiosk.domain.model.pages.validator.GeneralEventStringsValidator;
import com.ppublica.apps.kiosk.domain.model.pages.validator.KioskPageFieldTypeValidator;

/*
 * Represents an Events Page with the following:
 *  - regular description
 *  - an eventLangPickerLabel
 *  - strings for buttons/labels (general string container)
 *  - text for different event types (event section container)
 */
public class EventsPage extends KioskPage {
    public static final String PAGE_DESCR_FIELD_TYPE = "PageDescription";
    protected static final String PAGE_DESCR_FIELD_NAME_DEFAULT = "PageDescription";
    public static final String EVENTLANGPICKER_LABEL_FIELD_TYPE = "EventLangPickerLabel";
    protected static final String EVENTLANGPICKER_LABEL_FIELD_NAME_DEFAULT = "EventLangPickerLabel";

    // for page rep
    protected static final String GEN_STRINGS_CONTAINER_NAME = "genStringsFC";
    protected static final String SECTION_REG_CONTAINER_NAME = "regFC";
    protected static final String SECTION_CO_CONTAINER_NAME = "coFC";
    protected static final String SECTION_BR_CONTAINER_NAME = "brFC";
    protected static final String SECTION_OTHER_CONTAINER_NAME = "otherFC";
    protected static final String MAIN_CONTAINER_NAME = "mainFC";

    protected static final String GEN_STRINGS_EVENTTHEMELABEL_FIELD_TYPE = "EventThemeLabel";
    protected static final String GEN_STRINGS_EVENTTHEMELABEL_FIELD_NAME_DEFAULT = "EventThemeLabel";
    protected static final String GEN_STRINGS_DURATIONLABEL_FIELD_TYPE = "DurationLabel";
    protected static final String GEN_STRINGS_DURATIONLABEL_FIELD_NAME_DEFAULT = "DurationLabel";
    protected static final String GEN_STRINGS_YEARSSHOWINGLABEL_FIELD_TYPE = "YearsShowingLabel";
    protected static final String GEN_STRINGS_YEARSSHOWINGLABEL_FIELD_NAME_DEFAULT = "YearsShowingLabel";
    protected static final String GEN_STRINGS_DATELABEL_FIELD_TYPE = "DateLabel";
    protected static final String GEN_STRINGS_DATELABEL_FIELD_NAME_DEFAULT = "DateLabel";
    protected static final String GEN_STRINGS_EVENTLANGLABEL_FIELD_TYPE = "EventLangLabel";
    protected static final String GEN_STRINGS_EVENTLANGLABEL_FIELD_NAME_DEFAULT = "EventLangLabel";
    protected static final String GEN_STRINGS_NOEVENTSFOUND_FIELD_TYPE = "NoEventsFound";
    protected static final String GEN_STRINGS_NOEVENTSFOUND_FIELD_NAME_DEFAULT = "NoEventsFound";

    protected static final String SECTION_TITLE_FIELD_TYPE = "Title";
    protected static final String SECTION_TITLE_FIELD_NAME_DEFAULT = "Title";
    protected static final String SECTION_BUTTON_TEXT_FIELD_TYPE = "Button_text";
    protected static final String SECTION_BUTTON_TEXT_FIELD_NAME_DEFAULT = "Button_text";

    private static final KioskPageType KIOSK_PAGE_TYPE = KioskPageType.EVENTS;
    public static final String PAGE_TYPE = KIOSK_PAGE_TYPE.toString();
    protected static final String PAGE_NAME_DEFAULT = "events_page";

    private KioskPageField<String> pageDescription;
    private KioskPageField<String> eventLangPickerLabel;
    private GeneralEventStringsContainer generalStringsContainer;
    private EventSectionContainer regSectionContainer;
    private EventSectionContainer coSectionContainer;
    private EventSectionContainer brSectionContainer;
    private EventSectionContainer otherSectionContainer;


    private EventsPage(Page pageRep, KioskPageField<String> pageTitleField, KioskPageMetadata pageMetadata, KioskPageType kioskPageType, 
                String pageName, KioskPageField<String> pageDescription, KioskPageField<String> eventLangPickerLabel, GeneralEventStringsContainer generalStringsContainer,
                EventSectionContainer regSectionContainer, EventSectionContainer coSectionContainer, EventSectionContainer brSectionContainer, EventSectionContainer otherSectionContainer) {
        super(pageRep, pageTitleField, pageMetadata, kioskPageType, pageName);
        this.pageDescription = pageDescription;
        this.eventLangPickerLabel = eventLangPickerLabel;
        this.generalStringsContainer = generalStringsContainer;
        this.regSectionContainer = regSectionContainer;
        this.coSectionContainer = coSectionContainer;
        this.brSectionContainer = brSectionContainer;
        this.otherSectionContainer = otherSectionContainer;
    }


    public KioskPageField<String> getPageDescriptionField() {
        return this.pageDescription;
    }

    public KioskPageField<String> getEventLangPickerLabelField() {
        return this.eventLangPickerLabel;
    }

    public GeneralEventStringsContainer getGeneralStringsContainer() {
        return this.generalStringsContainer;
    }

    public EventSectionContainer getRegSectionContainer() {
        return this.regSectionContainer;
    }

    public EventSectionContainer getCoSectionContainer() {
        return this.coSectionContainer;
    }
    public EventSectionContainer getBrSectionContainer() {
        return this.brSectionContainer;
    }
    public EventSectionContainer getOtherSectionContainer() {
        return this.otherSectionContainer;
    }

    @Override
    protected Page buildPageRep(Page.Builder pageRepBuilder) {
        FieldContainer genStringsFC = this.generalStringsContainer.toFieldContainer(GEN_STRINGS_CONTAINER_NAME, cmsConverter);
        FieldContainer sectionRegFC = this.regSectionContainer.toFieldContainer(SECTION_REG_CONTAINER_NAME, cmsConverter);
        FieldContainer sectionCoFC = this.coSectionContainer.toFieldContainer(SECTION_CO_CONTAINER_NAME, cmsConverter);
        FieldContainer sectionBrFC = this.brSectionContainer.toFieldContainer(SECTION_BR_CONTAINER_NAME, cmsConverter);
        FieldContainer sectionOtherFC = this.otherSectionContainer.toFieldContainer(SECTION_OTHER_CONTAINER_NAME, cmsConverter);

        FieldContainer mainFC = new FieldContainer.Builder()
                                                            .addRegularTextLongDescriptionField(cmsConverter.toRegTextLongDescr(this.pageDescription))
                                                            .addRegularTextLongDescriptionField(cmsConverter.toRegTextLongDescr(this.eventLangPickerLabel))
                                                            .fieldContainerName(MAIN_CONTAINER_NAME)
                                                            .addChildContainer(genStringsFC)
                                                            .addChildContainer(sectionRegFC)
                                                            .addChildContainer(sectionCoFC)
                                                            .addChildContainer(sectionBrFC)
                                                            .addChildContainer(sectionOtherFC)
                                                            .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);

        pageRepBuilder.fieldContainers(fieldContainers);

        return pageRepBuilder.build();

    }




    public static class Builder extends KioskPage.Builder<Builder, EventsPage> {
        private KioskPageField<String> pageDescriptionField;
        private EditedPageFieldPayload<String> editedPageDescriptionField;
        private KioskPageField<String> eventLangPickerLabelField;
        private EditedPageFieldPayload<String> editedEventLangPickerLabelField;

        private GeneralEventStringsValidator genEventStringsValidator;
        private GeneralEventStringsContainer generalEventStringsContainer;
        private EditedGeneralEventStringsContainer editedGeneralEventStringsContainer;

        private EventSectionContainerValidator eventSectionContainerValidator;

        private EventSectionContainer regSectionContainer;
        private EditedEventSectionContainer editedRegSectionContainer;

        private EventSectionContainer coSectionContainer;
        private EditedEventSectionContainer editedCoSectionContainer;

        private EventSectionContainer brSectionContainer;
        private EditedEventSectionContainer editedBrSectionContainer;

        private EventSectionContainer otherSectionContainer;
        private EditedEventSectionContainer editedOtherSectionContainer;


        public Builder(String pageName) {
            super(KIOSK_PAGE_TYPE);
            super.pageName = pageName;
        }
        
        public Builder() {
            super(KIOSK_PAGE_TYPE);
            super.pageName = PAGE_NAME_DEFAULT;
        }

        public Builder(Page pageRep) {
            super(KIOSK_PAGE_TYPE, pageRep);
        }

        public Builder pageDescriptionField(EditedPageFieldPayload<String> pageDescriptionField) {
            this.editedPageDescriptionField = pageDescriptionField;
            return self();
        }

        public Builder eventLangPickerLabelField(EditedPageFieldPayload<String> eventLangPickerLabelField) {
            this.editedEventLangPickerLabelField = eventLangPickerLabelField;
            return self();
        }

        public Builder generalEventStringsContainer(EditedGeneralEventStringsContainer generalEventStringsContainer) {
            this.editedGeneralEventStringsContainer = generalEventStringsContainer;
            return self();
        }

        public Builder regSectionContainer(EditedEventSectionContainer regSectionContainer) {
            this.editedRegSectionContainer = regSectionContainer;
            return self();
        }

        public Builder coSectionContainer(EditedEventSectionContainer coSectionContainer) {
            this.editedCoSectionContainer = coSectionContainer;
            return self();
        }

        public Builder brSectionContainer(EditedEventSectionContainer brSectionContainer) {
            this.editedBrSectionContainer = brSectionContainer;
            return self();
        }

        public Builder otherSectionContainer(EditedEventSectionContainer otherSectionContainer) {
            this.editedOtherSectionContainer = otherSectionContainer;
            return self();
        }


        @Override
        protected void validateAndPrepareChild() {

            if(super.pageRep != null) {
                // process pageRep, overriding everything else
                FieldContainer mainFC = pageRep.getFieldContainers().get(0);

                FieldSelector fieldSelectorMainFc = new FieldSelector(mainFC);
                FieldContainer genStringsFC = fieldSelectorMainFc.selectChildContainer(GEN_STRINGS_CONTAINER_NAME);
                FieldContainer sectionRegFC = fieldSelectorMainFc.selectChildContainer(SECTION_REG_CONTAINER_NAME);
                FieldContainer sectionCoFC = fieldSelectorMainFc.selectChildContainer(SECTION_CO_CONTAINER_NAME);
                FieldContainer sectionBrFC = fieldSelectorMainFc.selectChildContainer(SECTION_BR_CONTAINER_NAME);
                FieldContainer sectionOtherFC = fieldSelectorMainFc.selectChildContainer(SECTION_OTHER_CONTAINER_NAME);

                GeneralEventStringsFieldSelector genEventStringFieldSelector = new GeneralEventStringsFieldSelector(GEN_STRINGS_EVENTTHEMELABEL_FIELD_TYPE, 
                                                                                                                    GEN_STRINGS_DURATIONLABEL_FIELD_TYPE,
                                                                                                                    GEN_STRINGS_YEARSSHOWINGLABEL_FIELD_TYPE,
                                                                                                                    GEN_STRINGS_DATELABEL_FIELD_TYPE,
                                                                                                                    GEN_STRINGS_EVENTLANGLABEL_FIELD_TYPE,
                                                                                                                    GEN_STRINGS_NOEVENTSFOUND_FIELD_TYPE);
                EventSectionFieldSelector eventSectionFieldSelector = new EventSectionFieldSelector(SECTION_TITLE_FIELD_TYPE, SECTION_BUTTON_TEXT_FIELD_TYPE);
                
                generalEventStringsContainer = GeneralEventStringsContainer.fromFieldContainer(genStringsFC, super.toKioskPageFieldConverter, genEventStringFieldSelector);
                
                regSectionContainer = EventSectionContainer.fromFieldContainer(sectionRegFC, super.toKioskPageFieldConverter, eventSectionFieldSelector);
                coSectionContainer = EventSectionContainer.fromFieldContainer(sectionCoFC, super.toKioskPageFieldConverter, eventSectionFieldSelector);
                brSectionContainer = EventSectionContainer.fromFieldContainer(sectionBrFC, super.toKioskPageFieldConverter, eventSectionFieldSelector);
                otherSectionContainer = EventSectionContainer.fromFieldContainer(sectionOtherFC, super.toKioskPageFieldConverter, eventSectionFieldSelector);

                RegularTextLongDescriptionField cmsPageDescriptionField = fieldSelectorMainFc.selectRegTextLongDescrField(PAGE_DESCR_FIELD_TYPE);
                RegularTextLongDescriptionField cmsEventLangPickerLabelField = fieldSelectorMainFc.selectRegTextLongDescrField(EVENTLANGPICKER_LABEL_FIELD_TYPE);

                pageDescriptionField = super.toKioskPageFieldConverter.toStringField(cmsPageDescriptionField);
                eventLangPickerLabelField = super.toKioskPageFieldConverter.toStringField(cmsEventLangPickerLabelField);
                

            }

            if(pageDescriptionField == null) {
                if(editedPageDescriptionField == null) {
                    editedPageDescriptionField =  new EditedPageFieldPayload<String>(PAGE_DESCR_FIELD_NAME_DEFAULT, null);
                }
                pageDescriptionField = editedPageDescriptionField.toKioskPageField(PAGE_DESCR_FIELD_TYPE, true);
            }

            if(eventLangPickerLabelField == null) {
                if(editedEventLangPickerLabelField == null) {
                    editedEventLangPickerLabelField = new EditedPageFieldPayload<String>(EVENTLANGPICKER_LABEL_FIELD_NAME_DEFAULT, null);
                }
                eventLangPickerLabelField = editedEventLangPickerLabelField.toKioskPageField(EVENTLANGPICKER_LABEL_FIELD_TYPE, true);
            }

            if(generalEventStringsContainer == null) {
                if(editedGeneralEventStringsContainer == null) {
                    editedGeneralEventStringsContainer = EditedGeneralEventStringsContainer.empty(GEN_STRINGS_EVENTTHEMELABEL_FIELD_NAME_DEFAULT, 
                                                                                                    GEN_STRINGS_DURATIONLABEL_FIELD_NAME_DEFAULT,
                                                                                                    GEN_STRINGS_YEARSSHOWINGLABEL_FIELD_NAME_DEFAULT,
                                                                                                    GEN_STRINGS_DATELABEL_FIELD_NAME_DEFAULT,
                                                                                                    GEN_STRINGS_EVENTLANGLABEL_FIELD_NAME_DEFAULT,
                                                                                                    GEN_STRINGS_NOEVENTSFOUND_FIELD_NAME_DEFAULT);
                }
                generalEventStringsContainer = editedGeneralEventStringsContainer.toGeneralEventStringsContainer(GEN_STRINGS_EVENTTHEMELABEL_FIELD_TYPE, 
                                                                                                                GEN_STRINGS_DURATIONLABEL_FIELD_TYPE,
                                                                                                                GEN_STRINGS_YEARSSHOWINGLABEL_FIELD_TYPE,
                                                                                                                GEN_STRINGS_DATELABEL_FIELD_TYPE,
                                                                                                                GEN_STRINGS_EVENTLANGLABEL_FIELD_TYPE,
                                                                                                                GEN_STRINGS_NOEVENTSFOUND_FIELD_TYPE);
            }

            if(regSectionContainer == null) {
                if(editedRegSectionContainer == null) {
                    editedRegSectionContainer = EditedEventSectionContainer.empty(SECTION_TITLE_FIELD_NAME_DEFAULT, SECTION_BUTTON_TEXT_FIELD_NAME_DEFAULT);
                }

                regSectionContainer = editedRegSectionContainer.toEventSectionContainer(SECTION_TITLE_FIELD_TYPE, SECTION_BUTTON_TEXT_FIELD_TYPE);
            }

            if(coSectionContainer == null) {
                if(editedCoSectionContainer == null) {
                    editedCoSectionContainer =  EditedEventSectionContainer.empty(SECTION_TITLE_FIELD_NAME_DEFAULT, SECTION_BUTTON_TEXT_FIELD_NAME_DEFAULT);
                }

                coSectionContainer = editedCoSectionContainer.toEventSectionContainer(SECTION_TITLE_FIELD_TYPE, SECTION_BUTTON_TEXT_FIELD_TYPE);
            }

            if(brSectionContainer == null) {
                if(editedBrSectionContainer == null) {
                    editedBrSectionContainer =  EditedEventSectionContainer.empty(SECTION_TITLE_FIELD_NAME_DEFAULT, SECTION_BUTTON_TEXT_FIELD_NAME_DEFAULT);
                }

                brSectionContainer = editedBrSectionContainer.toEventSectionContainer(SECTION_TITLE_FIELD_TYPE, SECTION_BUTTON_TEXT_FIELD_TYPE);
            }

            if(otherSectionContainer == null) {
                if(editedOtherSectionContainer == null) {
                    editedOtherSectionContainer =  EditedEventSectionContainer.empty(SECTION_TITLE_FIELD_NAME_DEFAULT, SECTION_BUTTON_TEXT_FIELD_NAME_DEFAULT);
                }

                otherSectionContainer = editedOtherSectionContainer.toEventSectionContainer(SECTION_TITLE_FIELD_TYPE, SECTION_BUTTON_TEXT_FIELD_TYPE);
            }

            // validate objects
            if (!KioskPageFieldTypeValidator.isValid(pageDescriptionField, PAGE_DESCR_FIELD_TYPE)) {
                throw new RuntimeException("PageDescription fieldType does not match");
            }

            if (!KioskPageFieldTypeValidator.isValid(eventLangPickerLabelField, EVENTLANGPICKER_LABEL_FIELD_TYPE)) {
                throw new RuntimeException("EventLangPickerLabelField fieldType does not match");
            }

            genEventStringsValidator = new GeneralEventStringsValidator(GEN_STRINGS_EVENTTHEMELABEL_FIELD_TYPE, 
                                                                        GEN_STRINGS_DURATIONLABEL_FIELD_TYPE,
                                                                        GEN_STRINGS_YEARSSHOWINGLABEL_FIELD_TYPE,
                                                                        GEN_STRINGS_DATELABEL_FIELD_TYPE,
                                                                        GEN_STRINGS_EVENTLANGLABEL_FIELD_TYPE,
                                                                        GEN_STRINGS_NOEVENTSFOUND_FIELD_TYPE);
            
            if(!genEventStringsValidator.isValid(generalEventStringsContainer)) {
                throw new RuntimeException("Invalid field type in the GeneralEventStringsContainer");
            }

            eventSectionContainerValidator = new EventSectionContainerValidator(SECTION_TITLE_FIELD_TYPE, SECTION_BUTTON_TEXT_FIELD_TYPE);

            if(!eventSectionContainerValidator.isValid(regSectionContainer)) {
                throw new RuntimeException("Invalid field type in the EventSectionContainer for reg");
            }

            if(!eventSectionContainerValidator.isValid(coSectionContainer)) {
                throw new RuntimeException("Invalid field type in the EventSectionContainer for co");
            }

            if(!eventSectionContainerValidator.isValid(brSectionContainer)) {
                throw new RuntimeException("Invalid field type in the EventSectionContainer for br");
            }

            if(!eventSectionContainerValidator.isValid(otherSectionContainer)) {
                throw new RuntimeException("Invalid field type in the EventSectionContainer for other");
            }
            
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected EventsPage buildChild() {

            return new EventsPage(super.pageRep, super.kioskPageTitleField, super.pageMetadata, super.kioskPageType, super.pageName,
                                    pageDescriptionField, eventLangPickerLabelField, generalEventStringsContainer, regSectionContainer,
                                    coSectionContainer, brSectionContainer, otherSectionContainer);
        }
    }
}
