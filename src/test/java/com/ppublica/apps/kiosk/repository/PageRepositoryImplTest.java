package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        Page savedPage = repo.findByPageTypeAndKioskLocale("about", "EN");
        PageInternals savedPageInternals = savedPage.getPageInternals();
        
        Assertions.assertNotNull(savedPage.getId());
        Assertions.assertEquals(newPageNoNesting.getPageName(), savedPage.getPageName());
        Assertions.assertEquals(newPageNoNesting.getPageType(), savedPage.getPageType());
        Assertions.assertEquals(newPageNoNesting.getPageInternals().getKioskLocaleId(), savedPageInternals.getKioskLocaleId());
        Assertions.assertEquals(newPageNoNesting.getPageInternals().getPageStatus(), savedPageInternals.getPageStatus());
        Assertions.assertEquals(newPageNoNesting.getPageInternals().getCreatedOn(), savedPageInternals.getCreatedOn());
        Assertions.assertEquals(newPageNoNesting.getPageInternals().getLastModified(), savedPageInternals.getLastModified());
        Assertions.assertEquals(newPageNoNesting.getPageTitleField().getFieldName(), savedPage.getPageTitleField().getFieldName());
        Assertions.assertEquals(newPageNoNesting.getPageTitleField().getFieldValue(), savedPage.getPageTitleField().getFieldValue());
        Assertions.assertTrue(newPageNoNesting.getFieldContainers().isEmpty());                 

    }
    
    @Test
    public void givenNewPageWithNesting_saveAndRead_success() {

        repo.save(newPageWithComplexNesting);

        Page savedPage = repo.findByPageTypeAndKioskLocale("sample", "EN");
        PageInternals savedPageInternals = savedPage.getPageInternals();
        List<FieldContainer> savedFieldContainers = savedPage.getFieldContainers();
        
        Assertions.assertNotNull(savedPage.getId());
        Assertions.assertEquals(newPageWithComplexNesting.getPageInternals().getKioskLocaleId(), savedPageInternals.getKioskLocaleId());
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
        Assertions.assertEquals("image1a1", savedFieldContainer_1a.getImageFields().get(0).getFieldName());
        Assertions.assertEquals("image1a_path", savedFieldContainer_1a.getImageFields().get(0).getFieldValue().location());
        Assertions.assertEquals(1, savedFieldContainer_1a.getImageFields().get(0).getFieldValue().width());
        Assertions.assertEquals(2, savedFieldContainer_1a.getImageFields().get(0).getFieldValue().height());
        
        Assertions.assertEquals(1, savedFieldContainer_1a.getRegularTextLongDescriptionFields().size());
        Assertions.assertEquals("regtldf1a1", savedFieldContainer_1a.getRegularTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("sample regtldf1a1 value", savedFieldContainer_1a.getRegularTextLongDescriptionFields().get(0).getFieldValue());

        Assertions.assertEquals(1, savedFieldContainer_1a.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals("rtldf1a", savedFieldContainer_1a.getRichTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("sample rtldf1a value", savedFieldContainer_1a.getRichTextLongDescriptionFields().get(0).getFieldValue());
        
        Assertions.assertEquals(1, savedFieldContainer_1a.getUrlFields().size());
        Assertions.assertEquals("urlf1a1", savedFieldContainer_1a.getUrlFields().get(0).getFieldName());
        Assertions.assertEquals("url: urlf1a1", savedFieldContainer_1a.getUrlFields().get(0).getFieldValue());

        Assertions.assertTrue(savedFieldContainer_1a.getChildContainers().isEmpty());



        // second container
        Assertions.assertEquals("Layer1b_FC", savedFieldContainer_1b.getFieldContainerName());
        Assertions.assertTrue(savedFieldContainer_1b.getButtonFields().isEmpty());
        Assertions.assertTrue(savedFieldContainer_1b.getImageFields().isEmpty());
        Assertions.assertTrue(savedFieldContainer_1b.getRegularTextLongDescriptionFields().isEmpty());
        Assertions.assertEquals(1, savedFieldContainer_1b.getRichTextLongDescriptionFields().size());
        Assertions.assertEquals("rtldf1b", savedFieldContainer_1b.getRichTextLongDescriptionFields().get(0).getFieldName());
        Assertions.assertEquals("sample rtldf1b value", savedFieldContainer_1b.getRichTextLongDescriptionFields().get(0).getFieldValue());
        Assertions.assertTrue(savedFieldContainer_1b.getUrlFields().isEmpty());
        Assertions.assertEquals(1, savedFieldContainer_1b.getChildContainers().size());



        // Ensure layer 2 field containers were saved correctly
        FieldContainer savedFieldContainer_2 = savedFieldContainer_1b.getChildContainers().get(0);
        List<FieldContainer> savedFieldContainers_2 = savedFieldContainer_2.getChildContainers();

        Assertions.assertEquals("Layer2_FC", savedFieldContainer_2.getFieldContainerName());
        Assertions.assertEquals(1, savedFieldContainer_2.getRichTextLongDescriptionFields().size());
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

        Assertions.assertThrows(RuntimeException.class, () -> {
            repo.findByPageTypeAndKioskLocale("about", "SP");
        });

        Page otherPage = repo.findByPageTypeAndKioskLocale("about", "EN");
        Assertions.assertEquals("about", otherPage.getPageType());
        Assertions.assertEquals(enLocaleId, otherPage.getPageInternals().getKioskLocaleId());

    }

    @Test
    public void givenPageWithNesting_saveAndDeletePageLocale_success() {
        repo.save(newPageWithComplexNesting);
        repo.save(newPageWithComplexNestingSp);
        repo.deletePageWithLocale("sample", "SP");

        Assertions.assertThrows(RuntimeException.class, () -> {
            repo.findByPageTypeAndKioskLocale("sample", "SP");
        });

        Page otherPage = repo.findByPageTypeAndKioskLocale("sample", "EN");
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

        Page updatedPage = repo.findByPageTypeAndKioskLocale("about", "EN");
        
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

        Page returnedPage = repo.findByPageTypeAndKioskLocale("sample", "EN");
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
     *         -buttonField ("bf1a1")
     *         -imageField ("image1a1")
     *         -regularTextLongDescriptionField ("regtldf1a1")
     *         -richTextLongDescriptionField ("rtldf1a")
     *         -urlField ("urlf1a1")
     *     -fieldContainer ("Layer1b_FC")
     *         -richTextLongDescriptionField ("rtldf1b")
     *         -fieldContainer ("Layer2_FC")
     *              -richTextLongDescriptionField ("rtldf2")
     *              -fieldContainer ("Layer3a_FC")
     *              -fieldContainer ("Layer3b_FC")
     *                   -regularTextLongDescriptionField ("regtldf3b1")
     *                   -regularTextLongDescriptionField ("regtldf3b2")
     *                   -imageField ("image3b1")
     *                   -imageField ("image3b2")
     *                   -buttonField ("bf3b1")
     *                   -buttonField ("bf3b2")
     *                   -urlField ("urlf3b1")
     *                   -urlField ("urlf3b2")
     *                   -richTextLongDescriptionField ("rtldf3a")
     *                   -richTextLongDescriptionField ("rtldf3b")
     */

    private Page buildPageWithComplexNesting(PageInternals pageInternals, String pageType, String prefix) {
        List<FieldContainer> newPageFieldContainers = new ArrayList<>();

        FieldContainer f1 = new FieldContainer.Builder()
                                .addButtonField(new ButtonField(prefix + "bf1a1",  false))
                                .fieldContainerName(prefix + "Layer1a_FC")
                                .addImageField(new ImageField(prefix + "image1a1", new Image(prefix + "image1a_path", 1, 2)))
                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(prefix + "regtldf1a1", prefix + "sample regtldf1a1 value"))
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf1a", prefix + "sample rtldf1a value"))
                                .addUrlField(new UrlField(prefix + "urlf1a1", prefix + "url: urlf1a1"))
                                .build();

        FieldContainer f2 = new FieldContainer.Builder()
                                .fieldContainerName(prefix + "Layer3a_FC")
                                .build();

        FieldContainer f3 = new FieldContainer.Builder()
                                .addButtonField(new ButtonField(prefix + "bf3b1",  false))
                                .addButtonField(new ButtonField(prefix + "bf3b2",  true))
                                .fieldContainerName(prefix + "Layer3b_FC")
                                .addImageField(new ImageField(prefix + "image3b1", new Image(prefix + "image3b1_path", 1, 2)))
                                .addImageField(new ImageField(prefix + "image3b2", new Image(prefix + "image3b2_path", 3, 4)))
                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(prefix + "regtldf3b1", prefix + "sample regtldf3b1 value"))
                                .addRegularTextLongDescriptionField(new RegularTextLongDescriptionField(prefix + "regtldf3b2", prefix + "sample regtldf3b1 value"))
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf3a", prefix + "sample rtldf3a value"))
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf3b", prefix + "sample rtldf3b value"))
                                .addUrlField(new UrlField(prefix + "urlf3b1", prefix + "url: urlf3b1"))
                                .addUrlField(new UrlField(prefix + "urlf3b2", prefix + "url: urlf3b2"))
                                .build();

        FieldContainer f4 = new FieldContainer.Builder()
                                .fieldContainerName(prefix + "Layer2_FC")
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf2", prefix + "sample rtldf2 value"))
                                .addChildContainer(f2)
                                .addChildContainer(f3)
                                .build();

        FieldContainer f5 = new FieldContainer.Builder()
                                .fieldContainerName(prefix + "Layer1b_FC")
                                .addRichTextLongDescriptionField(new RichTextLongDescriptionField(prefix + "rtldf1b", prefix + "sample rtldf1b value"))
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
