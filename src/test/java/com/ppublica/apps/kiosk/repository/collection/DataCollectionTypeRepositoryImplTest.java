package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

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
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;
import com.ppublica.apps.kiosk.repository.collection.SimpleCollectionTypeRepositoryImplTest.IdRowMapper;

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

   // DataCollectionTypeRepositoryImpl.Builder newDataCollectionBuilder;

    /*
    SimpleCollectionType newSimpleCollectionType1aChild; 
    SimpleCollectionType newSimpleCollectionType1b;

    SimpleCollectionType newSimpleCollectionType1bDiffLang;

    SimpleCollectionType newSimpleCollectionType2;

    SimpleCollectionType newSimpleCollectionType1aUpdated;  */

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
        localizedBooleanFields.add(new BooleanField("bf_fieldType1", "bf_fieldName1", true));
        localizedBooleanFields.add(new BooleanField("bf_fieldType2", "bf_fieldName2", true));

        List<NumericField> localizedNumericFields = new ArrayList<>();
        localizedNumericFields.add(new NumericField("nf_fieldType1", "nf_fieldName1", 34L));
        localizedNumericFields.add(new NumericField("nf_fieldType2", "nf_fieldName2", 4L));

        CollectionNameField localizedCollectionNameField = new CollectionNameField("coll_nf_fieldName", "coll_nf_fieldValue");
        CollectionInternals localizedCollectionInternals = new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDateLocalized, testDateTimeLocalized);

        newDataCollectionTypeNoElems = new DataCollectionTypeImpl.Builder().type("TYPE")
                                                                    .subType("SUBTYPE")
                                                                    .localizedFields(new LocalizedFields(localizedCollectionNameField, localizedTextFields, localizedNumericFields, localizedBooleanFields, localizedCollectionInternals))
                                                                    .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDateColl, testDateTimeColl))
                                                                    .build();

        List<DataCollectionElement> linkedDataElements = new ArrayList<>();
        linkedDataElements.add(new DataCollectionElementImpl.Builder()
                                                .booleanFields(localizedBooleanFields)
                                                .textFields(localizedTextFields)
                                                .numericFields(localizedNumericFields)
                                                .type("ELEMTYPE1")
                                                .build()
                            );
        
        linkedDataElements.add(new DataCollectionElementImpl.Builder()
                                                .addBooleanField(new BooleanField("bf_fieldType_elem2", "bf_fieldName_elem2", true))
                                                .addTextField(new TextField("local_tf_fieldType_elem2", "local_tf_fieldName_elem2", "local_tf_fieldValue_elem2"))
                                                .type("ELEMTYPE2")
                                                .build()
                            );

        newDataCollectionTypeWithElems = new DataCollectionTypeImpl.Builder().type("TYPE")
                                                                    .subType("SUBTYPE")
                                                                    .localizedFields(new LocalizedFields(localizedCollectionNameField, localizedTextFields, localizedNumericFields, localizedBooleanFields, localizedCollectionInternals))
                                                                    .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDateColl, testDateTimeColl))
                                                                    .linkedDataElements(linkedDataElements)
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
        Assertions.assertTrue(foundLocalizedFields.getNumericFields().get(0).getFieldType().equals("nf_fieldType1") || foundLocalizedFields.getNumericFields().get(1).getFieldType().equals("nf_fieldType1"));
        NumericField numField1 = foundLocalizedFields.getNumericFields().get(0).getFieldType().equals("nf_fieldType1") ? foundLocalizedFields.getNumericFields().get(0) : foundLocalizedFields.getNumericFields().get(1);
        NumericField numField2 = foundLocalizedFields.getNumericFields().get(0).getFieldType().equals("nf_fieldType2") ? foundLocalizedFields.getNumericFields().get(0) : foundLocalizedFields.getNumericFields().get(1);
        Assertions.assertEquals("nf_fieldType1", numField1.getFieldType());
        Assertions.assertEquals("nf_fieldName1", numField1.getFieldName());
        Assertions.assertEquals(34L, numField1.getFieldValue());
        Assertions.assertEquals("nf_fieldType2", numField2.getFieldType());
        Assertions.assertEquals("nf_fieldName2", numField2.getFieldName());
        Assertions.assertEquals(4L, numField2.getFieldValue());

        Assertions.assertEquals(2, foundLocalizedFields.getBooleanFields().size());
        Assertions.assertTrue(foundLocalizedFields.getBooleanFields().get(0).getFieldType().equals("bf_fieldType1") || foundLocalizedFields.getBooleanFields().get(1).getFieldType().equals("bf_fieldType1"));
        BooleanField boolField1 = foundLocalizedFields.getBooleanFields().get(0).getFieldType().equals("bf_fieldType1") ? foundLocalizedFields.getBooleanFields().get(0) : foundLocalizedFields.getBooleanFields().get(1);
        BooleanField boolField2 = foundLocalizedFields.getBooleanFields().get(0).getFieldType().equals("bf_fieldType2") ? foundLocalizedFields.getBooleanFields().get(0) : foundLocalizedFields.getBooleanFields().get(1);
        Assertions.assertEquals("bf_fieldType1", boolField1.getFieldType());
        Assertions.assertEquals("bf_fieldName1", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());
        Assertions.assertEquals("bf_fieldType2", boolField2.getFieldType());
        Assertions.assertEquals("bf_fieldName2", boolField2.getFieldName());
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
        Assertions.assertTrue(elem1.getNumericFields().get(0).getFieldType().equals("nf_fieldType1") || elem1.getNumericFields().get(1).getFieldType().equals("nf_fieldType1"));
        NumericField numField1 = elem1.getNumericFields().get(0).getFieldType().equals("nf_fieldType1") ? elem1.getNumericFields().get(0) : elem1.getNumericFields().get(1);
        NumericField numField2 = elem1.getNumericFields().get(0).getFieldType().equals("nf_fieldType2") ? elem1.getNumericFields().get(0) : elem1.getNumericFields().get(1);
        Assertions.assertEquals("nf_fieldType1", numField1.getFieldType());
        Assertions.assertEquals("nf_fieldName1", numField1.getFieldName());
        Assertions.assertEquals(34L, numField1.getFieldValue());
        Assertions.assertEquals("nf_fieldType2", numField2.getFieldType());
        Assertions.assertEquals("nf_fieldName2", numField2.getFieldName());
        Assertions.assertEquals(4L, numField2.getFieldValue());

        Assertions.assertEquals(2, elem1.getBooleanFields().size());
        Assertions.assertTrue(elem1.getBooleanFields().get(0).getFieldType().equals("bf_fieldType1") || elem1.getBooleanFields().get(1).getFieldType().equals("bf_fieldType1"));
        BooleanField boolField1 = elem1.getBooleanFields().get(0).getFieldType().equals("bf_fieldType1") ? elem1.getBooleanFields().get(0) : elem1.getBooleanFields().get(1);
        BooleanField boolField2 = elem1.getBooleanFields().get(0).getFieldType().equals("bf_fieldType2") ? elem1.getBooleanFields().get(0) : elem1.getBooleanFields().get(1);
        Assertions.assertEquals("bf_fieldType1", boolField1.getFieldType());
        Assertions.assertEquals("bf_fieldName1", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());
        Assertions.assertEquals("bf_fieldType2", boolField2.getFieldType());
        Assertions.assertEquals("bf_fieldName2", boolField2.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());
        
        Assertions.assertEquals(2, elem1.getTextFields().size());
        Assertions.assertTrue(elem1.getTextFields().get(0).getFieldType().equals("local_tf_fieldType1") || elem1.getTextFields().get(1).getFieldType().equals("local_tf_fieldType1"));
        TextField textField1 = elem1.getTextFields().get(0).getFieldType().equals("local_tf_fieldType1") ? elem1.getTextFields().get(0) : elem1.getTextFields().get(1);
        TextField textField2 = elem1.getTextFields().get(0).getFieldType().equals("local_tf_fieldType2") ? elem1.getTextFields().get(0) : elem1.getTextFields().get(1);
        Assertions.assertEquals("local_tf_fieldType1", textField1.getFieldType());
        Assertions.assertEquals("local_tf_fieldName1", textField1.getFieldName());
        Assertions.assertEquals("local_tf_fieldValue1", textField1.getFieldValue());
        Assertions.assertEquals("local_tf_fieldType2", textField2.getFieldType());
        Assertions.assertEquals("local_tf_fieldName2", textField2.getFieldName());
        Assertions.assertEquals("local_tf_fieldValue2", textField2.getFieldValue());

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
        Assertions.assertEquals("local_tf_fieldType_elem2", textField.getFieldType());
        Assertions.assertEquals("local_tf_fieldName_elem2", textField.getFieldName());
        Assertions.assertEquals("local_tf_fieldValue_elem2", textField.getFieldValue());

        Assertions.assertTrue(elem2.getNumericFields().isEmpty());

        
    }

    //@Test
    //public void givenNewDataCollTypeWithElems_saveAndFind_success() {


    class IdRowMapper implements RowMapper<Long>{
    
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        
            return rs.getLong("id");

        }
    }
}
