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
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;

public class Error404PageTest {

    @Test
    public void givenValidErrorPage_correctGetters() {
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();

        Error404Page error404Page = new Error404Page.Builder()
                                        .createdOn(createdOn)
                                        .lastModified(lastModified)
                                        .pageStatus(PageStatus.DRAFT)
                                        .pageName("error404")
                                        .pageTitle("page_title")
                                        .withLocaleId(5L)
                                        .errorDescription("error_descr")
                                        .showRedirectLink(true)
                                        .redirectUrl("/redirect")
                                        .redirectDisplayText("redirect_display_text")
                                        .redirectDescription("redirect_description")
                                        .build();

        Assertions.assertEquals("error_descr", error404Page.getErrorDescription());
        Assertions.assertEquals("page_title", error404Page.getPageTitle());
        Assertions.assertEquals("ERROR", error404Page.getPageType());
        Assertions.assertEquals("redirect_description", error404Page.getRedirectDescription());
        Assertions.assertEquals("redirect_display_text", error404Page.getRedirectDisplayText());
        Assertions.assertEquals("/redirect", error404Page.getRedirectUrl());
        Assertions.assertTrue(error404Page.shouldShowRedirectLink());


    }

    @Test
    public void givenValidPageRep_correctGetters() {
    
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();

        FieldContainer redirectLinkFC = new FieldContainer.Builder()
                                                            .addUrlField(new UrlField(Error404Page.REDIRECT_URL_FIELD_NAME, "/redirect"))
                                                            .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(Error404Page.REDIRECT_DISPLAY_TEXT_FIELD_NAME, "redirect_display_text"))
                                                            .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(Error404Page.REDIRECT_DESCRIPTION_FIELD_NAME, "redirect_description"))
                                                            .fieldContainerName(Error404Page.REDIRECT_URL_CONTAINER_NAME)
                                                            .build();


        FieldContainer mainFC = new FieldContainer.Builder()
                                                        .addButtonField(new ButtonField(Error404Page.SHOW_REDIRECT_LINK_OPTION_FIELD_NAME, true))
                                                        .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(Error404Page.ERROR_DESCR__FIELD_NAME, "error_descr"))
                                                        .fieldContainerName(Error404Page.MAIN_CONTAINER_NAME)
                                                        .addChildContainer(redirectLinkFC)
                                                        .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);


        Page pageRep = new Page.Builder().pageInternals(new PageInternals(2L, PageStatus.PUBLISHED, createdOn, lastModified))
                                .titleField(new PageTitleField(KioskPage.PAGE_TITLE_FIELD_NAME, "page_title"))
                                .pageType(KioskPageType.ERROR.toString())
                                .pageName("error404")
                                .fieldContainers(fieldContainers)
                                .build();


        Error404Page error404Page = Error404Page.fromPage(pageRep);

        Assertions.assertEquals("error_descr", error404Page.getErrorDescription());
        Assertions.assertEquals("page_title", error404Page.getPageTitle());
        Assertions.assertEquals("ERROR", error404Page.getPageType());
        Assertions.assertEquals("redirect_description", error404Page.getRedirectDescription());
        Assertions.assertEquals("redirect_display_text", error404Page.getRedirectDisplayText());
        Assertions.assertEquals("/redirect", error404Page.getRedirectUrl());
        Assertions.assertTrue(error404Page.shouldShowRedirectLink());
        
    }
    
}
