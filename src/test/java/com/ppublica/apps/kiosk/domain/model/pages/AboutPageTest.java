package com.ppublica.apps.kiosk.domain.model.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;

public class AboutPageTest {
    
    @Test
    public void givenValidAboutPage_correctGetters() {
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();
        Image sampleImage = new Image("location", 1,  2);

        AboutPage aboutPage = new AboutPage.Builder()
                                        .createdOn(createdOn)
                                        .lastModified(lastModified)
                                        .pageStatus(PageStatus.DRAFT)
                                        .pageName("sample")
                                        .pageTitle("page_title")
                                        .withLocaleId(5L)
                                        .richDescriptionField(new EditedPageFieldPayload<String>("r_desc", "r_desc_field_name"))
                                        .imageContainer(new EditedImageContainer(new EditedPageFieldPayload<Image>("feat_img_field_name", sampleImage), 
                                                                                 new EditedPageFieldPayload<String>("feat_img", "feat_img_alt_text_field_name")))
                                        .build();

        Assertions.assertEquals(sampleImage, aboutPage.getImageContainer().getImageField().getFieldValue());
        Assertions.assertEquals("feat_img", aboutPage.getImageContainer().getImageAltTextField().getFieldName());
        Assertions.assertEquals("page_title", aboutPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals(KioskPageType.ABOUT, aboutPage.getPageType());
        Assertions.assertEquals("r_desc_field_name", aboutPage.getRichDescrField().getFieldValue());

    }

    @Test
    public void givenValidPageRep_correctGetters() {
    
        Image featureImage = new Image("location", 1,  2);
        LocalDate createdOn = LocalDate.of(2023, 9, 20);
        LocalDateTime lastModified = LocalDateTime.now();

        FieldContainer featureImageFC = new FieldContainer.Builder()
                                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(AboutPage.IMAGE_ALTERNATIVE_TEXT_FIELD_TYPE, AboutPage.IMAGE_ALTERNATIVE_TEXT_FIELD_NAME_DEFAULT, "altText"))
                                                .addImageField(new ImageField(AboutPage.FEATURE_IMAGE_FIELD_NAME_DEFAULT, AboutPage.FEATURE_IMAGE_FIELD_TYPE, featureImage))
                                                .fieldContainerName(AboutPage.IMAGE_CONTAINER_NAME)
                                                .build();

        FieldContainer mainFC = new FieldContainer.Builder()
                                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(AboutPage.RICH_DESCRIPTION_TEXT_FIELD_TYPE, AboutPage.RICH_DESCRIPTION_TEXT_FIELD_NAME_DEFAULT, "richDescription"))
                                                .fieldContainerName(AboutPage.MAIN_CONTAINER_NAME)
                                                .addChildContainer(featureImageFC)
                                                .build();

        List<FieldContainer> fieldContainers = new ArrayList<>();
        fieldContainers.add(mainFC);


        Page pageRep = new Page.Builder().pageInternals(new PageInternals(2L, PageStatus.PUBLISHED, createdOn, lastModified))
                                .titleField(new PageTitleField(KioskPage.PAGE_TITLE_FIELD_TYPE, KioskPage.PAGE_TITLE_FIELD_NAME_DEFAULT, "pageTitle"))
                                .pageType(KioskPageType.ABOUT.toString())
                                .pageName("pageName")
                                .fieldContainers(fieldContainers)
                                .build();


        AboutPage aboutPage = new AboutPage.Builder(pageRep)
                                .build();

        Assertions.assertEquals(featureImage, aboutPage.getImageContainer().getImageField().getFieldValue());
        Assertions.assertEquals("altText", aboutPage.getImageContainer().getImageAltTextField().getFieldValue());
        Assertions.assertEquals("pageTitle", aboutPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals(KioskPageType.ABOUT, aboutPage.getPageType());
        Assertions.assertEquals("richDescription", aboutPage.getRichDescrField().getFieldValue());
        
    }

}
