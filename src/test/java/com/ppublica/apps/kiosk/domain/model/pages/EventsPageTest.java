package com.ppublica.apps.kiosk.domain.model.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class EventsPageTest {

    @Test
    public void givenValidEventsPage_correctGetters() {
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();

        EventsPage eventsPage = new EventsPage.Builder()
                                        .createdOn(createdOn)
                                        .lastModified(lastModified)
                                        .pageStatus(PageStatus.DRAFT)
                                        .pageName("sample")
                                        .pageTitle("page_title")
                                        .withLocaleId(5L)
                                        .pageDescriptionField(new EditedPageFieldPayload<String>("pageDescr_fName", "pageDescr_fValue"))
                                        .eventLangPickerLabelField(new EditedPageFieldPayload<String>("eventLangPickerLabel_fName", "eventLangPickerLabel_fValue"))
                                        .generalEventStringsContainer(new EditedGeneralEventStringsContainer(
                                                                            new EditedPageFieldPayload<String>("eventThemeLabel_fName", "eventThemeLabel_fValue"),
                                                                            new EditedPageFieldPayload<String>("durationLabel_fName", "durationLabel_fValue"),
                                                                            new EditedPageFieldPayload<String>("yearsShowingLabel_fName", "yearsShowingLabel_fValue"),
                                                                            new EditedPageFieldPayload<String>("dateLabel_fName", "dateLabel_fValue"),
                                                                            new EditedPageFieldPayload<String>("eventLangLabel_fName", "eventLangLabel_fValue"),
                                                                            new EditedPageFieldPayload<String>("noEventsFound_fName", "noEventsFound_fValue")))
                                        .regSectionContainer(new EditedEventSectionContainer(
                                                                    new EditedPageFieldPayload<String>("regTitle_fName", "regTitle_fValue"),
                                                                    new EditedPageFieldPayload<String>("regBtnText_fName", "regBtnText_fValue")))
                                        .coSectionContainer(new EditedEventSectionContainer(
                                                                    new EditedPageFieldPayload<String>("coTitle_fName", "coTitle_fValue"),
                                                                    new EditedPageFieldPayload<String>("coBtnText_fName", "coBtnText_fValue")))
                                        .brSectionContainer(new EditedEventSectionContainer(
                                                                    new EditedPageFieldPayload<String>("brTitle_fName", "brTitle_fValue"),
                                                                    new EditedPageFieldPayload<String>("brBtnText_fName", "brBtnText_fValue")))
                                        .build();

        Assertions.assertEquals("page_title", eventsPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals("sample", eventsPage.getPageName());
        Assertions.assertEquals(KioskPageType.EVENTS, eventsPage.getPageType());
        
        Assertions.assertEquals("pageDescr_fValue", eventsPage.getPageDescriptionField().getFieldValue());
        Assertions.assertEquals("eventLangPickerLabel_fValue", eventsPage.getEventLangPickerLabelField().getFieldValue());

        GeneralEventStringsContainer genEventStringsContaienr = eventsPage.getGeneralStringsContainer();
        Assertions.assertEquals("eventThemeLabel_fValue", genEventStringsContaienr.getEventThemeLabelField().getFieldValue());
        Assertions.assertEquals("durationLabel_fValue", genEventStringsContaienr.getDurationLabelField().getFieldValue());
        Assertions.assertEquals("yearsShowingLabel_fValue", genEventStringsContaienr.getYearsShowingLabelField().getFieldValue());
        Assertions.assertEquals("dateLabel_fValue", genEventStringsContaienr.getDateLabel().getFieldValue());
        Assertions.assertEquals("eventLangLabel_fValue", genEventStringsContaienr.getEventLangLabelField().getFieldValue());
        Assertions.assertEquals("noEventsFound_fValue", genEventStringsContaienr.getNoEventsFoundField().getFieldValue());


        EventSectionContainer regEventSectionContainer = eventsPage.getRegSectionContainer();
        Assertions.assertEquals("regTitle_fValue", regEventSectionContainer.getTitleField().getFieldValue());
        Assertions.assertEquals("regBtnText_fValue", regEventSectionContainer.getBtnTextField().getFieldValue());

        EventSectionContainer coEventSectionContainer = eventsPage.getCoSectionContainer();
        Assertions.assertEquals("coTitle_fValue", coEventSectionContainer.getTitleField().getFieldValue());
        Assertions.assertEquals("coBtnText_fValue", coEventSectionContainer.getBtnTextField().getFieldValue());

        EventSectionContainer brEventSectionContainer = eventsPage.getBrSectionContainer();
        Assertions.assertEquals("brTitle_fValue", brEventSectionContainer.getTitleField().getFieldValue());
        Assertions.assertEquals("brBtnText_fValue", brEventSectionContainer.getBtnTextField().getFieldValue());

        EventSectionContainer otherEventSectionContainer = eventsPage.getOtherSectionContainer();
        Assertions.assertNotNull(otherEventSectionContainer);
        Assertions.assertEquals(EventsPage.SECTION_TITLE_FIELD_NAME_DEFAULT, otherEventSectionContainer.getTitleField().getFieldName());
        Assertions.assertEquals(EventsPage.SECTION_BUTTON_TEXT_FIELD_NAME_DEFAULT, otherEventSectionContainer.getBtnTextField().getFieldName());

    }

    @Test
    public void givenValidPageRep_correctGetters() {
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();

        FieldContainer genEventStringsFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.GEN_STRINGS_EVENTTHEMELABEL_FIELD_TYPE, "eventThemeLabel_fName", "eventThemeLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.GEN_STRINGS_DURATIONLABEL_FIELD_TYPE, "durationLabel_fName", "durationLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.GEN_STRINGS_YEARSSHOWINGLABEL_FIELD_TYPE, "yearsShowingLabel_fName", "yearsShowingLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.GEN_STRINGS_DATELABEL_FIELD_TYPE, "dateLabel_fName", "dateLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.GEN_STRINGS_EVENTLANGLABEL_FIELD_TYPE, "eventLangLabel_fName", "eventLangLabel_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.GEN_STRINGS_NOEVENTSFOUND_FIELD_TYPE, "noEventsFound_fName", "noEventsFound_fValue"))
                                                .fieldContainerName(EventsPage.GEN_STRINGS_CONTAINER_NAME)
                                                .build();

        FieldContainer regFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.SECTION_TITLE_FIELD_TYPE, "regTitle_fName", "regTitle_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.SECTION_BUTTON_TEXT_FIELD_TYPE, "regBtnText_fName", "regBtnText_fValue"))
                                                .fieldContainerName(EventsPage.SECTION_REG_CONTAINER_NAME)
                                                .build();
        
        FieldContainer coFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.SECTION_TITLE_FIELD_TYPE, "coTitle_fName", "coTitle_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.SECTION_BUTTON_TEXT_FIELD_TYPE, "coBtnText_fName", "coBtnText_fValue"))
                                                .fieldContainerName(EventsPage.SECTION_CO_CONTAINER_NAME)
                                                .build();

        FieldContainer brFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.SECTION_TITLE_FIELD_TYPE, "brTitle_fName", "brTitle_fValue"))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.SECTION_BUTTON_TEXT_FIELD_TYPE, "brBtnText_fName", "brBtnText_fValue"))
                                                .fieldContainerName(EventsPage.SECTION_BR_CONTAINER_NAME)
                                                .build();

        FieldContainer otherFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.SECTION_TITLE_FIELD_TYPE, EventsPage.SECTION_TITLE_FIELD_NAME_DEFAULT, null))
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(EventsPage.SECTION_BUTTON_TEXT_FIELD_TYPE, EventsPage.SECTION_BUTTON_TEXT_FIELD_NAME_DEFAULT, null))
                                                .fieldContainerName(EventsPage.SECTION_OTHER_CONTAINER_NAME)
                                                .build();

        RegularTextLongDescriptionField pageDescriptionField = new RegularTextLongDescriptionField(EventsPage.PAGE_DESCR_FIELD_TYPE, EventsPage.PAGE_DESCR_FIELD_NAME_DEFAULT, "pageDescr_fValue");
        RegularTextLongDescriptionField eventLangPickerLabelField = new RegularTextLongDescriptionField(EventsPage.EVENTLANGPICKER_LABEL_FIELD_TYPE, EventsPage.EVENTLANGPICKER_LABEL_FIELD_NAME_DEFAULT, "eventLangPickerLabel_fValue");


        FieldContainer mainFC = new FieldContainer.Builder()
                                                .fieldContainerName(EventsPage.MAIN_CONTAINER_NAME)
                                                .addChildContainer(genEventStringsFC)
                                                .addChildContainer(regFC)
                                                .addChildContainer(coFC)
                                                .addChildContainer(brFC)
                                                .addChildContainer(otherFC)
                                                .addRegularTextLongDescriptionField(pageDescriptionField)
                                                .addRegularTextLongDescriptionField(eventLangPickerLabelField)
                                                .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);


        Page pageRep = new Page.Builder().pageInternals(new PageInternals(2L, PageStatus.PUBLISHED, createdOn, lastModified))
                                .titleField(new PageTitleField(KioskPage.PAGE_TITLE_FIELD_TYPE, KioskPage.PAGE_TITLE_FIELD_NAME_DEFAULT, "page_title"))
                                .pageType(KioskPageType.EVENTS.toString())
                                .pageName("sample")
                                .fieldContainers(fieldContainers)
                                .build();


        EventsPage eventsPage = new EventsPage.Builder(pageRep)
                                .build();


        Assertions.assertEquals("page_title", eventsPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals("sample", eventsPage.getPageName());
        Assertions.assertEquals(KioskPageType.EVENTS, eventsPage.getPageType());
        
        Assertions.assertEquals("pageDescr_fValue", eventsPage.getPageDescriptionField().getFieldValue());
        Assertions.assertEquals("eventLangPickerLabel_fValue", eventsPage.getEventLangPickerLabelField().getFieldValue());

        GeneralEventStringsContainer genEventStringsContaienr = eventsPage.getGeneralStringsContainer();
        Assertions.assertEquals("eventThemeLabel_fValue", genEventStringsContaienr.getEventThemeLabelField().getFieldValue());
        Assertions.assertEquals("durationLabel_fValue", genEventStringsContaienr.getDurationLabelField().getFieldValue());
        Assertions.assertEquals("yearsShowingLabel_fValue", genEventStringsContaienr.getYearsShowingLabelField().getFieldValue());
        Assertions.assertEquals("dateLabel_fValue", genEventStringsContaienr.getDateLabel().getFieldValue());
        Assertions.assertEquals("eventLangLabel_fValue", genEventStringsContaienr.getEventLangLabelField().getFieldValue());
        Assertions.assertEquals("noEventsFound_fValue", genEventStringsContaienr.getNoEventsFoundField().getFieldValue());


        EventSectionContainer regEventSectionContainer = eventsPage.getRegSectionContainer();
        Assertions.assertEquals("regTitle_fValue", regEventSectionContainer.getTitleField().getFieldValue());
        Assertions.assertEquals("regBtnText_fValue", regEventSectionContainer.getBtnTextField().getFieldValue());

        EventSectionContainer coEventSectionContainer = eventsPage.getCoSectionContainer();
        Assertions.assertEquals("coTitle_fValue", coEventSectionContainer.getTitleField().getFieldValue());
        Assertions.assertEquals("coBtnText_fValue", coEventSectionContainer.getBtnTextField().getFieldValue());

        EventSectionContainer brEventSectionContainer = eventsPage.getBrSectionContainer();
        Assertions.assertEquals("brTitle_fValue", brEventSectionContainer.getTitleField().getFieldValue());
        Assertions.assertEquals("brBtnText_fValue", brEventSectionContainer.getBtnTextField().getFieldValue());

        EventSectionContainer otherEventSectionContainer = eventsPage.getOtherSectionContainer();
        Assertions.assertNotNull(otherEventSectionContainer);
        Assertions.assertEquals(EventsPage.SECTION_TITLE_FIELD_NAME_DEFAULT, otherEventSectionContainer.getTitleField().getFieldName());
        Assertions.assertEquals(EventsPage.SECTION_BUTTON_TEXT_FIELD_NAME_DEFAULT, otherEventSectionContainer.getBtnTextField().getFieldName());
        
    }
    
}
