package com.ppublica.apps.kiosk.domain.model.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class HomePageTest {

    @Test
    public void givenValidHomePage_correctGetters() {
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();

        HomePage homePage = new HomePage.Builder()
                                        .createdOn(createdOn)
                                        .lastModified(lastModified)
                                        .pageStatus(PageStatus.DRAFT)
                                        .pageName("home page")
                                        .pageTitle("home_title")
                                        .withLocaleId(5L)
                                        .tapToContinueTextField(new EditedPageFieldPayload<String>("tap-to_name", "tap-to_value"))
                                        .welcomeTextField(new EditedPageFieldPayload<String>("welcome_name", "welcome_value"))
                                        .showSelectFromAvailableLocalesField(new EditedPageFieldPayload<Boolean>("show_select_name", true))
                                        .build();

        Assertions.assertEquals("tap-to_value", homePage.getTapToContinueText().getFieldValue());
        Assertions.assertEquals("home_title", homePage.getPageTitleField().getFieldValue());
        Assertions.assertEquals(KioskPageType.HOME, homePage.getPageType());
        Assertions.assertEquals("home page", homePage.getPageName());
        Assertions.assertEquals("welcome_value", homePage.getWelcomeText().getFieldValue());
        Assertions.assertTrue(homePage.shouldShowSelectFromAvailableLocales().getFieldValue());


    }

    @Test
    public void givenValidPageRep_correctGetters() {
    
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();

        FieldContainer mainFC = new FieldContainer.Builder()
                                                        .addButtonField(new ButtonField(HomePage.SHOW_SELECT_FROM_AVAILABLE_LOCALES_TYPE, HomePage.SHOW_SELECT_FROM_AVAILABLE_LOCALES_FIELD_NAME_DEFAULT, true))
                                                        .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(HomePage.TAP_TO_CONTINUE_TEXT_TYPE, HomePage.TAP_TO_CONTINUE_TEXT_FIELD_NAME_DEFAULT, "tap-to_value"))
                                                        .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(HomePage.WELCOME_TEXT_TYPE, HomePage.WELCOME_TEXT_FIELD_NAME_DEFAULT, "welcome_value"))
                                                        .fieldContainerName(HomePage.MAIN_CONTAINER_NAME)
                                                        .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);


        Page pageRep = new Page.Builder().pageInternals(new PageInternals(2L, PageStatus.PUBLISHED, createdOn, lastModified))
                                .titleField(new PageTitleField(KioskPage.PAGE_TITLE_FIELD_TYPE, KioskPage.PAGE_TITLE_FIELD_NAME_DEFAULT, "home_title"))
                                .pageType(KioskPageType.HOME.toString())
                                .pageName("home page")
                                .fieldContainers(fieldContainers)
                                .build();

        HomePage homePage = new HomePage.Builder(pageRep)
                                        .build();

        Assertions.assertEquals("tap-to_value", homePage.getTapToContinueText().getFieldValue());
        Assertions.assertEquals("home_title", homePage.getPageTitleField().getFieldValue());
        Assertions.assertEquals(KioskPageType.HOME, homePage.getPageType());
        Assertions.assertEquals("home page", homePage.getPageName());
        Assertions.assertEquals("welcome_value", homePage.getWelcomeText().getFieldValue());
        Assertions.assertTrue(homePage.shouldShowSelectFromAvailableLocales().getFieldValue());
        
    }

}
