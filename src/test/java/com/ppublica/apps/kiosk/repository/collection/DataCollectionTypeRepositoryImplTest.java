package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.TestPropertySource;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElement;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElementImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

@JdbcTest
@TestPropertySource("classpath:test.properties")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import(DataCollectionTypeRepositoryImpl.class)
public class DataCollectionTypeRepositoryImplTest {
    @Autowired
    DataCollectionTypeRepository repo;

    @Autowired
    JdbcTemplate template;

    Long enLocaleId;
    Long esLocaleId;

    DataCollectionType newDataCollectionTypeNoElems;
    DataCollectionType newDataCollectionTypeWithElems;
    LocalizedFields newLocalizedFieldsElemsSp;

    LocalizedFields newLocalizedFieldsUpdated;
    List<DataCollectionElement> newDataElementsUpdated;

    DataCollectionType newDataCollectionTypeWithElemsUpd;
/*

    @BeforeEach
    public void setup() {
        
        this.enLocaleId = template.query("SELECT id FROM kiosk_locale WHERE abbrev = ?", new IdRowMapper(), "EN").get(0);
        this.esLocaleId = template.query("SELECT id FROM kiosk_locale WHERE abbrev = ?", new IdRowMapper(), "SP").get(0);
        

        LocalDate testDateColl = LocalDate.of(2023, 9, 12);
        LocalDateTime testDateTimeColl = LocalDateTime.of(2023, 1, 9, 9, 30);


        // prepare LocalizedFields components
        LocalDate testDateLocalized = LocalDate.of(2023, 10, 12);
        LocalDateTime testDateTimeLocalized = LocalDateTime.of(2023, 2, 9, 9, 30);

        List<TextField> localizedTextFields = new ArrayList<>();
        localizedTextFields.add(new TextField("local_tf_fieldType1", "local_tf_fieldName1", "local_tf_fieldValue1"));
        localizedTextFields.add(new TextField("local_tf_fieldType2", "local_tf_fieldName2", "local_tf_fieldValue2"));

        List<BooleanField> localizedBooleanFields = new ArrayList<>();
        localizedBooleanFields.add(new BooleanField("local_bf_fieldType1", "local_bf_fieldName1", true));
        localizedBooleanFields.add(new BooleanField("local_bf_fieldType2", "local_bf_fieldName2", true));

        List<NumericField> localizedNumericFields = new ArrayList<>();
        localizedNumericFields.add(new NumericField("local_nf_fieldType1", "local_nf_fieldName1", 34L));
        localizedNumericFields.add(new NumericField("local_nf_fieldType2", "local_nf_fieldName2", 4L));

        CollectionNameField localizedCollectionNameField = new CollectionNameField("coll_nf_fieldName", "coll_nf_fieldValue");
        CollectionInternals localizedCollectionInternals = new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDateLocalized, testDateTimeLocalized);

        newDataCollectionTypeNoElems = new DataCollectionTypeImpl.Builder().type("TYPE")
                                                                    .subType("SUBTYPE")
                                                                    .localizedFields(new LocalizedFields(localizedCollectionNameField, localizedTextFields, localizedNumericFields, localizedBooleanFields, localizedCollectionInternals))
                                                                    .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDateColl, testDateTimeColl))
                                                                    .build();

        List<DataCollectionElement> linkedDataElements = new ArrayList<>();
        linkedDataElements.add(new DataCollectionElementImpl.Builder()
                                                .addBooleanField(new BooleanField("bf_fieldType_elem1", "bf_fieldName_elem1", true))
                                                .addBooleanField(new BooleanField("bf_fieldType_elem1_2", "bf_fieldName_elem1_2", true))
                                                .addTextField(new TextField("tf_fieldType_elem1", "tf_fieldName_elem1", "tf_fieldValue_elem1"))
                                                .addTextField(new TextField("tf_fieldType_elem1_2", "tf_fieldName_elem1_2", "tf_fieldValue_elem1_2"))
                                                .addNumericField(new NumericField("nf_fieldType_elem1", "nf_fieldName_elem1", 1L))
                                                .addNumericField(new NumericField("nf_fieldType_elem1_2", "nf_fieldName_elem1_2", 2L))
                                                .type("ELEMTYPE1")
                                                .build()
                            );
        
        linkedDataElements.add(new DataCollectionElementImpl.Builder()
                                                .addBooleanField(new BooleanField("bf_fieldType_elem2", "bf_fieldName_elem2", true))
                                                .addTextField(new TextField("tf_fieldType_elem2", "tf_fieldName_elem2", "tf_fieldValue_elem2"))
                                                .type("ELEMTYPE2")
                                                .build()
                            );

        newDataCollectionTypeWithElems = new DataCollectionTypeImpl.Builder().type("TYPE")
                                                                    .subType("SUBTYPE")
                                                                    .localizedFields(new LocalizedFields(localizedCollectionNameField, localizedTextFields, localizedNumericFields, localizedBooleanFields, localizedCollectionInternals))
                                                                    .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDateColl, testDateTimeColl))
                                                                    .linkedDataElements(linkedDataElements)
                                                                    .build();

        CollectionInternals localizedCollectionInternalsSp = new CollectionInternals(esLocaleId, PageStatus.PUBLISHED, testDateLocalized, testDateTimeLocalized);
        newLocalizedFieldsElemsSp = new LocalizedFields(localizedCollectionNameField, localizedTextFields, localizedNumericFields, localizedBooleanFields, localizedCollectionInternalsSp);

        CollectionNameField localizedCollectionNameFieldUpdated = new CollectionNameField("coll_nf_fieldName_upd", "coll_nf_fieldValue_upd");
        CollectionInternals localizedCollectionInternalsUpdated = new CollectionInternals(enLocaleId, PageStatus.DRAFT, LocalDate.of(2023, 11, 10),  LocalDateTime.of(2023, 11, 10, 9, 30));
        List<TextField> updTextFields = new ArrayList<>();
        updTextFields.add(new TextField("local_tf_fieldType_upd", "local_tf_fieldName_upd", "local_tf_fieldValue_upd"));

        List<BooleanField> updBooleanFields = new ArrayList<>();
        updBooleanFields.add(new BooleanField("local_bf_fieldType_upd", "local_bf_fieldName_upd", true));

        List<NumericField> updNumericFields = new ArrayList<>();
        updNumericFields.add(new NumericField("local_nf_fieldType_upd", "local_nf_fieldName_upd", 9L));
        newLocalizedFieldsUpdated = new LocalizedFields(localizedCollectionNameFieldUpdated, updTextFields, updNumericFields, updBooleanFields, localizedCollectionInternalsUpdated);

        newDataElementsUpdated = new ArrayList<>();
        
        newDataElementsUpdated.add(new DataCollectionElementImpl.Builder()
                                                .addBooleanField(new BooleanField("bf_fieldType_elem_upd", "bf_fieldName_elem_upd", true))
                                                .addTextField(new TextField("tf_fieldType_elem_upd", "tf_fieldName_elem_upd", "tf_fieldValue_elem_upd"))
                                                .addNumericField(new NumericField("nf_fieldType_elem_upd", "nf_fieldName_elem_upd", 9L))
                                                .type("ELEMTYPE3")
                                                .build()
                                    );

        newDataCollectionTypeWithElemsUpd = new DataCollectionTypeImpl.Builder().type("TYPE_UPD")
                                                                    .subType("SUBTYPE_UPD")
                                                                    .localizedFields(newLocalizedFieldsUpdated)
                                                                    .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.DRAFT, LocalDate.of(2023, 11, 10),  LocalDateTime.of(2023, 11, 10, 9, 30)))
                                                                    .linkedDataElements(newDataElementsUpdated)
                                                                    .build();
        
    }

    @Test
    public void givenNewDataCollTypeNoElems_saveAndFind_success() {

        DataCollectionType savedDataCollectionType = repo.saveCollectionInstance(newDataCollectionTypeNoElems);

        List<DataCollectionType> foundDataCollectionTypes = repo.findByCollectionTypeAndLocale(newDataCollectionTypeNoElems.getType(), newDataCollectionTypeNoElems.getSubType(), "EN");
        Assertions.assertEquals(1, foundDataCollectionTypes.size());

        DataCollectionType foundDataCollectionType = foundDataCollectionTypes.get(0);
        
        Assertions.assertNotNull(foundDataCollectionType.getId());
        Assertions.assertEquals(savedDataCollectionType.getType(), foundDataCollectionType.getType());
        Assertions.assertEquals(savedDataCollectionType.getSubType(), foundDataCollectionType.getSubType());

        CollectionInternals foundDataCollectionInternals = foundDataCollectionType.getCollectionInternals();
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getKioskLocaleId(), foundDataCollectionInternals.getKioskLocaleId());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getStatus(), foundDataCollectionInternals.getStatus());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getCreatedOn(), foundDataCollectionInternals.getCreatedOn());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getLastModified(), foundDataCollectionInternals.getLastModified());

        LocalizedFields foundLocalizedFields = foundDataCollectionType.getLocalizedFields();
        LocalizedFields savedLocalizedFields = savedDataCollectionType.getLocalizedFields();

        CollectionNameField foundCollectionNameField = foundLocalizedFields.getCollectionNameField();
        CollectionNameField savedCollectionNameField = savedLocalizedFields.getCollectionNameField();
        Assertions.assertEquals(savedCollectionNameField.getFieldType(), foundCollectionNameField.getFieldType());
        Assertions.assertEquals(savedCollectionNameField.getFieldName(), foundCollectionNameField.getFieldName());
        Assertions.assertEquals(savedCollectionNameField.getFieldValue(), foundCollectionNameField.getFieldValue());

        Assertions.assertEquals(2, foundLocalizedFields.getNumericFields().size());
        Assertions.assertTrue(foundLocalizedFields.getNumericFields().get(0).getFieldType().equals("local_nf_fieldType1") || foundLocalizedFields.getNumericFields().get(1).getFieldType().equals("local_nf_fieldType1"));
        NumericField numField1 = foundLocalizedFields.getNumericFields().get(0).getFieldType().equals("local_nf_fieldType1") ? foundLocalizedFields.getNumericFields().get(0) : foundLocalizedFields.getNumericFields().get(1);
        NumericField numField2 = foundLocalizedFields.getNumericFields().get(0).getFieldType().equals("local_nf_fieldType2") ? foundLocalizedFields.getNumericFields().get(0) : foundLocalizedFields.getNumericFields().get(1);
        Assertions.assertEquals("local_nf_fieldType1", numField1.getFieldType());
        Assertions.assertEquals("local_nf_fieldName1", numField1.getFieldName());
        Assertions.assertEquals(34L, numField1.getFieldValue());
        Assertions.assertEquals("local_nf_fieldType2", numField2.getFieldType());
        Assertions.assertEquals("local_nf_fieldName2", numField2.getFieldName());
        Assertions.assertEquals(4L, numField2.getFieldValue());

        Assertions.assertEquals(2, foundLocalizedFields.getBooleanFields().size());
        Assertions.assertTrue(foundLocalizedFields.getBooleanFields().get(0).getFieldType().equals("local_bf_fieldType1") || foundLocalizedFields.getBooleanFields().get(1).getFieldType().equals("local_bf_fieldType1"));
        BooleanField boolField1 = foundLocalizedFields.getBooleanFields().get(0).getFieldType().equals("local_bf_fieldType1") ? foundLocalizedFields.getBooleanFields().get(0) : foundLocalizedFields.getBooleanFields().get(1);
        BooleanField boolField2 = foundLocalizedFields.getBooleanFields().get(0).getFieldType().equals("local_bf_fieldType2") ? foundLocalizedFields.getBooleanFields().get(0) : foundLocalizedFields.getBooleanFields().get(1);
        Assertions.assertEquals("local_bf_fieldType1", boolField1.getFieldType());
        Assertions.assertEquals("local_bf_fieldName1", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField1.getFieldValue());
        Assertions.assertEquals("local_bf_fieldType2", boolField2.getFieldType());
        Assertions.assertEquals("local_bf_fieldName2", boolField2.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());
        
        Assertions.assertEquals(2, foundLocalizedFields.getTextFields().size());
        Assertions.assertTrue(foundLocalizedFields.getTextFields().get(0).getFieldType().equals("local_tf_fieldType1") || foundLocalizedFields.getTextFields().get(1).getFieldType().equals("local_tf_fieldType1"));
        TextField textField1 = foundLocalizedFields.getTextFields().get(0).getFieldType().equals("local_tf_fieldType1") ? foundLocalizedFields.getTextFields().get(0) : foundLocalizedFields.getTextFields().get(1);
        TextField textField2 = foundLocalizedFields.getTextFields().get(0).getFieldType().equals("local_tf_fieldType2") ? foundLocalizedFields.getTextFields().get(0) : foundLocalizedFields.getTextFields().get(1);
        Assertions.assertEquals("local_tf_fieldType1", textField1.getFieldType());
        Assertions.assertEquals("local_tf_fieldName1", textField1.getFieldName());
        Assertions.assertEquals("local_tf_fieldValue1", textField1.getFieldValue());
        Assertions.assertEquals("local_tf_fieldType2", textField2.getFieldType());
        Assertions.assertEquals("local_tf_fieldName2", textField2.getFieldName());
        Assertions.assertEquals("local_tf_fieldValue2", textField2.getFieldValue());

        Assertions.assertTrue(foundDataCollectionType.getLinkedDataElements().isEmpty());

    }

    @Test
    public void givenNewDataCollTypeWithElems_saveAndFind_success() {

        DataCollectionType savedDataCollectionType = repo.saveCollectionInstance(newDataCollectionTypeWithElems);

        List<DataCollectionType> foundDataCollectionTypes = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "EN");
        Assertions.assertEquals(1, foundDataCollectionTypes.size());

        DataCollectionType foundDataCollectionType = foundDataCollectionTypes.get(0);
        
        Assertions.assertNotNull(foundDataCollectionType.getId());
        Assertions.assertEquals(savedDataCollectionType.getType(), foundDataCollectionType.getType());
        Assertions.assertEquals(savedDataCollectionType.getSubType(), foundDataCollectionType.getSubType());

        CollectionInternals foundDataCollectionInternals = foundDataCollectionType.getCollectionInternals();
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getKioskLocaleId(), foundDataCollectionInternals.getKioskLocaleId());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getStatus(), foundDataCollectionInternals.getStatus());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getCreatedOn(), foundDataCollectionInternals.getCreatedOn());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getLastModified(), foundDataCollectionInternals.getLastModified());

        LocalizedFields foundLocalizedFields = foundDataCollectionType.getLocalizedFields();
        LocalizedFields savedLocalizedFields = savedDataCollectionType.getLocalizedFields();

        CollectionNameField foundCollectionNameField = foundLocalizedFields.getCollectionNameField();
        CollectionNameField savedCollectionNameField = savedLocalizedFields.getCollectionNameField();

        Assertions.assertEquals(savedCollectionNameField.getFieldType(), foundCollectionNameField.getFieldType());
        Assertions.assertEquals(savedCollectionNameField.getFieldName(), foundCollectionNameField.getFieldName());
        Assertions.assertEquals(savedCollectionNameField.getFieldValue(), foundCollectionNameField.getFieldValue());

        Assertions.assertEquals(2, foundLocalizedFields.getNumericFields().size());

        Assertions.assertEquals(2, foundLocalizedFields.getBooleanFields().size());

        Assertions.assertEquals(2, foundLocalizedFields.getTextFields().size());

        List<DataCollectionElement> foundElems = foundDataCollectionType.getLinkedDataElements();
        Assertions.assertEquals(2, foundElems.size());
        Assertions.assertTrue(foundElems.get(0).getType().equals("ELEMTYPE1") || foundElems.get(1).getType().equals("ELEMTYPE1"));
        DataCollectionElement elem1 = foundElems.get(0).getType().equals("ELEMTYPE1") ? foundElems.get(0) : foundElems.get(1);
        DataCollectionElement elem2 = foundElems.get(0).getType().equals("ELEMTYPE2") ? foundElems.get(0) : foundElems.get(1);
        
        // test elem1
        Assertions.assertNotNull(elem1.getId());
        Assertions.assertEquals("ELEMTYPE1", elem1.getType());
        
        Assertions.assertEquals(2, elem1.getNumericFields().size());
        Assertions.assertTrue(elem1.getNumericFields().get(0).getFieldType().equals("nf_fieldType_elem1") || elem1.getNumericFields().get(1).getFieldType().equals("nf_fieldType_elem1"));
        NumericField numField1 = elem1.getNumericFields().get(0).getFieldType().equals("nf_fieldType_elem1") ? elem1.getNumericFields().get(0) : elem1.getNumericFields().get(1);
        NumericField numField2 = elem1.getNumericFields().get(0).getFieldType().equals("nf_fieldType_elem1_2") ? elem1.getNumericFields().get(0) : elem1.getNumericFields().get(1);
        Assertions.assertEquals("nf_fieldType_elem1", numField1.getFieldType());
        Assertions.assertEquals("nf_fieldName_elem1", numField1.getFieldName());
        Assertions.assertEquals(1L, numField1.getFieldValue());
        Assertions.assertEquals("nf_fieldType_elem1_2", numField2.getFieldType());
        Assertions.assertEquals("nf_fieldName_elem1_2", numField2.getFieldName());
        Assertions.assertEquals(2L, numField2.getFieldValue());

        Assertions.assertEquals(2, elem1.getBooleanFields().size());
        Assertions.assertTrue(elem1.getBooleanFields().get(0).getFieldType().equals("bf_fieldType_elem1") || elem1.getBooleanFields().get(1).getFieldType().equals("bf_fieldType_elem1"));
        BooleanField boolField1 = elem1.getBooleanFields().get(0).getFieldType().equals("bf_fieldType_elem1") ? elem1.getBooleanFields().get(0) : elem1.getBooleanFields().get(1);
        BooleanField boolField2 = elem1.getBooleanFields().get(0).getFieldType().equals("bf_fieldType_elem1_2") ? elem1.getBooleanFields().get(0) : elem1.getBooleanFields().get(1);
        Assertions.assertEquals("bf_fieldType_elem1", boolField1.getFieldType());
        Assertions.assertEquals("bf_fieldName_elem1", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());
        Assertions.assertEquals("bf_fieldType_elem1_2", boolField2.getFieldType());
        Assertions.assertEquals("bf_fieldName_elem1_2", boolField2.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());
        
        Assertions.assertEquals(2, elem1.getTextFields().size());
        Assertions.assertTrue(elem1.getTextFields().get(0).getFieldType().equals("tf_fieldType_elem1") || elem1.getTextFields().get(1).getFieldType().equals("tf_fieldType_elem1"));
        TextField textField1 = elem1.getTextFields().get(0).getFieldType().equals("tf_fieldType_elem1") ? elem1.getTextFields().get(0) : elem1.getTextFields().get(1);
        TextField textField2 = elem1.getTextFields().get(0).getFieldType().equals("tf_fieldType_elem1_2") ? elem1.getTextFields().get(0) : elem1.getTextFields().get(1);
        Assertions.assertEquals("tf_fieldType_elem1", textField1.getFieldType());
        Assertions.assertEquals("tf_fieldName_elem1", textField1.getFieldName());
        Assertions.assertEquals("tf_fieldValue_elem1", textField1.getFieldValue());
        Assertions.assertEquals("tf_fieldType_elem1_2", textField2.getFieldType());
        Assertions.assertEquals("tf_fieldName_elem1_2", textField2.getFieldName());
        Assertions.assertEquals("tf_fieldValue_elem1_2", textField2.getFieldValue());

        // test elem2
        Assertions.assertNotNull(elem2.getId());
        Assertions.assertEquals("ELEMTYPE2", elem2.getType());

        Assertions.assertEquals(1, elem2.getBooleanFields().size());
        BooleanField boolField = elem2.getBooleanFields().get(0);
        Assertions.assertEquals("bf_fieldType_elem2", boolField.getFieldType());
        Assertions.assertEquals("bf_fieldName_elem2", boolField.getFieldName());
        Assertions.assertEquals(true, boolField.getFieldValue());

        
        Assertions.assertEquals(1, elem2.getTextFields().size());
        TextField textField = elem2.getTextFields().get(0);
        Assertions.assertEquals("tf_fieldType_elem2", textField.getFieldType());
        Assertions.assertEquals("tf_fieldName_elem2", textField.getFieldName());
        Assertions.assertEquals("tf_fieldValue_elem2", textField.getFieldValue());

        Assertions.assertTrue(elem2.getNumericFields().isEmpty());

        
    }

    @Test
    public void givenNewDataCollTypeWithoutSubType_saveAndFind_success() {
        repo.saveCollectionInstance(newDataCollectionTypeWithElems);

        List<DataCollectionType> foundDataCollectionTypes = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), null, "EN");
        Assertions.assertEquals(1, foundDataCollectionTypes.size());
    }

    @Test
    public void givenNewDataCollTypeLocaleDoesntExist_saveAndFind_Empty() {
        repo.saveCollectionInstance(newDataCollectionTypeWithElems);

        List<DataCollectionType> foundDataCollectionTypesEn = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "EN");
        Assertions.assertEquals(1, foundDataCollectionTypesEn.size());

        List<DataCollectionType> foundDataCollectionTypesSp = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "SP");
        Assertions.assertTrue(foundDataCollectionTypesSp.isEmpty());

    }

    @Test
    public void givenNewLocalizedFieldsParam_updateCollectionLocalizedFields_success() {
        DataCollectionType savedDataCollectionType = repo.saveCollectionInstance(newDataCollectionTypeNoElems);

        repo.updateCollectionLocalizedFields(savedDataCollectionType.getId(), newLocalizedFieldsUpdated, "EN");
        
        List<DataCollectionType> foundDataCollectionTypes = repo.findByCollectionTypeAndLocale(newDataCollectionTypeNoElems.getType(), newDataCollectionTypeNoElems.getSubType(), "EN");
        Assertions.assertEquals(1, foundDataCollectionTypes.size());

        DataCollectionType foundDataCollectionType = foundDataCollectionTypes.get(0);
        
        Assertions.assertNotNull(foundDataCollectionType.getId());
        Assertions.assertEquals(savedDataCollectionType.getType(), foundDataCollectionType.getType());
        Assertions.assertEquals(savedDataCollectionType.getSubType(), foundDataCollectionType.getSubType());

        CollectionInternals foundDataCollectionInternals = foundDataCollectionType.getCollectionInternals();
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getKioskLocaleId(), foundDataCollectionInternals.getKioskLocaleId());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getStatus(), foundDataCollectionInternals.getStatus());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getCreatedOn(), foundDataCollectionInternals.getCreatedOn());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getLastModified(), foundDataCollectionInternals.getLastModified());

        LocalizedFields foundLocalizedFields = foundDataCollectionType.getLocalizedFields();
        LocalizedFields savedLocalizedFields = newLocalizedFieldsUpdated;

        CollectionNameField foundCollectionNameField = foundLocalizedFields.getCollectionNameField();
        CollectionNameField savedCollectionNameField = savedLocalizedFields.getCollectionNameField();
        Assertions.assertEquals(savedCollectionNameField.getFieldType(), foundCollectionNameField.getFieldType());
        Assertions.assertEquals(savedCollectionNameField.getFieldName(), foundCollectionNameField.getFieldName());
        Assertions.assertEquals(savedCollectionNameField.getFieldValue(), foundCollectionNameField.getFieldValue());

        Assertions.assertEquals(1, foundLocalizedFields.getNumericFields().size());
        NumericField numField1 = foundLocalizedFields.getNumericFields().get(0);
        Assertions.assertEquals("local_nf_fieldType_upd", numField1.getFieldType());
        Assertions.assertEquals("local_nf_fieldName_upd", numField1.getFieldName());
        Assertions.assertEquals(9L, numField1.getFieldValue());

        Assertions.assertEquals(1, foundLocalizedFields.getBooleanFields().size());
        BooleanField boolField1 = foundLocalizedFields.getBooleanFields().get(0);
        Assertions.assertEquals("local_bf_fieldType_upd", boolField1.getFieldType());
        Assertions.assertEquals("local_bf_fieldName_upd", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField1.getFieldValue());
        
        Assertions.assertEquals(1, foundLocalizedFields.getTextFields().size());
        TextField textField1 = foundLocalizedFields.getTextFields().get(0);
        Assertions.assertEquals("local_tf_fieldType_upd", textField1.getFieldType());
        Assertions.assertEquals("local_tf_fieldName_upd", textField1.getFieldName());
        Assertions.assertEquals("local_tf_fieldValue_upd", textField1.getFieldValue());

        Assertions.assertTrue(foundDataCollectionType.getLinkedDataElements().isEmpty());
    }

    @Test
    public void givenNewDataElemsParam_updateCollectionInstanceDataElements_success() {
        DataCollectionType savedDataCollectionType = repo.saveCollectionInstance(newDataCollectionTypeWithElems);

        repo.updateCollectionInstanceDataElements(savedDataCollectionType.getId(), newDataElementsUpdated);

        List<DataCollectionType> foundDataCollectionTypes = repo.findByCollectionTypeAndLocale(newDataCollectionTypeNoElems.getType(), newDataCollectionTypeNoElems.getSubType(), "EN");
        Assertions.assertEquals(1, foundDataCollectionTypes.size());

        DataCollectionType foundDataCollectionType = foundDataCollectionTypes.get(0);
        
        Assertions.assertNotNull(foundDataCollectionType.getId());
        Assertions.assertEquals(savedDataCollectionType.getType(), foundDataCollectionType.getType());
        Assertions.assertEquals(savedDataCollectionType.getSubType(), foundDataCollectionType.getSubType());

        CollectionInternals foundDataCollectionInternals = foundDataCollectionType.getCollectionInternals();
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getKioskLocaleId(), foundDataCollectionInternals.getKioskLocaleId());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getStatus(), foundDataCollectionInternals.getStatus());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getCreatedOn(), foundDataCollectionInternals.getCreatedOn());
        Assertions.assertEquals(savedDataCollectionType.getCollectionInternals().getLastModified(), foundDataCollectionInternals.getLastModified());

        LocalizedFields foundLocalizedFields = foundDataCollectionType.getLocalizedFields();
        LocalizedFields savedLocalizedFields = savedDataCollectionType.getLocalizedFields();

        CollectionNameField foundCollectionNameField = foundLocalizedFields.getCollectionNameField();
        CollectionNameField savedCollectionNameField = savedLocalizedFields.getCollectionNameField();
        Assertions.assertEquals(savedCollectionNameField.getFieldType(), foundCollectionNameField.getFieldType());
        Assertions.assertEquals(savedCollectionNameField.getFieldName(), foundCollectionNameField.getFieldName());
        Assertions.assertEquals(savedCollectionNameField.getFieldValue(), foundCollectionNameField.getFieldValue());

        Assertions.assertEquals(2, foundLocalizedFields.getNumericFields().size());
        Assertions.assertEquals(2, foundLocalizedFields.getBooleanFields().size());
        Assertions.assertEquals(2, foundLocalizedFields.getTextFields().size());

        List<DataCollectionElement> foundElems = foundDataCollectionType.getLinkedDataElements();
        Assertions.assertEquals(1, foundElems.size());
        DataCollectionElement savedElemUpd = foundElems.get(0);
        
        // test elem1
        Assertions.assertNotNull(savedElemUpd.getId());
        Assertions.assertEquals("ELEMTYPE3", savedElemUpd.getType());

        Assertions.assertEquals(1, savedElemUpd.getNumericFields().size());
        NumericField numField1 = savedElemUpd.getNumericFields().get(0);
        Assertions.assertEquals("nf_fieldType_elem_upd", numField1.getFieldType());
        Assertions.assertEquals("nf_fieldName_elem_upd", numField1.getFieldName());
        Assertions.assertEquals(9L, numField1.getFieldValue());

        Assertions.assertEquals(1, savedElemUpd.getBooleanFields().size());
        BooleanField boolField1 = savedElemUpd.getBooleanFields().get(0);
        Assertions.assertEquals("bf_fieldType_elem_upd", boolField1.getFieldType());
        Assertions.assertEquals("bf_fieldName_elem_upd", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField1.getFieldValue());
        
        Assertions.assertEquals(1, savedElemUpd.getTextFields().size());
        TextField textField1 = savedElemUpd.getTextFields().get(0);
        Assertions.assertEquals("tf_fieldType_elem_upd", textField1.getFieldType());
        Assertions.assertEquals("tf_fieldName_elem_upd", textField1.getFieldName());
        Assertions.assertEquals("tf_fieldValue_elem_upd", textField1.getFieldValue());

    }

    @Test
    public void givenNewCollectionInstanceParam_updateCollectionInstance_success() {
        DataCollectionType savedDataCollectionType = repo.saveCollectionInstance(newDataCollectionTypeWithElems);
        DataCollectionType savedDataCollectionTypeUpd = repo.updateCollectionInstance(savedDataCollectionType.getId(), newDataCollectionTypeWithElemsUpd);

        List<DataCollectionType> foundDataCollectionTypes = repo.findByCollectionTypeAndLocale(savedDataCollectionTypeUpd.getType(), savedDataCollectionTypeUpd.getSubType(), "EN");
        Assertions.assertEquals(1, foundDataCollectionTypes.size());

        DataCollectionType foundDataCollectionType = foundDataCollectionTypes.get(0);
        
        Assertions.assertNotNull(foundDataCollectionType.getId());
        Assertions.assertEquals(newDataCollectionTypeWithElemsUpd.getType(), foundDataCollectionType.getType());
        Assertions.assertEquals(newDataCollectionTypeWithElemsUpd.getSubType(), foundDataCollectionType.getSubType());

        CollectionInternals foundDataCollectionInternals = foundDataCollectionType.getCollectionInternals();
        Assertions.assertEquals(savedDataCollectionTypeUpd.getCollectionInternals().getKioskLocaleId(), foundDataCollectionInternals.getKioskLocaleId());
        Assertions.assertEquals(savedDataCollectionTypeUpd.getCollectionInternals().getStatus(), foundDataCollectionInternals.getStatus());
        Assertions.assertEquals(savedDataCollectionTypeUpd.getCollectionInternals().getCreatedOn(), foundDataCollectionInternals.getCreatedOn());
        Assertions.assertEquals(savedDataCollectionTypeUpd.getCollectionInternals().getLastModified(), foundDataCollectionInternals.getLastModified());

        LocalizedFields foundLocalizedFields = foundDataCollectionType.getLocalizedFields();
        LocalizedFields savedLocalizedFields = savedDataCollectionTypeUpd.getLocalizedFields();

        CollectionNameField foundCollectionNameField = foundLocalizedFields.getCollectionNameField();
        CollectionNameField savedCollectionNameField = savedLocalizedFields.getCollectionNameField();
        Assertions.assertEquals(savedCollectionNameField.getFieldType(), foundCollectionNameField.getFieldType());
        Assertions.assertEquals(savedCollectionNameField.getFieldName(), foundCollectionNameField.getFieldName());
        Assertions.assertEquals(savedCollectionNameField.getFieldValue(), foundCollectionNameField.getFieldValue());

        Assertions.assertEquals(1, foundLocalizedFields.getNumericFields().size());
        NumericField numField1 = foundLocalizedFields.getNumericFields().get(0);
        Assertions.assertEquals("local_nf_fieldType_upd", numField1.getFieldType());
        Assertions.assertEquals("local_nf_fieldName_upd", numField1.getFieldName());
        Assertions.assertEquals(9L, numField1.getFieldValue());

        Assertions.assertEquals(1, foundLocalizedFields.getBooleanFields().size());
        BooleanField boolField1 = foundLocalizedFields.getBooleanFields().get(0);
        Assertions.assertEquals("local_bf_fieldType_upd", boolField1.getFieldType());
        Assertions.assertEquals("local_bf_fieldName_upd", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField1.getFieldValue());
        
        Assertions.assertEquals(1, foundLocalizedFields.getTextFields().size());
        TextField textField1 = foundLocalizedFields.getTextFields().get(0);
        Assertions.assertEquals("local_tf_fieldType_upd", textField1.getFieldType());
        Assertions.assertEquals("local_tf_fieldName_upd", textField1.getFieldName());
        Assertions.assertEquals("local_tf_fieldValue_upd", textField1.getFieldValue());

        List<DataCollectionElement> foundElems = foundDataCollectionType.getLinkedDataElements();
        Assertions.assertEquals(1, foundElems.size());
        DataCollectionElement savedElemUpd = foundElems.get(0);
        
        // test elem1
        Assertions.assertNotNull(savedElemUpd.getId());
        Assertions.assertEquals("ELEMTYPE3", savedElemUpd.getType());

        Assertions.assertEquals(1, savedElemUpd.getNumericFields().size());
        NumericField numFieldElem = savedElemUpd.getNumericFields().get(0);
        Assertions.assertEquals("nf_fieldType_elem_upd", numFieldElem.getFieldType());
        Assertions.assertEquals("nf_fieldName_elem_upd", numFieldElem.getFieldName());
        Assertions.assertEquals(9L, numFieldElem.getFieldValue());

        Assertions.assertEquals(1, savedElemUpd.getBooleanFields().size());
        BooleanField boolFieldElem = savedElemUpd.getBooleanFields().get(0);
        Assertions.assertEquals("bf_fieldType_elem_upd", boolFieldElem.getFieldType());
        Assertions.assertEquals("bf_fieldName_elem_upd", boolFieldElem.getFieldName());
        Assertions.assertEquals(true, boolFieldElem.getFieldValue());
        
        Assertions.assertEquals(1, savedElemUpd.getTextFields().size());
        TextField textFieldElem = savedElemUpd.getTextFields().get(0);
        Assertions.assertEquals("tf_fieldType_elem_upd", textFieldElem.getFieldType());
        Assertions.assertEquals("tf_fieldName_elem_upd", textFieldElem.getFieldName());
        Assertions.assertEquals("tf_fieldValue_elem_upd", textFieldElem.getFieldValue());
    }

    @Test
    public void givenExistingTypeAndSubTypeAndLocaleParams_deleteCollectionTypeInstancesWithLocale_success() {
        DataCollectionType savedCollectionInstance = repo.saveCollectionInstance(newDataCollectionTypeWithElems);
        repo.updateCollectionLocalizedFields(savedCollectionInstance.getId(), newLocalizedFieldsElemsSp, "SP");

        repo.deleteCollectionTypeInstancesWithLocale("TYPE", "SUBTYPE", "EN");

        List<DataCollectionType> foundDataCollectionTypes = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "EN");
        Assertions.assertTrue(foundDataCollectionTypes.isEmpty());

        List<DataCollectionType> foundDataCollectionTypesSp = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "SP");
        Assertions.assertEquals(1, foundDataCollectionTypesSp.size());


    }

    @Test
    public void givenExistingTypeAndNullSubTypeAndLocaleParams_deleteCollectionTypeInstancesWithLocale_success() {
        DataCollectionType savedCollectionInstance = repo.saveCollectionInstance(newDataCollectionTypeWithElems);
        repo.updateCollectionLocalizedFields(savedCollectionInstance.getId(), newLocalizedFieldsElemsSp, "SP");

        repo.deleteCollectionTypeInstancesWithLocale("TYPE", null, "EN");

        List<DataCollectionType> foundDataCollectionTypes = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "EN");
        Assertions.assertTrue(foundDataCollectionTypes.isEmpty());

        List<DataCollectionType> foundDataCollectionTypesSp = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "SP");
        Assertions.assertEquals(1, foundDataCollectionTypesSp.size());
    }

    @Test
    public void givenExistingIdParam_deleteCollectionInstance_success() {
        DataCollectionType savedCollectionInstance = repo.saveCollectionInstance(newDataCollectionTypeWithElems);
        repo.updateCollectionLocalizedFields(savedCollectionInstance.getId(), newLocalizedFieldsElemsSp, "SP");

        repo.deleteCollectionInstance(savedCollectionInstance.getId());

        List<DataCollectionType> foundDataCollectionTypes = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "EN");
        Assertions.assertTrue(foundDataCollectionTypes.isEmpty());

        List<DataCollectionType> foundDataCollectionTypesSp = repo.findByCollectionTypeAndLocale(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "SP");
        Assertions.assertTrue(foundDataCollectionTypesSp.isEmpty());
    }

    @Test
    public void givenExistingTypeAndSubTypeParams_doesInstanceOfCollectionExist_success() {
        DataCollectionType savedCollectionInstance = repo.saveCollectionInstance(newDataCollectionTypeWithElems);
        repo.updateCollectionLocalizedFields(savedCollectionInstance.getId(), newLocalizedFieldsElemsSp, "SP");

        boolean doesExist = repo.doesInstanceOfCollectionExist(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "EN");
        boolean doesExistSp = repo.doesInstanceOfCollectionExist(newDataCollectionTypeWithElems.getType(), newDataCollectionTypeWithElems.getSubType(), "SP");
        Assertions.assertTrue(doesExist);
        Assertions.assertTrue(doesExistSp);

    }

    @Test
    public void givenExistingTypeAndNullSubTypeParams_doesInstanceOfCollectionExist_success() {
        DataCollectionType savedCollectionInstance = repo.saveCollectionInstance(newDataCollectionTypeWithElems);
        repo.updateCollectionLocalizedFields(savedCollectionInstance.getId(), newLocalizedFieldsElemsSp, "SP");

        boolean doesExist = repo.doesInstanceOfCollectionExist(newDataCollectionTypeWithElems.getType(), null, "EN");
        boolean doesExistSp = repo.doesInstanceOfCollectionExist(newDataCollectionTypeWithElems.getType(), null, "SP");
        Assertions.assertTrue(doesExist);
        Assertions.assertTrue(doesExistSp);
    }

    class IdRowMapper implements RowMapper<Long>{
    
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        
            return rs.getLong("id");

        }
    } */
}
