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
                                        .errorDescriptionField(new EditedPageFieldPayload<String>("error_descr_name", "error_descr"))
                                        .showRedirectLinkField(new EditedPageFieldPayload<Boolean>("show_redirect_name", true))
                                        .redirectUrlContainer(new EditedRedirectUrlContainer(
                                                                new EditedPageFieldPayload<String>("redirect_url", "/redirect"), 
                                                                new EditedPageFieldPayload<String>("redirect_display_text", "redirect_display_text_value"), 
                                                                new EditedPageFieldPayload<String>("redirect_description", "redirect_description_value")))
                                        .build();

        Assertions.assertEquals("error_descr", error404Page.getErrorDescriptionField().getFieldValue());
        Assertions.assertEquals("page_title", error404Page.getPageTitleField().getFieldValue());
        Assertions.assertEquals(KioskPageType.ERROR, error404Page.getPageType());
        Assertions.assertEquals("redirect_description_value", error404Page.getRedirectUrlContainer().getRedirectDescriptionField().getFieldValue());
        Assertions.assertEquals("redirect_display_text_value", error404Page.getRedirectUrlContainer().getRedirectDisplayTextField().getFieldValue());
        Assertions.assertEquals("/redirect", error404Page.getRedirectUrlContainer().getRedirectUrlField().getFieldValue());
        Assertions.assertTrue(error404Page.shouldShowRedirectLink().getFieldValue());


    }

    @Test
    public void givenValidPageRep_correctGetters() {
    
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();

        FieldContainer redirectLinkFC = new FieldContainer.Builder()
                                                            .addUrlField(new UrlField(Error404Page.ERROR_DESCR__FIELD_TYPE, Error404Page.ERROR_DESCR__FIELD_NAME_DEFAULT, "/redirect"))
                                                            .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(Error404Page.REDIRECT_DISPLAY_TEXT_FIELD_TYPE, Error404Page.REDIRECT_DISPLAY_TEXT_FIELD_NAME_DEFAULT, "redirect_display_text"))
                                                            .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(Error404Page.REDIRECT_DESCRIPTION_FIELD_TYPE, Error404Page.REDIRECT_DESCRIPTION_FIELD_NAME_DEFAULT, "redirect_description"))
                                                            .fieldContainerName(Error404Page.REDIRECT_URL_CONTAINER_NAME)
                                                            .build();


        FieldContainer mainFC = new FieldContainer.Builder()
                                                        .addButtonField(new ButtonField(Error404Page.SHOW_REDIRECT_LINK_OPTION_FIELD_TYPE, Error404Page.SHOW_REDIRECT_LINK_OPTION_FIELD_NAME_DEFAULT, true))
                                                        .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(Error404Page.ERROR_DESCR__FIELD_TYPE, Error404Page.ERROR_DESCR__FIELD_NAME_DEFAULT, "error_descr"))
                                                        .fieldContainerName(Error404Page.MAIN_CONTAINER_NAME)
                                                        .addChildContainer(redirectLinkFC)
                                                        .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);


        Page pageRep = new Page.Builder().pageInternals(new PageInternals(2L, PageStatus.PUBLISHED, createdOn, lastModified))
                                .titleField(new PageTitleField(KioskPage.PAGE_TITLE_FIELD_TYPE, KioskPage.PAGE_TITLE_FIELD_NAME_DEFAULT, "page_title"))
                                .pageType(KioskPageType.ERROR.toString())
                                .pageName("error404")
                                .fieldContainers(fieldContainers)
                                .build();


        Error404Page error404Page = new Error404Page.Builder(pageRep)
                                        .build();

        Assertions.assertEquals("error_descr", error404Page.getErrorDescriptionField().getFieldValue());
        Assertions.assertEquals("page_title", error404Page.getPageTitleField().getFieldValue());
        Assertions.assertEquals(KioskPageType.ERROR, error404Page.getPageType());
        Assertions.assertEquals("redirect_description", error404Page.getRedirectUrlContainer().getRedirectDescriptionField().getFieldValue());
        Assertions.assertEquals("redirect_display_text", error404Page.getRedirectUrlContainer().getRedirectDisplayTextField().getFieldValue());
        Assertions.assertEquals("/redirect", error404Page.getRedirectUrlContainer().getRedirectUrlField().getFieldValue());
        Assertions.assertTrue(error404Page.shouldShowRedirectLink().getFieldValue());
        
    }
    
}
