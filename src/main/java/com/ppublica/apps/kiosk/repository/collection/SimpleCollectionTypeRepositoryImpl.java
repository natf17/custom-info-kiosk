package com.ppublica.apps.kiosk.repository.collection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

import static com.ppublica.apps.kiosk.repository.collection.SQLStatements.*;

@Repository
public class SimpleCollectionTypeRepositoryImpl implements SimpleCollectionTypeRepository {

    @Autowired
    private JdbcTemplate template;

    @Override
    public SimpleCollectionType saveInstance(SimpleCollectionType collectionInstance) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 1. Insert into simple_collection_type_impl table
        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SIMPLE_COLL_TYPE_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, collectionInstance.getType());

            return ps;
        }, keyHolder);

        Long savedSimpleCollectionTypeId = getKey(keyHolder);
        SimpleCollectionType savedSimpleCollectionType = collectionInstance.withId(savedSimpleCollectionTypeId);


        // 2. Insert CollectionInternals
        CollectionInternals collectionInternals = savedSimpleCollectionType.getCollectionInternals();
        this.template.update(INSERT_COLL_INTERNALS_TABLE,
                            collectionInternals.getKioskLocaleId(),
                            collectionInternals.getCreatedOn(),
                            collectionInternals.getLastModified(), 
                            collectionInternals.getStatus().toString(),
                            savedSimpleCollectionTypeId);
            

        // 3. Insert CollectionNameField
        CollectionNameField collNameField = savedSimpleCollectionType.getCollectionNameField();
        this.template.update(INSERT_COLL_NAME_FIELD_TABLE,
                                collNameField.getFieldType(),
                                collNameField.getFieldName(),
                                collNameField.getFieldValue(),
                                savedSimpleCollectionTypeId);

        // 4. Insert TextFields
        List<TextField> textFields = savedSimpleCollectionType.getTextFields();
        for (TextField textField : textFields) {
            this.template.update(INSERT_TEXT_FIELD_TABLE,
                                textField.getFieldType(),
                                textField.getFieldName(),
                                textField.getFieldValue(),
                                savedSimpleCollectionTypeId);
        }
        
        // 5. Insert NumericFields
        List<NumericField> numericFields = savedSimpleCollectionType.getNumericFields();
        for (NumericField numericField : numericFields) {
            this.template.update(INSERT_NUMERIC_FIELD_TABLE,
                                numericField.getFieldType(),
                                numericField.getFieldName(),
                                numericField.getFieldValue(),
                                savedSimpleCollectionTypeId);
        }

        // 6. Insert BooleanFields
        List<BooleanField> booleanFields = savedSimpleCollectionType.getBooleanFields();
        for (BooleanField booleanField : booleanFields) {
            this.template.update(INSERT_BOOLEAN_FIELD_TABLE,
                                booleanField.getFieldType(),
                                booleanField.getFieldName(),
                                booleanField.getFieldValue(),
                                savedSimpleCollectionTypeId);
        }

        // 7. Insert ImageFields
        List<ImageField> imageFields = savedSimpleCollectionType.getImageFields();
        KeyHolder keyHolderIm = new GeneratedKeyHolder();

        Long currentImageFieldId = null;
        Image currentImage = null;
        for (ImageField imageField : imageFields) {
            
            this.template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_IMAGE_FIELD_TABLE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, imageField.getFieldType());
                ps.setString(2, imageField.getFieldName());
                ps.setLong(3, savedSimpleCollectionTypeId);

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

        // 8. Insert LinkedCollectionFields
        List<LinkedCollectionField> linkedCollectionFields = savedSimpleCollectionType.getLinkedCollectionFields();
        for (LinkedCollectionField linkedCollectionField : linkedCollectionFields) {
            this.template.update(INSERT_LINKEDCOLL_FIELD_TABLE,
                                linkedCollectionField.getFieldType(),
                                linkedCollectionField.getFieldName(),
                                linkedCollectionField.getFieldValue(),
                                savedSimpleCollectionTypeId);
        }

        return savedSimpleCollectionType;
    }

    @Override
    public List<SimpleCollectionType> findByCollectionTypeAndLocale(String collectionType, String localeAbbrev) {
        List<SimpleCollectionType> simpleCollectionTypes = new ArrayList<>();
        
        List<SimpleCollectionTypeQueryResults> simpleCollectionTypeResults = findShallowByCollectionTypeAndLocale(collectionType, localeAbbrev);

        for(SimpleCollectionTypeQueryResults simpleCollectionTypeResult : simpleCollectionTypeResults) {
            simpleCollectionTypes.add(getSimpleCollectionType(simpleCollectionTypeResult));
        }

        return simpleCollectionTypes;
    }

    private SimpleCollectionType getSimpleCollectionType(SimpleCollectionTypeQueryResults simpleCollectionTypeResult) {

        Long collectionTypeId = simpleCollectionTypeResult.getId();
        // Get TextFields
        List<TextField> textFields = template.query(FIND_TEXT_FIELD_TABLE, new TextFieldRowMapper(), collectionTypeId);

        // Get NumericFields
        List<NumericField> numericFields = template.query(FIND_NUMERIC_FIELD_TABLE, new NumericFieldRowMapper(), collectionTypeId);

        // Get BooleanFields
        List<BooleanField> booleanFields = template.query(FIND_BOOLEAN_FIELD_TABLE, new BooleanFieldRowMapper(), collectionTypeId);

        // Get ImageFields
        List<ImageField> imageFields = template.query(FIND_IMAGE_FIELD_TABLE, new ImageQueryRowMapper(), collectionTypeId);

        // Get LinkedCollectionFields
        List<LinkedCollectionField> linkedCollectionFields = template.query(FIND_LINKEDCOLL_FIELD_TABLE, new LinkedCollectionFieldQueryRowMapper(), collectionTypeId);
        
        return new SimpleCollectionTypeQueryResultsAdapter(simpleCollectionTypeResult, textFields, numericFields, booleanFields, imageFields, linkedCollectionFields);
    }

    @Override
    public void deleteCollectionTypeInstancesWithLocale(String collectionType, String localeAbbrev) {
       
        List<SimpleCollectionTypeQueryResults> simpleCollectionTypeResults = findShallowByCollectionTypeAndLocale(collectionType, localeAbbrev);
        
        for (SimpleCollectionTypeQueryResults simpleCollectionTypeResult : simpleCollectionTypeResults) {
            this.template.update(DELETE_SIMPLE_COLL_TYPE_TABLE, simpleCollectionTypeResult.getId());
        }

    }

    @Override
    public void deleteCollectionInstance(Long id) {
       
        this.template.update(DELETE_SIMPLE_COLL_TYPE_TABLE, id);

    }

    @Override
    public boolean doesInstanceOfCollectionExist(String collectionType, String localeAbbrev) {
        
        return !findShallowByCollectionTypeAndLocale(collectionType, localeAbbrev).isEmpty();
    }

    @Override
    public SimpleCollectionType updateInstance(Long id, SimpleCollectionType collectionInstance) {

        deleteCollectionInstance(id);

        return saveInstance(collectionInstance);
    }

    private Long getKey(KeyHolder key) {
        Number keyValue = key.getKey();
        if(keyValue == null) {
            throw new RuntimeException("Unable to obtain id");
        }

        return keyValue.longValue();
    }

    private List<SimpleCollectionTypeQueryResults> findShallowByCollectionTypeAndLocale(String collectionType, String localeAbbrev) {
        List<SimpleCollectionTypeQueryResults> simpleCollectionTypeResults = this.template.query(FIND_SIMPLE_COLL_TYPE_TABLE, new SimpleCollectionTypeQueryRowMapper(), localeAbbrev, collectionType);
        
        return simpleCollectionTypeResults;
    }

    
}
