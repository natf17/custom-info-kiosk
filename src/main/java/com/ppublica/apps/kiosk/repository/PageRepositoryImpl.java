package com.ppublica.apps.kiosk.repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;

import static com.ppublica.apps.kiosk.repository.SQLStatements.*;


public class PageRepositoryImpl implements PageRepository {
    @Autowired
    private JdbcTemplate template;

    @Override
    public Page save(Page page) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 1. Insert into page table
        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_PAGE_TABLE);
            ps.setString(1, page.getPageType());
            ps.setString(2, page.getPageName());

            return ps;
        }, keyHolder);
        
        page.withId((Long)keyHolder.getKey());


        // 2. Insert PageInternals
        PageInternals pageInternals = page.getPageInternals();
        this.template.update(INSERT_PAGE_INTERNALS_TABLE,
                            pageInternals.getKioskLocaleId(),
                            pageInternals.getCreatedOn(),
                            pageInternals.getLastModified(), 
                            pageInternals.getPageStatus().toString(),
                            page.getId()
        );
            

        // 3. Insert PageTitleField
        PageTitleField pTitleField = page.getPageTitleField();
        this.template.update(INSERT_PAGE_TITLE_FIELD_TABLE,
                                pTitleField.getFieldName(),
                                pTitleField.getFieldValue(),
                                page.getId()
        );

        // 4. Insert field containers
        this.insertFieldContainers(page.getFieldContainers(), page.getId(), false);
        
        return page;
    }

    @Override
    public Page findByPageTypeAndKioskLocale(String pageType, String localeAbbrev) {
        // get all pages that match pageType and locale, obtaining pageInternals along the way

        Page page = this.template.queryForObject(FIND_PAGE_TABLE, new PageQueryRowMapper(), pageType, localeAbbrev);
        
        if (page == null) {
            return null;
        }
        // get the page title field that matches
        page = page.withTitleField(template.queryForObject(FIND_PAGE_TITLE_FIELD_TABLE, new PageTitleFieldQueryRowMapper(), page.getId()));

        // get page containers
        List<FieldContainer> fieldContainers = getFieldContainers(page.getId(), false);

        page = page.withFieldContainers(fieldContainers);

        return page;
    }


    private void insertFieldContainers(List<FieldContainer> fieldContainers, Long parentId, boolean hasContainerParent) {
        
        for (FieldContainer fieldContainer : fieldContainers) {
            this.insertFieldContainer(fieldContainer, parentId, hasContainerParent);
        }
    }

    private void insertFieldContainer(FieldContainer fieldContainer, Long parentId, boolean hasContainerParent) {
        // insert the container
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_FIELD_CONTAINER_TABLE);
            ps.setString(1, fieldContainer.getFieldContainerName());
            ps.setBoolean(2, fieldContainer.hasNestedContainer());
            ps.setBoolean(4, hasContainerParent);
            ps.setLong(5, parentId);

            return ps;

        }, keyHolder);

        Long currentContainerId = (Long)keyHolder.getKey();

        // Insert RichTextLongDescriptionFields
        List<RichTextLongDescriptionField> richTextLongDescriptionFields = fieldContainer.getRichTextLongDescriptionFields();
        for (RichTextLongDescriptionField richTextLongDescr : richTextLongDescriptionFields) {
            this.template.update(INSERT_RICHTEXTLONGDESCR_FIELD_TABLE,
                                richTextLongDescr.getFieldName(),
                                richTextLongDescr.getFieldValue(),
                                currentContainerId);
        }

        // TODO: Insert RegularTextLongDescriptionFields

        // TODO: Insert ImageFields

        // TODO: Insert ButtonFields

        // TODO: Insert UrlFields

        // insert any nested containers
        if(!fieldContainer.hasNestedContainer()) {
            return;
        }

        this.insertFieldContainers(fieldContainer.getChildContainers(), currentContainerId, true);

    }

    private List<FieldContainer> getFieldContainers(Long parentId, boolean isParentAContainer) {
        List<FieldContainerQueryResults> fieldContainerResults = this.template.query(FIND_FIELD_CONTAINER_TABLE, new FieldContainerQueryRowMapper(), isParentAContainer, parentId);
        List<FieldContainer> fieldContainers = new ArrayList<>();

        for(FieldContainerQueryResults fieldContainerResult : fieldContainerResults) {
            fieldContainers.add(getFieldContainer(fieldContainerResult, parentId, isParentAContainer));
        }

        return null;
    }

    private FieldContainer getFieldContainer(FieldContainerQueryResults fieldContainerResults, Long parentId, boolean isParentAContainer) {
        FieldContainer.Builder fieldContainerBuilder = new FieldContainer.Builder()
                                                        .withId(fieldContainerResults.getId())
                                                        .fieldContainerName(fieldContainerResults.getContainerName());

        List<RichTextLongDescriptionField> richTextLongDescriptionFields = template.query(FIND_RICHTEXTLONGDESCR_FIELD_TABLE, 
                                                                                new RichTextLongDescriptionFieldRowMapper(),
                                                                                fieldContainerResults.getId());
        fieldContainerBuilder.richTextLongDescriptionFields(richTextLongDescriptionFields);

        // TODO: get RegularTextLongDescriptionField
        List<RegularTextLongDescriptionField> regularTextLongDescriptionFields = null;
        fieldContainerBuilder.regularTextLongDescriptionFields(regularTextLongDescriptionFields);

        // TODO: get ImageField
        List<ImageField> imageFields = null;
        fieldContainerBuilder.imageFields(imageFields);

        // TODO: get ButtonField
        List<ButtonField> buttonFields = null;
        fieldContainerBuilder.buttonFields(buttonFields);

        // TODO: get UrlField
        List<UrlField> urlFields = null;
        fieldContainerBuilder.urlFields(urlFields);

        // TODO: get nested containers
        List<FieldContainer> nestedContainers = new ArrayList<>();

        if(fieldContainerResults.hasNestedContainer()) {
            nestedContainers = getFieldContainers(fieldContainerResults.getId(), true);
        }

        fieldContainerBuilder.childContainers(nestedContainers);

        return fieldContainerBuilder.build();
    }
    
}
