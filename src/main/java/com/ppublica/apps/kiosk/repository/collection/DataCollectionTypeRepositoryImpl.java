package com.ppublica.apps.kiosk.repository.collection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElement;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElementImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;

import static com.ppublica.apps.kiosk.repository.collection.SQLStatements.*;

public class DataCollectionTypeRepositoryImpl implements DataCollectionTypeRepository {

    @Autowired
    private JdbcTemplate template;

    @Override
    public DataCollectionType saveCollectionInstance(DataCollectionType collectionInstance) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 1. Insert into simple_collection_type_impl table
        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_DATA_COLL_TYPE_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, collectionInstance.getType());
            ps.setString(2, collectionInstance.getSubType());

            return ps;
        }, keyHolder);

        Long savedDataCollectionTypeId = getKey(keyHolder);
        DataCollectionType savedDataCollectionType = collectionInstance.withId(savedDataCollectionTypeId);


        // 2. Insert CollectionInternals
        CollectionInternals collectionInternals = savedDataCollectionType.getCollectionInternals();
        this.template.update(INSERT_DATA_COLL_INTERNALS_TABLE,
                            collectionInternals.getKioskLocaleId(),
                            collectionInternals.getCreatedOn(),
                            collectionInternals.getLastModified(), 
                            collectionInternals.getStatus().toString(),
                            savedDataCollectionTypeId);

                
        // 3. Insert LocalizedFields
        LocalizedFields localizedFields = savedDataCollectionType.getLocalizedFields();

        insertCollectionLocalizedFields(savedDataCollectionTypeId, localizedFields);
        
        // 4. Insert DataCollectionElements
        List<DataCollectionElement> dataCollectionElements = savedDataCollectionType.getLinkedDataElements();
        for (DataCollectionElement dataCollElem : dataCollectionElements) {
            insertDataCollectionElement(savedDataCollectionTypeId, dataCollElem);
        }

        return savedDataCollectionType;
    }

    @Override
    public void updateCollectionLocalizedFields(Long collectionInstanceId, LocalizedFields localizedFields, String localeAbbrev) {

        List<Long> localizedFieldsIds = this.template.query(FIND_LOCAL_FIELDS_ID, new LocalizedFieldsIdRowMapper(), localeAbbrev, collectionInstanceId);

        if(!localizedFieldsIds.isEmpty()) {
            this.template.update(DELETE_LOC_FIELDS_TABLE, localizedFieldsIds.get(0));
        }

        insertCollectionLocalizedFields(collectionInstanceId, localizedFields);
        
    }

    private void insertCollectionLocalizedFields(Long collectionInstanceId, LocalizedFields localizedFields) {

        KeyHolder keyHolderIm = new GeneratedKeyHolder();
        Long localizedFieldsId = null;

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_LOC_FIELDS_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, collectionInstanceId);

            return ps;
        }, keyHolderIm);

        localizedFieldsId = getKey(keyHolderIm);
        
        // a. Insert CollectionNameField
        CollectionNameField collNameField = localizedFields.getCollectionNameField();
        this.template.update(INSERT_DATA_COLL_NAME_FIELD_TABLE,
                                collNameField.getFieldType(),
                                collNameField.getFieldName(),
                                collNameField.getFieldValue(),
                                localizedFieldsId);

        // b. Insert TextFields
        List<TextField> textFields = localizedFields.getTextFields();
        for (TextField textField : textFields) {
            this.template.update(INSERT_DATA_LOC_TEXT_FIELD_TABLE,
                                textField.getFieldType(),
                                textField.getFieldName(),
                                textField.getFieldValue(),
                                localizedFieldsId);
        }

        // c. Insert NumericFields
        List<NumericField> numericFields = localizedFields.getNumericFields();
        for (NumericField numericField : numericFields) {
            this.template.update(INSERT_DATA_LOC_NUMERIC_FIELD_TABLE,
                                numericField.getFieldType(),
                                numericField.getFieldName(),
                                numericField.getFieldValue(),
                                localizedFieldsId);
        }

        // d. Insert BooleanFields
        List<BooleanField> booleanFields = localizedFields.getBooleanFields();
        for (BooleanField booleanField : booleanFields) {
            this.template.update(INSERT_DATA_LOC_BOOLEAN_FIELD_TABLE,
                                booleanField.getFieldType(),
                                booleanField.getFieldName(),
                                booleanField.getFieldValue(),
                                localizedFieldsId);
        }

        // e. Insert CollectionLocalizedInternals
        CollectionInternals collectionLocalizedInternals = localizedFields.getCollectionLocalizedInternals();
        this.template.update(INSERT_DATA_COLL_LOC_INTERNALS_TABLE,
                            collectionLocalizedInternals.getKioskLocaleId(),
                            collectionLocalizedInternals.getCreatedOn(),
                            collectionLocalizedInternals.getLastModified(), 
                            collectionLocalizedInternals.getStatus().toString(),
                            localizedFieldsId);
    }

    @Override
    public void updateCollectionInstanceDataElements(Long collectionInstanceId, List<DataCollectionElement> newElements) {
        List<Long> dataElemIds = this.template.query(FIND_DATA_ELEMS_ID, new DataElemIdRowMapper(), collectionInstanceId);

        for (Long dataElemId : dataElemIds) {
            this.template.update(DELETE_DATA_ELEMS_TABLE, dataElemId);
        }

        for (DataCollectionElement dataCollElem : newElements) {
            insertDataCollectionElement(collectionInstanceId, dataCollElem);
        };
    }

    @Override
    public List<DataCollectionType> findByCollectionTypeAndLocale(String collectionType, String collectionSubType,
            String localeAbbrev) {

        List<DataCollectionType> matchingDataCollectionTypes = new ArrayList<>();
        
        List<DataCollectionTypeQueryResults> dataCollectionTypeResults = findShallowByCollectionTypeAndLocale(collectionType, collectionSubType, localeAbbrev);

        for(DataCollectionTypeQueryResults dataCollectionTypeResult : dataCollectionTypeResults) {
            matchingDataCollectionTypes.add(getDataCollectionType(dataCollectionTypeResult));
        }

        return matchingDataCollectionTypes;

    }

    @Override
    public void deleteCollectionTypeInstancesWithLocale(String collectionType, String collectionSubType,
            String localeAbbrev) {

        List<DataCollectionType> dataCollectionInstances = findByCollectionTypeAndLocale(collectionType, collectionSubType, localeAbbrev);
        
        for(DataCollectionType dataCollectionInstance : dataCollectionInstances) {
            List<Long> localizedFieldsIds = this.template.query(FIND_LOCAL_FIELDS_ID, new LocalizedFieldsIdRowMapper(), localeAbbrev, dataCollectionInstance.getId());

            // there should only be one...
            if(localizedFieldsIds.isEmpty()) {
                throw new RuntimeException("No locale fields deleted since none were found");
            }

            Long localizedFieldId = localizedFieldsIds.get(0);

            this.template.update(DELETE_LOC_FIELDS_TABLE, localizedFieldId);
        }

    }

    @Override
    public void deleteCollectionInstance(Long collectionInstanceId) {
        this.template.update(DELETE_COLL_TYPE_TABLE, collectionInstanceId);

    }

    @Override
    public boolean doesInstanceOfCollectionExist(String collectionType, String collectionSubType, String localeAbbrev) {
        
        return !findByCollectionTypeAndLocale(collectionType, collectionSubType, localeAbbrev).isEmpty();
    }

    @Override
    public DataCollectionType updateCollectionInstance(Long collectionInstanceId, DataCollectionType collectionInstance) {
        // Get all other LocalizedFields;
        List<LocalizedFieldsResults> localizedFieldsResults = template.query(FIND_LOC_FIELDS_TABLE, new LocalizedFieldsResultsRowMapper(), collectionInstanceId);
        
        Long localeIdOfNewCollection = collectionInstance.getLocalizedFields().getCollectionLocalizedInternals().getKioskLocaleId();

        List<LocalizedFields> existingLocalizedFields = new ArrayList<>();

        for (LocalizedFieldsResults localizedFieldsResult : localizedFieldsResults) {
            if(!localizedFieldsResult.getLocalizedCollInternals().getKioskLocaleId().equals(localeIdOfNewCollection)) {
                existingLocalizedFields.add(getLocalizedFields(localizedFieldsResult));
            }
        }

        // Delete collection along with associated locales and re-insert
        deleteCollectionInstance(collectionInstanceId);
        
        DataCollectionType dataCollectionInstance = saveCollectionInstance(collectionInstance);

        for(LocalizedFields locFields : existingLocalizedFields) {
            insertCollectionLocalizedFields(collectionInstanceId, locFields);
        }

        return dataCollectionInstance;

    }

    private LocalizedFields getLocalizedFields(LocalizedFieldsResults localizedFieldsResult) {

        List<TextField> localizedTextFields = template.query(FIND_LOC_TEXT_FIELD_TABLE, new TextFieldRowMapper(), localizedFieldsResult.getLocalizedFieldsId());
        List<NumericField> localizedNumericFields = template.query(FIND_LOC_NUMERIC_FIELD_TABLE, new NumericFieldRowMapper(), localizedFieldsResult.getLocalizedFieldsId());
        List<BooleanField> localizedBooleanFields = template.query(FIND_LOC_BOOLEAN_FIELD_TABLE, new BooleanFieldRowMapper(), localizedFieldsResult.getLocalizedFieldsId());

        return new LocalizedFields(localizedFieldsResult.getLocCollNameField(), localizedTextFields, localizedNumericFields, localizedBooleanFields, localizedFieldsResult.getLocalizedCollInternals());
        
        
    }
    
    private Long getKey(KeyHolder key) {
        Number keyValue = key.getKey();
        if(keyValue == null) {
            throw new RuntimeException("Unable to obtain id");
        }

        return keyValue.longValue();
    }

    private void insertDataCollectionElement(Long parentColl, DataCollectionElement dataCollectionElement) {
        KeyHolder keyHolderDataElem = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_DATA_ELEM_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dataCollectionElement.getType());
            ps.setLong(2, parentColl);

            return ps;
        }, keyHolderDataElem);

        Long savedDataCollectionElemId = getKey(keyHolderDataElem);

         // 1. Insert TextFields
        List<TextField> textFields = dataCollectionElement.getTextFields();
        for (TextField textField : textFields) {
            this.template.update(INSERT_DATA_ELEM_TEXT_FIELD_TABLE,
                                textField.getFieldType(),
                                textField.getFieldName(),
                                textField.getFieldValue(),
                                savedDataCollectionElemId);
        }

        // 2. Insert NumericFields
        List<NumericField> numericFields = dataCollectionElement.getNumericFields();
        for (NumericField numericField : numericFields) {
            this.template.update(INSERT_DATA_ELEM_NUMERIC_FIELD_TABLE,
                                numericField.getFieldType(),
                                numericField.getFieldName(),
                                numericField.getFieldValue(),
                                savedDataCollectionElemId);
        }

        // 3. Insert BooleanFields
        List<BooleanField> booleanFields = dataCollectionElement.getBooleanFields();
        for (BooleanField booleanField : booleanFields) {
            this.template.update(INSERT_DATA_ELEM_BOOLEAN_FIELD_TABLE,
                                booleanField.getFieldType(),
                                booleanField.getFieldName(),
                                booleanField.getFieldValue(),
                                savedDataCollectionElemId);
        }


    }

    private List<DataCollectionTypeQueryResults> findShallowByCollectionTypeAndLocale(String collectionType, String collectionSubType, String localeAbbrev) {
        List<DataCollectionTypeQueryResults> dataCollectionTypeResults = collectionSubType!= null ?
                                                                             this.template.query(FIND_DATA_COLL_TYPE_TABLE_WITH_SUB, new DataCollectionTypeQueryRowMapper(), localeAbbrev, collectionType, collectionSubType)
                                                                             : this.template.query(FIND_DATA_COLL_TYPE_TABLE, new DataCollectionTypeQueryRowMapper(), localeAbbrev, collectionType);
        
        return dataCollectionTypeResults;
    }

    private DataCollectionType getDataCollectionType(DataCollectionTypeQueryResults dataCollectionTypeResult) {

        Long collectionTypeId = dataCollectionTypeResult.getCollectionId();
        Long localizedFieldsContainerId = dataCollectionTypeResult.getLocalizedFieldsId();

        // Prepare LocalizedFields
        CollectionNameField localizedCollectionNameField = dataCollectionTypeResult.getLocalizedCollectionNameField();
        CollectionInternals localizedCollectionInternals = dataCollectionTypeResult.getLocalizedCollectionInternals();

        List<TextField> localizedTextFields = template.query(FIND_LOC_TEXT_FIELD_TABLE, new TextFieldRowMapper(), localizedFieldsContainerId);
        List<NumericField> localizedNumericFields = template.query(FIND_LOC_NUMERIC_FIELD_TABLE, new NumericFieldRowMapper(), localizedFieldsContainerId);
        List<BooleanField> localizedBooleanFields = template.query(FIND_LOC_BOOLEAN_FIELD_TABLE, new BooleanFieldRowMapper(), localizedFieldsContainerId);

        LocalizedFields localizedFields = new LocalizedFields(localizedCollectionNameField, localizedTextFields, localizedNumericFields, localizedBooleanFields, localizedCollectionInternals);
        
        
        List<DataCollectionElement> dataElements = getDataElements(collectionTypeId);
        
        return new DataCollectionTypeImpl.Builder()
                        .withId(collectionTypeId)
                        .type(dataCollectionTypeResult.getType())
                        .subType(dataCollectionTypeResult.getSubType())
                        .localizedFields(localizedFields)
                        .collectionInternals(dataCollectionTypeResult.getCollectionInternals())
                        .linkedDataElements(dataElements)
                        .build();
    }

    private List<DataCollectionElement> getDataElements(Long parentDataCollectionId) {
        List<DataElemQueryResults> dataElemResults = template.query(FIND_ELEMS_TABLE, new DataElemRowMapper(), parentDataCollectionId);
        List<DataCollectionElement> dataElements = new ArrayList<>();

        List<TextField> elemTextFields = null;
        List<NumericField> elemNumericFields = null;
        List<BooleanField> elemBooleanFields = null;

        for (DataElemQueryResults dataElemResult : dataElemResults) {
            elemTextFields = template.query(FIND_ELEM_TEXT_FIELD_TABLE, new TextFieldRowMapper(), dataElemResult.getDataElemId());
            elemNumericFields = template.query(FIND_ELEM_NUMERIC_FIELD_TABLE, new NumericFieldRowMapper(), dataElemResult.getDataElemId());
            elemBooleanFields = template.query(FIND_ELEM_BOOLEAN_FIELD_TABLE, new BooleanFieldRowMapper(), dataElemResult.getDataElemId());

            dataElements.add(new DataCollectionElementImpl.Builder().booleanFields(elemBooleanFields)
                                                                    .numericFields(elemNumericFields)
                                                                    .textFields(elemTextFields)
                                                                    .withId(dataElemResult.getDataElemId())
                                                                    .type(dataElemResult.getType())
                                                                    .build()

                            );
        }

        return dataElements;
    }

}
