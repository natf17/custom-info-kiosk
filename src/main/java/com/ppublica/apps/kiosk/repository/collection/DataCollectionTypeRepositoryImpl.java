package com.ppublica.apps.kiosk.repository.collection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElement;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
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
        KeyHolder keyHolderIm = new GeneratedKeyHolder();
        Long localizedFieldsId = null;

        LocalizedFields localizedFields = savedDataCollectionType.getLocalizedFields();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_LOC_FIELDS_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, savedDataCollectionTypeId);

            return ps;
        }, keyHolderIm);

        localizedFieldsId = getKey(keyHolderIm);
        
        // 3a. Insert CollectionNameField
        CollectionNameField collNameField = localizedFields.getCollectionNameField();
        this.template.update(INSERT_DATA_COLL_NAME_FIELD_TABLE,
                                collNameField.getFieldType(),
                                collNameField.getFieldName(),
                                collNameField.getFieldValue(),
                                localizedFieldsId);

        // 3b. Insert TextFields
        List<TextField> textFields = localizedFields.getTextFields();
        for (TextField textField : textFields) {
            this.template.update(INSERT_DATA_LOC_TEXT_FIELD_TABLE,
                                textField.getFieldType(),
                                textField.getFieldName(),
                                textField.getFieldValue(),
                                localizedFieldsId);
        }

        // 3c. Insert NumericFields
        List<NumericField> numericFields = localizedFields.getNumericFields();
        for (NumericField numericField : numericFields) {
            this.template.update(INSERT_DATA_LOC_NUMERIC_FIELD_TABLE,
                                numericField.getFieldType(),
                                numericField.getFieldName(),
                                numericField.getFieldValue(),
                                localizedFieldsId);
        }

        // 3d. Insert BooleanFields
        List<BooleanField> booleanFields = localizedFields.getBooleanFields();
        for (BooleanField booleanField : booleanFields) {
            this.template.update(INSERT_DATA_LOC_BOOLEAN_FIELD_TABLE,
                                booleanField.getFieldType(),
                                booleanField.getFieldName(),
                                booleanField.getFieldValue(),
                                localizedFieldsId);
        }

        // 3e. Insert CollectionLocalizedInternals
        CollectionInternals collectionLocalizedInternals = localizedFields.getCollectionLocalizedInternals();
        this.template.update(INSERT_DATA_COLL_LOC_INTERNALS_TABLE,
                            collectionLocalizedInternals.getKioskLocaleId(),
                            collectionLocalizedInternals.getCreatedOn(),
                            collectionLocalizedInternals.getLastModified(), 
                            collectionLocalizedInternals.getStatus().toString(),
                            localizedFieldsId);
        
        // 4. Insert DataCollectionElements
        List<DataCollectionElement> dataCollectionElements = savedDataCollectionType.getLinkedDataElements();
        for (DataCollectionElement dataCollElem : dataCollectionElements) {
            insertDataCollectionElement(savedDataCollectionTypeId, dataCollElem);
        }

        return savedDataCollectionType;
    }

    @Override
    public DataCollectionType saveCollectionLocalizedFields(Long collectionInstanceId,
            LocalizedFields localizedFields) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveCollectionLocalizedFields'");
    }

    @Override
    public DataCollectionType saveDataElementsToCollectionInstance(Long collectionInstanceId,
            List<DataCollectionElement> element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveDataElementsToCollectionInstance'");
    }

    @Override
    public List<SimpleCollectionType> findByCollectionTypeAndLocale(String collectionType, String collectionSubType,
            String localeAbbrev) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCollectionTypeAndLocale'");
    }

    @Override
    public void deleteCollectionTypeInstancesWithLocale(String collectionType, String collectionSubType,
            String localeAbbrev) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCollectionTypeInstancesWithLocale'");
    }

    @Override
    public void deleteCollectionInstance(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCollectionInstance'");
    }

    @Override
    public boolean doesInstanceOfCollectionExist(String collectionType, String collectionSubType, String localeAbbrev) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doesInstanceOfCollectionExist'");
    }

    @Override
    public DataCollectionType updateCollectionInstance(Long id, DataCollectionType collectionInstance) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCollectionInstance'");
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
            ps.setLong(1, parentColl);

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

}
