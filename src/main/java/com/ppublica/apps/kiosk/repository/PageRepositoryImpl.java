package com.ppublica.apps.kiosk.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;

import static com.ppublica.apps.kiosk.repository.SQLStatements.*;

@Repository
public class PageRepositoryImpl implements PageRepository {
    @Autowired
    private JdbcTemplate template;

    @Override
    public Page save(Page page) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 1. Insert into page table
        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_PAGE_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, page.getPageType());
            ps.setString(2, page.getPageName());

            return ps;
        }, keyHolder);

        Page savedPage = page.withId(getKey(keyHolder));


        // 2. Insert PageInternals
        PageInternals pageInternals = savedPage.getPageInternals();
        this.template.update(INSERT_PAGE_INTERNALS_TABLE,
                            pageInternals.getKioskLocaleId(),
                            pageInternals.getCreatedOn(),
                            pageInternals.getLastModified(), 
                            pageInternals.getPageStatus().toString(),
                            savedPage.getId()
        );
            

        // 3. Insert PageTitleField
        PageTitleField pTitleField = savedPage.getPageTitleField();
        this.template.update(INSERT_PAGE_TITLE_FIELD_TABLE,
                                pTitleField.getFieldType(),
                                pTitleField.getFieldName(),
                                pTitleField.getFieldValue(),
                                savedPage.getId()
        );

        // 4. Insert field containers
        this.insertFieldContainers(savedPage.getFieldContainers(), savedPage.getId(), false);
        
        return savedPage;
    }

    @Override
    public Optional<Page> findByPageTypeAndKioskLocale(String pageType, String localeAbbrev) {
        
        Page page = null;
        try {
            page = this.template.queryForObject(FIND_PAGE_TABLE, new PageQueryRowMapper(), localeAbbrev, pageType);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        // get the page title field that matches
        page = page.withTitleField(template.queryForObject(FIND_PAGE_TITLE_FIELD_TABLE, new PageTitleFieldQueryRowMapper(), page.getId()));

        // get page containers
        List<FieldContainer> fieldContainers = getFieldContainers(page.getId(), false);

        page = page.withFieldContainers(fieldContainers);

        return Optional.of(page);
    }


    private void insertFieldContainers(List<FieldContainer> fieldContainers, Long parentId, boolean hasContainerParent) {
        
        for (FieldContainer fieldContainer : fieldContainers) {
            this.insertFieldContainer(fieldContainer, parentId, hasContainerParent);
        }
    }

    private void insertFieldContainer(FieldContainer fieldContainer, Long parentId, boolean hasContainerParent) {
        // Insert the container
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_FIELD_CONTAINER_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, fieldContainer.getFieldContainerName());
            ps.setBoolean(2, fieldContainer.hasNestedContainer());
            ps.setBoolean(3, hasContainerParent);
            ps.setLong(4, parentId);

            return ps;

        }, keyHolder);

        Long currentContainerId = getKey(keyHolder);

        // Insert RichTextLongDescriptionFields
        List<RichTextLongDescriptionField> richTextLongDescriptionFields = fieldContainer.getRichTextLongDescriptionFields();
        for (RichTextLongDescriptionField richTextLongDescr : richTextLongDescriptionFields) {
            this.template.update(INSERT_RICHTEXTLONGDESCR_FIELD_TABLE,
                                richTextLongDescr.getFieldType(),
                                richTextLongDescr.getFieldName(),
                                richTextLongDescr.getFieldValue(),
                                currentContainerId);
        }

        // Insert RegularTextLongDescriptionFields
        List<RegularTextLongDescriptionField> regTextLongDescriptionFields = fieldContainer.getRegularTextLongDescriptionFields();
        for (RegularTextLongDescriptionField regTextLongDescr : regTextLongDescriptionFields) {
            this.template.update(INSERT_REGTEXTLONGDESCR_FIELD_TABLE,
                                regTextLongDescr.getFieldType(),
                                regTextLongDescr.getFieldName(),
                                regTextLongDescr.getFieldValue(),
                                currentContainerId);
        }

        // Insert ImageFields
        List<ImageField> imageFields = fieldContainer.getImageFields();
        KeyHolder keyHolderIm = new GeneratedKeyHolder();

        Long currentImageFieldId = null;
        Image currentImage = null;
        for (ImageField imageField : imageFields) {
            
            this.template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_IMAGE_FIELD_TABLE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, imageField.getFieldType());
                ps.setString(2, imageField.getFieldName());
                ps.setLong(3, currentContainerId);

                return ps;
            }, keyHolderIm);

            currentImageFieldId = getKey(keyHolderIm);
            currentImage = imageField.getFieldValue();

            if(currentImage != null) {
                this.template.update(INSERT_IMAGE_TABLE,
                                currentImage.location(),
                                currentImage.width(),
                                currentImage.height(),
                                currentImageFieldId);
            }
        
        }

        //  Insert ButtonFields
        List<ButtonField> buttonFields = fieldContainer.getButtonFields();
        for (ButtonField buttonField : buttonFields) {
            this.template.update(INSERT_BUTTON_FIELD_TABLE,
                                buttonField.getFieldType(),
                                buttonField.getFieldName(),
                                buttonField.getFieldValue(),
                                currentContainerId);
        }

        // Insert UrlFields
        List<UrlField> urlFields = fieldContainer.getUrlFields();
        for (UrlField urlField : urlFields) {
            this.template.update(INSERT_URL_FIELD_TABLE,
                                urlField.getFieldType(),
                                urlField.getFieldName(),
                                urlField.getFieldValue(),
                                currentContainerId);
        }

        // Insert any nested containers
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

        return fieldContainers;
    }

    private FieldContainer getFieldContainer(FieldContainerQueryResults fieldContainerResults, Long parentId, boolean isParentAContainer) {
        FieldContainer.Builder fieldContainerBuilder = new FieldContainer.Builder()
                                                        //.withId(fieldContainerResults.getId())
                                                        .fieldContainerName(fieldContainerResults.getContainerName());

        List<RichTextLongDescriptionField> richTextLongDescriptionFields = template.query(FIND_RICHTEXTLONGDESCR_FIELD_TABLE, 
                                                                                new RichTextLongDescriptionFieldRowMapper(),
                                                                                fieldContainerResults.getId());
        fieldContainerBuilder.richTextLongDescriptionFields(richTextLongDescriptionFields);

        // Get RegularTextLongDescriptionField
        List<RegularTextLongDescriptionField> regularTextLongDescriptionFields = template.query(FIND_REGTEXTLONGDESCR_FIELD_TABLE, 
                                                                                new RegularTextLongDescriptionFieldRowMapper(),
                                                                                fieldContainerResults.getId());
        fieldContainerBuilder.regularTextLongDescriptionFields(regularTextLongDescriptionFields);

        // Get ImageField
        List<ImageField> imageFields = template.query(FIND_IMAGE_FIELD_TABLE, 
                                                            new ImageQueryRowMapper(),
                                                            fieldContainerResults.getId());
        
        fieldContainerBuilder.imageFields(imageFields);

        // Get ButtonField
        List<ButtonField> buttonFields = template.query(FIND_BUTTON_FIELD_TABLE,
                                                            new ButtonFieldRowMapper(),
                                                            fieldContainerResults.getId());
        
        fieldContainerBuilder.buttonFields(buttonFields);

        // Get UrlField
        List<UrlField> urlFields = template.query(FIND_URL_FIELD_TABLE,
                                                    new UrlFieldRowMapper(),
                                                    fieldContainerResults.getId());
        fieldContainerBuilder.urlFields(urlFields);

        // Get nested containers
        List<FieldContainer> nestedContainers = new ArrayList<>();

        if(fieldContainerResults.hasNestedContainer()) {
            nestedContainers = getFieldContainers(fieldContainerResults.getId(), true);
        }

        fieldContainerBuilder.childContainers(nestedContainers);

        return fieldContainerBuilder.build();
    }

    private Long getKey(KeyHolder key) {
        Number keyValue = key.getKey();
        if(keyValue == null) {
            throw new RuntimeException("Unable to obtain id");
        }

        return keyValue.longValue();
    }

    

    @Override
    public void deletePageWithLocale(String pageType, String localeAbbrev) {

        /* 1. The field containers must be deleted manually.
         *    All child containers will be deleted before the parent.
         *    To accomplish this, first we obtain the pageId, then we use it to aggregate 
         *    all the ids in increasing "level" order, and then delete them in LIFO fashion.
         * 
         * Deleting the field containers will automatically trigger a delete for all child fields.
         */
        LinkedList<Long> fcIdAggregator = new LinkedList<>();
        List<FieldContainerQueryResults> currentLevelFCs;
        List<FieldContainerQueryResults> nextLevelFCs;
        List<FieldContainerQueryResults> nestedFCs;

        Page page = this.template.queryForObject(FIND_PAGE_TABLE, new PageQueryRowMapper(), localeAbbrev, pageType);
        
        if (page == null) {
            throw new RuntimeException("Delete failed: page not found");
        }
        
        // get first level of field containers
        currentLevelFCs = this.template.query(FIND_FIELD_CONTAINER_TABLE, new FieldContainerQueryRowMapper(), false, page.getId());

        // process the field containers one level at a time, aggregating their ids
        while(!currentLevelFCs.isEmpty()) {
            nextLevelFCs = new ArrayList<>();
            for (FieldContainerQueryResults fc : currentLevelFCs) {
                fcIdAggregator.add(fc.getId());
                
                if(fc.hasNestedContainer()) {
                    nestedFCs = this.template.query(FIND_FIELD_CONTAINER_TABLE, new FieldContainerQueryRowMapper(), false, fc.getId());
                    nextLevelFCs.addAll(nestedFCs);
                }
            }

            currentLevelFCs = nextLevelFCs;  
            
        }

        Iterator<Long> descendingIterator = fcIdAggregator.descendingIterator();
        Long currentFCId;

        while(descendingIterator.hasNext()) {
            currentFCId = descendingIterator.next();
            this.template.update(DELETE_FIELD_CONTAINER_TABLE, currentFCId);
        }


        /*
         * 2. Delete the page, which will also delete the associated page_internals and page_title_field
         */

        this.template.update(DELETE_PAGE_TABLE, page.getId());
        
    }

    @Override
    public boolean pageExists(String pageType, String localeAbbrev) {

        try {
            this.template.queryForObject(FIND_PAGE_TABLE, new PageQueryRowMapper(), localeAbbrev, pageType);
            return true;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        
    }

    @Override
    public Page update(String pageType, String localeAbbrev, Page page) {
        
        deletePageWithLocale(pageType, localeAbbrev);
        
        return save(page);
        
    }

}
