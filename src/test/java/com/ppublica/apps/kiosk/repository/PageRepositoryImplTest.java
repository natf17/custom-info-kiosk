package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Propagation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;


@JdbcTest
@TestPropertySource("classpath:test.properties")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import(PageRepositoryImpl.class)
public class PageRepositoryImplTest {
    @Autowired
    PageRepository repo;

    @Autowired
    JdbcTemplate template;

    Long enLocaleId;
    Long esLocaleId;

    Page newPageNoNesting;
    Page newPageNoNestingSp;

    Page newPageNoNestingUpdated;
    Page newPageWithComplexNestingUpdated;

    Page newPageWithComplexNesting;
    Page newPageWithComplexNestingSp;

    @BeforeEach
    public void setup() {
        
        this.enLocaleId = template.query("SELECT id FROM kiosk_locale WHERE abbrev = ?", new IdRowMapper(), "EN").get(0);
        this.esLocaleId = template.query("SELECT id FROM kiosk_locale WHERE abbrev = ?", new IdRowMapper(), "SP").get(0);
        

        LocalDate testDate = LocalDate.of(2023, 9, 12);
        LocalDateTime testDateTime = LocalDateTime.of(2023, 1, 9, 9, 30);

        this.newPageNoNesting = new Page.Builder()
                            .pageType("about")
                            .pageName("about_page")
                            .pageInternals(new PageInternals(enLocaleId, PageStatus.DRAFT, testDate, testDateTime))
                            .title("about_page_title")
                            .build();

        this.newPageNoNestingSp = new Page.Builder()
                            .pageType("about")
                            .pageName("pagina sobre...")
                            .pageInternals(new PageInternals(esLocaleId, PageStatus.DRAFT, testDate, testDateTime))
                            .title("titulo de pagina")
                            .build();

        this.newPageNoNestingUpdated = new Page.Builder()
                            .pageType("about")
                            .pageName("about_page_new")
                            .pageInternals(new PageInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                            .title("about_page_title_new")
                            .build();

        this.newPageWithComplexNestingUpdated = buildPageWithComplexNesting(new PageInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime), "sample", "upd_");


        this.newPageWithComplexNesting = buildPageWithComplexNesting(new PageInternals(enLocaleId, PageStatus.DRAFT, testDate, testDateTime), "sample", "");
        this.newPageWithComplexNestingSp = buildPageWithComplexNesting(new PageInternals(esLocaleId, PageStatus.DRAFT, testDate, testDateTime), "sample", "");

        
    }


    @Test
    public void givenNewPageWithNoNesting_saveAndRead_success() {


        repo.save(newPageNoNesting);

        Optional<Page> savedPageOpt = repo.findByPageTypeAndKioskLocale("about", "EN");
        Assertions.assertTrue(savedPageOpt.isPresent());

        Page savedPage = savedPageOpt.get();
        PageInternals savedPageInternals = savedPage.getPageInternals();
        
        Assertions.assertNotNull(savedPage.getId());
        Assertions.assertEquals(newPageNoNesting.getPageName(), savedPage.getPageName());
        Assertions.assertEquals(newPageNoNesting.getPageType(), savedPage.getPageType());
        Assertions.assertEquals(newPageNoNesting.getPageInternals().getKioskLocaleId(), savedPageInternals.getKioskLocaleId());
        Assertions.assertEquals(newPageNoNesting.getPageInternals().getPageStatus(), savedPageInternals.getPageStatus());
        Assertions.assertEquals(newPageNoNesting.getPageInternals().getCreatedOn(), savedPageInternals.getCreatedOn());
        Assertions.assertEquals(newPageNoNesting.getPageInternals().getLastModified(), savedPageInternals.getLastModified());
        Assertions.assertEquals(newPageNoNesting.getPageTitleField().getFieldType(), savedPage.getPageTitleField().getFieldType());
        Assertions.assertEquals(newPageNoNesting.getPageTitleField().getFieldName(), savedPage.getPageTitleField().getFieldName());
        Assertions.assertEquals(newPageNoNesting.getPageTitleField().getFieldValue(), savedPage.getPageTitleField().getFieldValue());
        Assertions.assertTrue(newPageNoNesting.getFieldContainers().isEmpty());                 

    }
    
    @Test
    public void givenNewPageWithNesting_saveAndRead_success() {

        repo.save(newPageWithComplexNesting);

        Optional<Page> savedPageOpt = repo.findByPageTypeAndKioskLocale("sample", "EN");
        Assertions.assertTrue(savedPageOpt.isPresent());
        
        Page savedPage = savedPageOpt.get();
        PageInternals savedPageInternals = savedPage.getPageInternals();
        List<FieldContainer> savedFieldContainers = savedPage.getFieldContainers();
        
        Assertions.assertNotNull(savedPage.getId());
        Assertions.assertEquals(newPageWithComplexNesting.getPageInternals().getKioskLocaleId(), savedPageInternals.getKioskLocaleId());
        Assertions.assertEquals(newPageWithComplexNesting.getPageTitleField().getFieldType(), savedPage.getPageTitleField().getFieldType());
        Assertions.assertEquals(newPageWithComplexNesting.getPageTitleField().getFieldName(), savedPage.getPageTitleField().getFieldName());
        Assertions.assertEquals(newPageWithComplexNesting.getPageTitleField().getFieldValue(), savedPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals(newPageWithComplexNesting.getFieldContainers().size(), savedFieldContainers.size());



        // Ensure layer 1 field containers were saved correctly
        FieldContainer savedFieldContainer_1a = savedFieldContainers.get(0);
        FieldContainer savedFieldContainer_1b = savedFieldContainers.get(1);

        Assertions.assertTrue(savedFieldContainer_1a.getFieldContainerName().equals("Layer1a_FC")|| savedFieldContainer_1b.getFieldContainerName().equals("Layer1a_FC"));

        if(! savedFieldContainer_1a.getFieldContainerName().equals("Layer1a_FC")) {
            savedFieldContainer_1a = savedFieldContainers.get(1);
            savedFieldContainer_1b = savedFieldContainers.get(0);
        }

        // first container
        Assertions.assertEquals(1, savedFieldContainer_1a.getButtonFields().size());
        Assertions.assertEquals("bf1a1", savedFieldContainer_1a.getButtonFields().get(0).getFieldName());
        Assertions.assertEquals(false, savedFieldContainer_1a.getButtonFields().get(0).getFieldValue());
        
        Assertions.assertEquals(1, savedFieldContainer_1a.getImageFields().size());
        Assertions.assertEquals("image1a1_type", savedFieldContainer_1a.getImageFields().get(0).getFieldType());
        Assertions.assertEquals("image1a1", savedFieldContainer_1a.getImageFields().get(0).getFieldName());
        Assertions.assertEquals("image1a_path", savedFieldContainer_1a.getImageFields().get(0).getFieldValue().location());
        Assertions.assertEquals(1, savedFieldContainer_1a.getImageFields().get(0).getFieldValue().width());
        Assertions.assertEquals(2, savedFieldContainer_1a.getImageFields().get(0).getFieldValue().height());
        
        Assertions.assertEquals(1, savedFieldContainer_1a.getRegularTextLongDescriptionFields().size());
        Assertions.assertEquals("regtldf1a1_type", savedFieldContainer_1a.getRegularTextLongDescriptionFields().get(0).getFieldType());
        Assertions.assertEquals("regtldf1a1", savedFieldContainer_1a.getRegularTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("sample regtldf1a1 value", savedFieldContainer_1a.getRegularTextLongDescriptionFields().get(0).getFieldValue());

        Assertions.assertEquals(1, savedFieldContainer_1a.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals("rtldf1a_type", savedFieldContainer_1a.getRichTextLongDescriptionFields().get(0).getFieldType());
        Assertions.assertEquals("rtldf1a", savedFieldContainer_1a.getRichTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("sample rtldf1a value", savedFieldContainer_1a.getRichTextLongDescriptionFields().get(0).getFieldValue());
        
        Assertions.assertEquals(1, savedFieldContainer_1a.getUrlFields().size());
        Assertions.assertEquals("urlf1a1_type", savedFieldContainer_1a.getUrlFields().get(0).getFieldType());
        Assertions.assertEquals("urlf1a1", savedFieldContainer_1a.getUrlFields().get(0).getFieldName());
        Assertions.assertEquals("url: urlf1a1", savedFieldContainer_1a.getUrlFields().get(0).getFieldValue());

        Assertions.assertTrue(savedFieldContainer_1a.getChildContainers().isEmpty());



        // second container
        Assertions.assertEquals("Layer1b_FC", savedFieldContainer_1b.getFieldContainerName());
        Assertions.assertTrue(savedFieldContainer_1b.getButtonFields().isEmpty());
        Assertions.assertTrue(savedFieldContainer_1b.getImageFields().isEmpty());
        Assertions.assertTrue(savedFieldContainer_1b.getRegularTextLongDescriptionFields().isEmpty());
        Assertions.assertEquals(1, savedFieldContainer_1b.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals("rtldf1b_type", savedFieldContainer_1b.getRichTextLongDescriptionFields().get(0).getFieldType());
        Assertions.assertEquals("rtldf1b", savedFieldContainer_1b.getRichTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("sample rtldf1b value", savedFieldContainer_1b.getRichTextLongDescriptionFields().get(0).getFieldValue());
        Assertions.assertTrue(savedFieldContainer_1b.getUrlFields().isEmpty());
        Assertions.assertEquals(1, savedFieldContainer_1b.getChildContainers().size());



        // Ensure layer 2 field containers were saved correctly
        FieldContainer savedFieldContainer_2 = savedFieldContainer_1b.getChildContainers().get(0);
        List<FieldContainer> savedFieldContainers_2 = savedFieldContainer_2.getChildContainers();

        Assertions.assertEquals("Layer2_FC", savedFieldContainer_2.getFieldContainerName());
        Assertions.assertEquals(1, savedFieldContainer_2.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals("rtldf2_type", savedFieldContainer_2.getRichTextLongDescriptionFields().get(0).getFieldType());
        Assertions.assertEquals("rtldf2", savedFieldContainer_2.getRichTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("sample rtldf2 value", savedFieldContainer_2.getRichTextLongDescriptionFields().get(0).getFieldValue());
        Assertions.assertEquals(2, savedFieldContainers_2.size());



        // Ensure layer 3 field containers were saved correctly
        FieldContainer savedFieldContainer_3a = savedFieldContainers_2.get(0);
        FieldContainer savedFieldContainer_3b = savedFieldContainers_2.get(1);

        Assertions.assertTrue(savedFieldContainer_3a.getFieldContainerName().equals("Layer3a_FC") || savedFieldContainer_3b.getFieldContainerName().equals("Layer3a_FC"));

        if(!savedFieldContainer_3a.getFieldContainerName().equals("Layer3a_FC")) {
            savedFieldContainer_3a = savedFieldContainers.get(1);
            savedFieldContainer_3b = savedFieldContainers.get(0);
        }

        // first container
        Assertions.assertTrue(savedFieldContainer_3a.getChildContainers().isEmpty());
        Assertions.assertTrue(savedFieldContainer_3a.getButtonFields().isEmpty());
        Assertions.assertTrue(savedFieldContainer_3a.getImageFields().isEmpty());
        Assertions.assertTrue(savedFieldContainer_3a.getRegularTextLongDescriptionFields().isEmpty());
        Assertions.assertTrue(savedFieldContainer_3a.getRichTextLongDescriptionFields().isEmpty());
        Assertions.assertTrue(savedFieldContainer_3a.getUrlFields().isEmpty());
        
        // second container
        Assertions.assertEquals("Layer3b_FC", savedFieldContainer_3b.getFieldContainerName());
        Assertions.assertEquals(2, savedFieldContainer_3b.getButtonFields().size());
        Assertions.assertEquals(2, savedFieldContainer_3b.getImageFields().size());
        Assertions.assertEquals(2, savedFieldContainer_3b.getRegularTextLongDescriptionFields().size());
        Assertions.assertEquals(2, savedFieldContainer_3b.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals(2, savedFieldContainer_3b.getUrlFields().size());
        Assertions.assertTrue(savedFieldContainer_3b.getChildContainers().isEmpty());

    }

    @Test
    public void givenPageWithNoNesting_saveAndDeletePageLocale_success() {
        repo.save(newPageNoNesting);
        repo.save(newPageNoNestingSp);
        repo.deletePageWithLocale("about", "SP");

        Assertions.assertTrue(repo.findByPageTypeAndKioskLocale("about", "SP").isEmpty());

        Optional<Page> otherPageOpt = repo.findByPageTypeAndKioskLocale("about", "EN");
        Assertions.assertTrue(otherPageOpt.isPresent());
        
        Page otherPage = otherPageOpt.get();
        Assertions.assertEquals("about", otherPage.getPageType());
        Assertions.assertEquals(enLocaleId, otherPage.getPageInternals().getKioskLocaleId());

    }

    @Test
    public void givenPageWithNesting_saveAndDeletePageLocale_success() {
        repo.save(newPageWithComplexNesting);
        repo.save(newPageWithComplexNestingSp);
        repo.deletePageWithLocale("sample", "SP");

        Assertions.assertTrue(repo.findByPageTypeAndKioskLocale("sample", "SP").isEmpty());

        Optional<Page> otherPageOpt = repo.findByPageTypeAndKioskLocale("sample", "EN");
        Assertions.assertTrue(otherPageOpt.isPresent());
        
        Page otherPage = otherPageOpt.get();
        Assertions.assertEquals("sample", otherPage.getPageType());
        Assertions.assertEquals(enLocaleId, otherPage.getPageInternals().getKioskLocaleId());
    }

    @Test
    public void givenPageWithNesting_pageExists_True() {
        repo.save(newPageWithComplexNesting);
        repo.save(newPageWithComplexNestingSp);

        Assertions.assertTrue(repo.pageExists("sample", "EN"));
    }

    @Test
    public void givenNoPage_pageExists_False() {
        Assertions.assertFalse(repo.pageExists("sample", "EN"));
    }

    @Test
    public void givenPageWithNoNesting_update_success() {
        repo.save(newPageNoNesting);

        repo.update("about", "EN", newPageNoNestingUpdated);

        Optional<Page> updatedPageOpt = repo.findByPageTypeAndKioskLocale("about", "EN");
        Assertions.assertTrue(updatedPageOpt.isPresent());
        
        Page updatedPage = updatedPageOpt.get();
        
        PageInternals updatedPageInternals = updatedPage.getPageInternals();
        
        Assertions.assertNotNull(updatedPage.getId());
        Assertions.assertEquals(newPageNoNestingUpdated.getPageName(), updatedPage.getPageName());
        Assertions.assertEquals(newPageNoNestingUpdated.getPageType(), updatedPage.getPageType());
        Assertions.assertEquals(newPageNoNestingUpdated.getPageInternals().getKioskLocaleId(), updatedPageInternals.getKioskLocaleId());
        Assertions.assertEquals(newPageNoNestingUpdated.getPageInternals().getPageStatus(), updatedPageInternals.getPageStatus());
        Assertions.assertEquals(newPageNoNestingUpdated.getPageInternals().getCreatedOn(), updatedPageInternals.getCreatedOn());
        Assertions.assertEquals(newPageNoNestingUpdated.getPageInternals().getLastModified(), updatedPageInternals.getLastModified());
        Assertions.assertEquals(newPageNoNestingUpdated.getPageTitleField().getFieldName(), updatedPage.getPageTitleField().getFieldName());
        Assertions.assertEquals(newPageNoNestingUpdated.getPageTitleField().getFieldValue(), updatedPage.getPageTitleField().getFieldValue());
        Assertions.assertTrue(newPageNoNestingUpdated.getFieldContainers().isEmpty());
    }

    @Test
    public void givenNewPageWithNesting_update_success() {

        repo.save(newPageWithComplexNesting);

        repo.update("sample", "EN", newPageWithComplexNestingUpdated);

        Optional<Page> returnedPageOpt = repo.findByPageTypeAndKioskLocale("sample", "EN");
        Assertions.assertTrue(returnedPageOpt.isPresent());
        
        Page returnedPage = returnedPageOpt.get();

        PageInternals returnedPageInternals = returnedPage.getPageInternals();
        List<FieldContainer> returnedFieldContainers = returnedPage.getFieldContainers();
        
        Assertions.assertNotNull(returnedPage.getId());
        Assertions.assertEquals(newPageWithComplexNestingUpdated.getPageInternals().getKioskLocaleId(), returnedPageInternals.getKioskLocaleId());
        Assertions.assertEquals(newPageWithComplexNestingUpdated.getPageTitleField().getFieldName(), returnedPage.getPageTitleField().getFieldName());
        Assertions.assertEquals(newPageWithComplexNestingUpdated.getPageTitleField().getFieldValue(), returnedPage.getPageTitleField().getFieldValue());
        Assertions.assertEquals(newPageWithComplexNestingUpdated.getFieldContainers().size(), returnedFieldContainers.size());



        // Ensure layer 1 field containers were saved correctly
        FieldContainer returnedFieldContainer_1a = returnedFieldContainers.get(0);
        FieldContainer returnedFieldContainer_1b = returnedFieldContainers.get(1);

        Assertions.assertTrue(returnedFieldContainer_1a.getFieldContainerName().equals("upd_Layer1a_FC")|| returnedFieldContainer_1b.getFieldContainerName().equals("upd_Layer1a_FC"));

        if(! returnedFieldContainer_1a.getFieldContainerName().equals("upd_Layer1a_FC")) {
            returnedFieldContainer_1a = returnedFieldContainers.get(1);
            returnedFieldContainer_1b = returnedFieldContainers.get(0);
        }

        // first container
        Assertions.assertEquals(1, returnedFieldContainer_1a.getButtonFields().size());
        Assertions.assertEquals("upd_bf1a1", returnedFieldContainer_1a.getButtonFields().get(0).getFieldName());
        Assertions.assertEquals(false, returnedFieldContainer_1a.getButtonFields().get(0).getFieldValue());
        
        Assertions.assertEquals(1, returnedFieldContainer_1a.getImageFields().size());
        Assertions.assertEquals("upd_image1a1", returnedFieldContainer_1a.getImageFields().get(0).getFieldName());
        Assertions.assertEquals("upd_image1a_path", returnedFieldContainer_1a.getImageFields().get(0).getFieldValue().location());
        Assertions.assertEquals(1, returnedFieldContainer_1a.getImageFields().get(0).getFieldValue().width());
        Assertions.assertEquals(2, returnedFieldContainer_1a.getImageFields().get(0).getFieldValue().height());
        
        Assertions.assertEquals(1, returnedFieldContainer_1a.getRegularTextLongDescriptionFields().size());
        Assertions.assertEquals("upd_regtldf1a1", returnedFieldContainer_1a.getRegularTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("upd_sample regtldf1a1 value", returnedFieldContainer_1a.getRegularTextLongDescriptionFields().get(0).getFieldValue());

        Assertions.assertEquals(1, returnedFieldContainer_1a.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals("upd_rtldf1a", returnedFieldContainer_1a.getRichTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("upd_sample rtldf1a value", returnedFieldContainer_1a.getRichTextLongDescriptionFields().get(0).getFieldValue());
        
        Assertions.assertEquals(1, returnedFieldContainer_1a.getUrlFields().size());
        Assertions.assertEquals("upd_urlf1a1", returnedFieldContainer_1a.getUrlFields().get(0).getFieldName());
        Assertions.assertEquals("upd_url: urlf1a1", returnedFieldContainer_1a.getUrlFields().get(0).getFieldValue());

        Assertions.assertTrue(returnedFieldContainer_1a.getChildContainers().isEmpty());



        // second container
        Assertions.assertEquals("upd_Layer1b_FC", returnedFieldContainer_1b.getFieldContainerName());
        Assertions.assertTrue(returnedFieldContainer_1b.getButtonFields().isEmpty());
        Assertions.assertTrue(returnedFieldContainer_1b.getImageFields().isEmpty());
        Assertions.assertTrue(returnedFieldContainer_1b.getRegularTextLongDescriptionFields().isEmpty());
        Assertions.assertEquals(1, returnedFieldContainer_1b.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals("upd_rtldf1b", returnedFieldContainer_1b.getRichTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("upd_sample rtldf1b value", returnedFieldContainer_1b.getRichTextLongDescriptionFields().get(0).getFieldValue());
        Assertions.assertTrue(returnedFieldContainer_1b.getUrlFields().isEmpty());
        Assertions.assertEquals(1, returnedFieldContainer_1b.getChildContainers().size());



        // Ensure layer 2 field containers were saved correctly
        FieldContainer returnedFieldContainer_2 = returnedFieldContainer_1b.getChildContainers().get(0);
        List<FieldContainer> returnedFieldContainers_2 = returnedFieldContainer_2.getChildContainers();

        Assertions.assertEquals("upd_Layer2_FC", returnedFieldContainer_2.getFieldContainerName());
        Assertions.assertEquals(1, returnedFieldContainer_2.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals("upd_rtldf2", returnedFieldContainer_2.getRichTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("upd_sample rtldf2 value", returnedFieldContainer_2.getRichTextLongDescriptionFields().get(0).getFieldValue());
        Assertions.assertEquals(2, returnedFieldContainers_2.size());



        // Ensure layer 3 field containers were saved correctly
        FieldContainer returnedFieldContainer_3a = returnedFieldContainers_2.get(0);
        FieldContainer returnedFieldContainer_3b = returnedFieldContainers_2.get(1);

        Assertions.assertTrue(returnedFieldContainer_3a.getFieldContainerName().equals("upd_Layer3a_FC") || returnedFieldContainer_3b.getFieldContainerName().equals("upd_Layer3a_FC"));

        if(!returnedFieldContainer_3a.getFieldContainerName().equals("upd_Layer3a_FC")) {
            returnedFieldContainer_3a = returnedFieldContainers_2.get(1);
            returnedFieldContainer_3b = returnedFieldContainers_2.get(0);
        }

        // first container
        Assertions.assertTrue(returnedFieldContainer_3a.getChildContainers().isEmpty());
        Assertions.assertTrue(returnedFieldContainer_3a.getButtonFields().isEmpty());
        Assertions.assertTrue(returnedFieldContainer_3a.getImageFields().isEmpty());
        Assertions.assertTrue(returnedFieldContainer_3a.getRegularTextLongDescriptionFields().isEmpty());
        Assertions.assertTrue(returnedFieldContainer_3a.getRichTextLongDescriptionFields().isEmpty());
        Assertions.assertTrue(returnedFieldContainer_3a.getUrlFields().isEmpty());
        
        // second container
        Assertions.assertEquals("upd_Layer3b_FC", returnedFieldContainer_3b.getFieldContainerName());
        Assertions.assertEquals(2, returnedFieldContainer_3b.getButtonFields().size());
        Assertions.assertEquals(2, returnedFieldContainer_3b.getImageFields().size());
        Assertions.assertEquals(2, returnedFieldContainer_3b.getRegularTextLongDescriptionFields().size());
        Assertions.assertEquals(2, returnedFieldContainer_3b.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals(2, returnedFieldContainer_3b.getUrlFields().size());
        Assertions.assertTrue(returnedFieldContainer_3b.getChildContainers().isEmpty());

    }

    
    /*
     * Builds a page with the following nested structure, where every string is prefixed with the provided
     * prefix ("Layer1aFC" -> prefix + "Layer1aFC")
     * 
     * -page
     *     -fieldContainer ("Layer1a_FC")
     *         -buttonField ("bf1a1_type", "bf1a1", value)
     *         -imageField ("image1a1_type", "image1a1", value)
     *         -regularTextLongDescriptionField ("regtldf1a1_type", "regtldf1a1", value)
     *         -richTextLongDescriptionField ("rtldf1a_type", "rtldf1a", value)
     *         -urlField ("urlf1a1_type", "urlf1a1", value)
     *     -fieldContainer ("Layer1b_FC")
     *         -richTextLongDescriptionField ("rtldf1b_type", "rtldf1b", value)
     *         -fieldContainer ("Layer2_FC")
     *              -richTextLongDescriptionField ("rtldf2_type", "rtldf2", value)
     *              -fieldContainer ("Layer3a_FC")
     *              -fieldContainer ("Layer3b_FC")
     *                   -regularTextLongDescriptionField ("regtldf3b1_type", "regtldf3b1", value)
     *                   -regularTextLongDescriptionField ("regtldf3b2_type", "regtldf3b2", value)
     *                   -imageField ("image3b1_type", "image3b1", value)
     *                   -imageField ("image3b2_type", "image3b2", value)
     *                   -buttonField ("bf3b1_type", "bf3b1", value)
     *                   -buttonField ("bf3b2_type", "bf3b2", value)
     *                   -urlField ("urlf3b1_type", "urlf3b1", value)
     *                   -urlField ("urlf3b2_type", "urlf3b2", value)
     *                   -richTextLongDescriptionField ("rtldf3a_type","rtldf3a", value)
     *                   -richTextLongDescriptionField ("rtldf3b_type", "rtldf3b", value)
     */

    private Page buildPageWithComplexNesting(PageInternals pageInternals, String pageType, String prefix) {
        List<FieldContainer> newPageFieldContainers = new ArrayList<>();

        FieldContainer f1 = new FieldContainer.Builder()
                                .addButtonField(new ButtonField(prefix + "bf1a1_type", prefix + "bf1a1",  false))
                                .fieldContainerName(prefix + "Layer1a_FC")
                                .addImageField(new ImageField(prefix + "image1a1_type", prefix + "image1a1", new Image(prefix + "image1a_path", 1, 2)))
                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(prefix + "regtldf1a1_type", prefix + "regtldf1a1", prefix + "sample regtldf1a1 value"))
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf1a_type", prefix + "rtldf1a", prefix + "sample rtldf1a value"))
                                .addUrlField(new UrlField(prefix + "urlf1a1_type", prefix + "urlf1a1", prefix + "url: urlf1a1"))
                                .build();

        FieldContainer f2 = new FieldContainer.Builder()
                                .fieldContainerName(prefix + "Layer3a_FC")
                                .build();

        FieldContainer f3 = new FieldContainer.Builder()
                                .addButtonField(new ButtonField(prefix + "bf3b1_type", prefix + "bf3b1",  false))
                                .addButtonField(new ButtonField(prefix + "bf3b2_type", prefix + "bf3b2",  true))
                                .fieldContainerName(prefix + "Layer3b_FC")
                                .addImageField(new ImageField(prefix + "image3b1_type", prefix + "image3b1", new Image(prefix + "image3b1_path", 1, 2)))
                                .addImageField(new ImageField(prefix + "image3b2_type", prefix + "image3b2", new Image(prefix + "image3b2_path", 3, 4)))
                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(prefix + "regtldf3b1_type", prefix + "regtldf3b1", prefix + "sample regtldf3b1 value"))
                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(prefix + "regtldf3b2_type", prefix + "regtldf3b2", prefix + "sample regtldf3b1 value"))
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf3a_type", prefix + "rtldf3a", prefix + "sample rtldf3a value"))
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf3b_type", prefix + "rtldf3b", prefix + "sample rtldf3b value"))
                                .addUrlField(new UrlField(prefix + "urlf3b1_type", prefix + "urlf3b1", prefix + "url: urlf3b1"))
                                .addUrlField(new UrlField(prefix + "urlf3b2_type", prefix + "urlf3b2", prefix + "url: urlf3b2"))
                                .build();

        FieldContainer f4 = new FieldContainer.Builder()
                                .fieldContainerName(prefix + "Layer2_FC")
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf2_type", prefix + "rtldf2", prefix + "sample rtldf2 value"))
                                .addChildContainer(f2)
                                .addChildContainer(f3)
                                .build();

        FieldContainer f5 = new FieldContainer.Builder()
                                .fieldContainerName(prefix + "Layer1b_FC")
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf1b_type", prefix + "rtldf1b", prefix + "sample rtldf1b value"))
                                .addChildContainer(f4)
                                .build();

        newPageFieldContainers.add(f1);
        newPageFieldContainers.add(f5);

        return new Page.Builder()
                        .pageType(pageType)
                        .pageName(pageType + "_page")
                        .pageInternals(pageInternals)
                        .title(pageType + "_page_title")
                        .fieldContainers(newPageFieldContainers)
                        .build();

    }


    class IdRowMapper implements RowMapper<Long>{
    
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        
            return rs.getLong("id");

        }
    }
    
}
