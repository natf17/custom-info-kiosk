package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

@JdbcTest
@TestPropertySource("classpath:test.properties")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import(SimpleCollectionTypeRepositoryImpl.class)
public class SimpleCollectionTypeRepositoryImplTest {
    @Autowired
    SimpleCollectionTypeRepository repo;

    @Autowired
    JdbcTemplate template;

    Long enLocaleId;
    Long esLocaleId;


    SimpleCollectionTypeImpl.Builder newSimpleCollectionType1aBuilder;
    SimpleCollectionType newSimpleCollectionType1aChild;
    SimpleCollectionType newSimpleCollectionType1b;

    SimpleCollectionType newSimpleCollectionType1bDiffLang;

    SimpleCollectionType newSimpleCollectionType2;

    SimpleCollectionType newSimpleCollectionType1aUpdated;

    @BeforeEach
    public void setup() {
        
        this.enLocaleId = template.query("SELECT id FROM kiosk_locale WHERE abbrev = ?", new IdRowMapper(), "EN").get(0);
        this.esLocaleId = template.query("SELECT id FROM kiosk_locale WHERE abbrev = ?", new IdRowMapper(), "SP").get(0);
        

        LocalDate testDate = LocalDate.of(2023, 9, 12);
        LocalDateTime testDateTime = LocalDateTime.of(2023, 1, 9, 9, 30);

        this.newSimpleCollectionType1aChild = new SimpleCollectionTypeImpl.Builder()
                                            .type("OTHER")
                                            .collectionNameField(new CollectionNameField("coll_nf_fieldName_1aChild", "coll_nf_fieldValue_1aChild"))
                                            .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                            .build();

        this.newSimpleCollectionType1aBuilder = new SimpleCollectionTypeImpl.Builder()
                                            .type("TEST")
                                            .collectionNameField(new CollectionNameField("coll_nf_fieldName_1a", "coll_nf_fieldValue_1a"))
                                            .addTextField(new TextField("tf_fieldType_1a", "tf_fieldName_1a", "tf_fieldValue_1a"))
                                            .addTextField(new TextField("tf_fieldType_1a_2", "tf_fieldName_1a_2", "tf_fieldValue_1a_2"))
                                            .addNumericField(new NumericField("nf_fieldType_1a", "nf_fieldName_1a", 34L))
                                            .addNumericField(new NumericField("nf_fieldType_1a_2", "nf_fieldName_1a_2", 37L))
                                            .addBooleanField(new BooleanField("bf_fieldType_1a", "bf_fieldName_1a", true))
                                            .addBooleanField(new BooleanField("bf_fieldType_1a_2", "bf_fieldName_1a_2", true))
                                            .addImageField(new ImageField("if_fieldType_1a", "if_fieldName_1a", new Image("url_1a", 1, 2)))
                                            .addImageField(new ImageField("if_fieldType_1a_2", "if_fieldName_1a_2", new Image("url_1a_2", 3, 4)))
                                            .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime));

        this.newSimpleCollectionType1b = new SimpleCollectionTypeImpl.Builder()
                                            .type("TEST")
                                            .collectionNameField(new CollectionNameField("coll_nf_fieldName_1b", "coll_nf_fieldValue_1b"))
                                            .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                            .build();

        this.newSimpleCollectionType1bDiffLang = new SimpleCollectionTypeImpl.Builder()
                                            .type("TEST")
                                            .collectionNameField(new CollectionNameField("coll_nf_fieldName_1b_ES", "coll_nf_fieldValue_1b_ES"))
                                            .collectionInternals(new CollectionInternals(esLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                            .build();

        this.newSimpleCollectionType2 = new SimpleCollectionTypeImpl.Builder()
                                            .type("TEST2")
                                            .collectionNameField(new CollectionNameField("coll_nf_fieldName_2", "coll_nf_fieldValue_2"))
                                            .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                            .build();
                                        
        this.newSimpleCollectionType1aUpdated = new SimpleCollectionTypeImpl.Builder()
                                            .type("TEST")
                                            .collectionNameField(new CollectionNameField("coll_nf_fieldName_1a_UPD", "coll_nf_fieldValue_1a_UPD"))
                                            .addTextField(new TextField("tf_fieldType_1a_UPD", "tf_fieldName_1a_UPD", "tf_fieldValue_1a_UPD"))
                                            .addNumericField(new NumericField("nf_fieldType_1a_UPD", "nf_fieldName_1a_UPD", 60L))
                                            .addBooleanField(new BooleanField("bf_fieldType_1a_UPD", "bf_fieldName_1a_UPD", false))
                                            .addImageField(new ImageField("if_fieldType_1a_UPD", "if_fieldName_1a_UPD", new Image("url_1a_UPD", 3, 4)))
                                            .collectionInternals(new CollectionInternals(enLocaleId, PageStatus.PUBLISHED, testDate, testDateTime))
                                            .build();

        
    }

    @Test
    public void givenNewSimpleCollType_saveAndFind_success() {

        SimpleCollectionType savedChild = repo.saveInstance(newSimpleCollectionType1aChild);

        SimpleCollectionType newSimpleCollectionType1a = newSimpleCollectionType1aBuilder.addLinkedCollectionField(new LinkedCollectionField("linkf_fieldType_1a", "linkf_fieldName_1a", savedChild.getId()))
                                                                                        .build();

        repo.saveInstance(newSimpleCollectionType1a);

        List<SimpleCollectionType> savedCollectionTypes = repo.findByCollectionTypeAndLocale(newSimpleCollectionType1a.getType(), "EN");
        Assertions.assertEquals(1, savedCollectionTypes.size());

        SimpleCollectionType savedCollectionType = savedCollectionTypes.get(0);
        CollectionInternals savedCollectionInternals = savedCollectionType.getCollectionInternals();
        
        Assertions.assertNotNull(savedCollectionType.getId());
        Assertions.assertEquals(newSimpleCollectionType1a.getType(), savedCollectionType.getType());

        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionNameField().getFieldName(), savedCollectionType.getCollectionNameField().getFieldName());
        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionInternals().getKioskLocaleId(), savedCollectionInternals.getKioskLocaleId());
        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionInternals().getStatus(), savedCollectionInternals.getStatus());
        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionInternals().getCreatedOn(), savedCollectionInternals.getCreatedOn());
        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionInternals().getLastModified(), savedCollectionInternals.getLastModified());


        Assertions.assertEquals(2, savedCollectionType.getImageFields().size());
        Assertions.assertTrue(savedCollectionType.getImageFields().get(0).getFieldType().equals("if_fieldType_1a") || savedCollectionType.getImageFields().get(1).getFieldType().equals("if_fieldType_1a"));
        ImageField imageField1 = savedCollectionType.getImageFields().get(0).getFieldType().equals("if_fieldType_1a") ? savedCollectionType.getImageFields().get(0) : savedCollectionType.getImageFields().get(1);
        ImageField imageField2 = savedCollectionType.getImageFields().get(0).getFieldType().equals("if_fieldType_1a_2") ? savedCollectionType.getImageFields().get(0) : savedCollectionType.getImageFields().get(1);
        Assertions.assertEquals("if_fieldType_1a", imageField1.getFieldType());
        Assertions.assertEquals("if_fieldName_1a", imageField1.getFieldName());
        Assertions.assertEquals(new Image("url_1a", 1, 2), imageField1.getFieldValue());
        Assertions.assertEquals("if_fieldType_1a_2", imageField2.getFieldType());
        Assertions.assertEquals("if_fieldName_1a_2", imageField2.getFieldName());
        Assertions.assertEquals(new Image("url_1a_2", 3, 4), imageField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.getNumericFields().size());
        Assertions.assertTrue(savedCollectionType.getNumericFields().get(0).getFieldType().equals("nf_fieldType_1a") || savedCollectionType.getNumericFields().get(1).getFieldType().equals("nf_fieldType_1a"));
        NumericField numField1 = savedCollectionType.getNumericFields().get(0).getFieldType().equals("nf_fieldType_1a") ? savedCollectionType.getNumericFields().get(0) : savedCollectionType.getNumericFields().get(1);
        NumericField numField2 = savedCollectionType.getNumericFields().get(0).getFieldType().equals("nf_fieldType_1a_2") ? savedCollectionType.getNumericFields().get(0) : savedCollectionType.getNumericFields().get(1);
        Assertions.assertEquals("nf_fieldType_1a", numField1.getFieldType());
        Assertions.assertEquals("nf_fieldName_1a", numField1.getFieldName());
        Assertions.assertEquals(34L, numField1.getFieldValue());
        Assertions.assertEquals("nf_fieldType_1a_2", numField2.getFieldType());
        Assertions.assertEquals("nf_fieldName_1a_2", numField2.getFieldName());
        Assertions.assertEquals(37L, numField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.getBooleanFields().size());
        Assertions.assertTrue(savedCollectionType.getBooleanFields().get(0).getFieldType().equals("bf_fieldType_1a") || savedCollectionType.getBooleanFields().get(1).getFieldType().equals("bf_fieldType_1a"));
        BooleanField boolField1 = savedCollectionType.getBooleanFields().get(0).getFieldType().equals("bf_fieldType_1a") ? savedCollectionType.getBooleanFields().get(0) : savedCollectionType.getBooleanFields().get(1);
        BooleanField boolField2 = savedCollectionType.getBooleanFields().get(0).getFieldType().equals("bf_fieldType_1a_2") ? savedCollectionType.getBooleanFields().get(0) : savedCollectionType.getBooleanFields().get(1);
        Assertions.assertEquals("bf_fieldType_1a", boolField1.getFieldType());
        Assertions.assertEquals("bf_fieldName_1a", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());
        Assertions.assertEquals("bf_fieldType_1a_2", boolField2.getFieldType());
        Assertions.assertEquals("bf_fieldName_1a_2", boolField2.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.getTextFields().size());
        Assertions.assertTrue(savedCollectionType.getTextFields().get(0).getFieldType().equals("tf_fieldType_1a") || savedCollectionType.getTextFields().get(1).getFieldType().equals("tf_fieldType_1a"));
        TextField textField1 = savedCollectionType.getTextFields().get(0).getFieldType().equals("tf_fieldType_1a") ? savedCollectionType.getTextFields().get(0) : savedCollectionType.getTextFields().get(1);
        TextField textField2 = savedCollectionType.getTextFields().get(0).getFieldType().equals("tf_fieldType_1a_2") ? savedCollectionType.getTextFields().get(0) : savedCollectionType.getTextFields().get(1);
        Assertions.assertEquals("tf_fieldType_1a", textField1.getFieldType());
        Assertions.assertEquals("tf_fieldName_1a", textField1.getFieldName());
        Assertions.assertEquals("tf_fieldValue_1a", textField1.getFieldValue());
        Assertions.assertEquals("tf_fieldType_1a_2", textField2.getFieldType());
        Assertions.assertEquals("tf_fieldName_1a_2", textField2.getFieldName());
        Assertions.assertEquals("tf_fieldValue_1a_2", textField2.getFieldValue());

        Assertions.assertEquals(1, savedCollectionType.getLinkedCollectionFields().size());
        LinkedCollectionField linkedCollField = savedCollectionType.getLinkedCollectionFields().get(0);
        Assertions.assertEquals("linkf_fieldType_1a", linkedCollField.getFieldType());
        Assertions.assertEquals("linkf_fieldName_1a", linkedCollField.getFieldName());
        Assertions.assertEquals(savedChild.getId(), linkedCollField.getFieldValue());

    }

    @Test
    public void givenNewSimpleCollTypeDiffLang_saveAndFind_success() {


        repo.saveInstance(newSimpleCollectionType1b);
        repo.saveInstance(newSimpleCollectionType1bDiffLang);

        List<SimpleCollectionType> savedCollectionTypes = repo.findByCollectionTypeAndLocale(newSimpleCollectionType1b.getType(), "EN");
        Assertions.assertEquals(1, savedCollectionTypes.size());

        SimpleCollectionType savedCollectionType = savedCollectionTypes.get(0);
        CollectionInternals savedCollectionInternals = savedCollectionType.getCollectionInternals();
        
        Assertions.assertNotNull(savedCollectionType.getId());
        Assertions.assertEquals("TEST", savedCollectionType.getType());

        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionNameField().getFieldName(), savedCollectionType.getCollectionNameField().getFieldName());
        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionInternals().getKioskLocaleId(), savedCollectionInternals.getKioskLocaleId());
        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionInternals().getStatus(), savedCollectionInternals.getStatus());
        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionInternals().getCreatedOn(), savedCollectionInternals.getCreatedOn());
        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionInternals().getLastModified(), savedCollectionInternals.getLastModified());


        Assertions.assertTrue(savedCollectionType.getImageFields().isEmpty());
        Assertions.assertTrue(savedCollectionType.getLinkedCollectionFields().isEmpty());
        Assertions.assertTrue(savedCollectionType.getNumericFields().isEmpty());
        Assertions.assertTrue(savedCollectionType.getBooleanFields().isEmpty());
        Assertions.assertTrue(savedCollectionType.getTextFields().isEmpty());
        
    }

    @Test
    public void givenNewSimpleCollTypeDiffType_saveAndFind_success() {

        repo.saveInstance(newSimpleCollectionType1b);
        repo.saveInstance(newSimpleCollectionType2);

        List<SimpleCollectionType> savedCollectionTypes = repo.findByCollectionTypeAndLocale(newSimpleCollectionType1b.getType(), "EN");
        Assertions.assertEquals(1, savedCollectionTypes.size());

        SimpleCollectionType savedCollectionType = savedCollectionTypes.get(0);
        CollectionInternals savedCollectionInternals = savedCollectionType.getCollectionInternals();
        
        Assertions.assertNotNull(savedCollectionType.getId());
        Assertions.assertEquals("TEST", savedCollectionType.getType());

        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionNameField().getFieldName(), savedCollectionType.getCollectionNameField().getFieldName());
        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionInternals().getKioskLocaleId(), savedCollectionInternals.getKioskLocaleId());
        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionInternals().getStatus(), savedCollectionInternals.getStatus());
        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionInternals().getCreatedOn(), savedCollectionInternals.getCreatedOn());
        Assertions.assertEquals(newSimpleCollectionType1b.getCollectionInternals().getLastModified(), savedCollectionInternals.getLastModified());


        Assertions.assertTrue(savedCollectionType.getImageFields().isEmpty());
        Assertions.assertTrue(savedCollectionType.getLinkedCollectionFields().isEmpty());
        Assertions.assertTrue(savedCollectionType.getNumericFields().isEmpty());
        Assertions.assertTrue(savedCollectionType.getBooleanFields().isEmpty());
        Assertions.assertTrue(savedCollectionType.getTextFields().isEmpty());

    }

    @Test
    public void givenNewSimpleCollTypeSameType_saveAndFind_success() {

        repo.saveInstance(newSimpleCollectionType1aBuilder.build());
        repo.saveInstance(newSimpleCollectionType1b);

        List<SimpleCollectionType> savedCollectionTypes = repo.findByCollectionTypeAndLocale(newSimpleCollectionType1b.getType(), "EN");
        Assertions.assertEquals(2, savedCollectionTypes.size());

    }

    @Test
    public void givenSimpleCollType_saveAndDeleteByCollTypeAndLocale_success() {
        repo.saveInstance(newSimpleCollectionType1aBuilder.build());
        repo.saveInstance(newSimpleCollectionType1b);
        repo.saveInstance(newSimpleCollectionType1bDiffLang);

        repo.deleteCollectionTypeInstancesWithLocale("TEST", "EN");

        Assertions.assertTrue(repo.findByCollectionTypeAndLocale("TEST", "EN").isEmpty());

        List<SimpleCollectionType> otherCollTypes = repo.findByCollectionTypeAndLocale("TEST", "SP");
        Assertions.assertEquals(1, otherCollTypes.size());

    }

    @Test
    public void givenSimpleCollType_saveAndDeleteInstance_success() {
        SimpleCollectionType savedCollectionType = repo.saveInstance(newSimpleCollectionType1aBuilder.build());
        repo.saveInstance(newSimpleCollectionType1b);
        repo.saveInstance(newSimpleCollectionType1bDiffLang);

        repo.deleteCollectionInstance(savedCollectionType.getId());

        List<SimpleCollectionType> otherCollTypes = repo.findByCollectionTypeAndLocale("TEST", "EN");

        Assertions.assertEquals(1, otherCollTypes.size());

        Assertions.assertEquals("coll_nf_fieldName_1b", otherCollTypes.get(0).getCollectionNameField().getFieldName());

    }

    @Test
    public void givenSimpleCollType_doesInstanceOfCollExist_true() {
        SimpleCollectionType savedCollectionType = repo.saveInstance(newSimpleCollectionType1aBuilder.build());
        repo.saveInstance(newSimpleCollectionType1b);
        repo.saveInstance(newSimpleCollectionType1bDiffLang);

        repo.deleteCollectionInstance(savedCollectionType.getId());

        Boolean doesInstanceOfCollectionExist = repo.doesInstanceOfCollectionExist("TEST", "EN");

        Assertions.assertTrue(doesInstanceOfCollectionExist);
    }

    @Test
    public void afterDeleteAllSimpleCollType_doesInstanceOfCollExist_false() {
        SimpleCollectionType savedCollectionType = repo.saveInstance(newSimpleCollectionType1aBuilder.build());
        SimpleCollectionType savedCollectionType2 = repo.saveInstance(newSimpleCollectionType1b);
        repo.saveInstance(newSimpleCollectionType1bDiffLang);

        repo.deleteCollectionInstance(savedCollectionType.getId());
        repo.deleteCollectionInstance(savedCollectionType2.getId());

        Boolean doesInstanceOfCollectionExist = repo.doesInstanceOfCollectionExist("TEST", "EN");

        Assertions.assertFalse(doesInstanceOfCollectionExist);
    }

    @Test
    public void givenUpdatedSimpleCollType_update_success() {

        SimpleCollectionType savedChild = repo.saveInstance(newSimpleCollectionType1aChild);

        SimpleCollectionType oldCollectionType = repo.saveInstance(newSimpleCollectionType1b);

        SimpleCollectionType newSimpleCollectionType1a = newSimpleCollectionType1aBuilder.addLinkedCollectionField(new LinkedCollectionField("linkf_fieldType_1a", "linkf_fieldName_1a", savedChild.getId()))
                                                                                        .build();

        repo.updateInstance(oldCollectionType.getId(), newSimpleCollectionType1a);

        List<SimpleCollectionType> savedCollectionTypes = repo.findByCollectionTypeAndLocale(newSimpleCollectionType1a.getType(), "EN");
        Assertions.assertEquals(1, savedCollectionTypes.size());

        SimpleCollectionType savedCollectionType = savedCollectionTypes.get(0);
        CollectionInternals savedCollectionInternals = savedCollectionType.getCollectionInternals();
        
        Assertions.assertNotNull(savedCollectionType.getId());
        Assertions.assertEquals(newSimpleCollectionType1a.getType(), savedCollectionType.getType());

        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionNameField().getFieldName(), savedCollectionType.getCollectionNameField().getFieldName());
        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionInternals().getKioskLocaleId(), savedCollectionInternals.getKioskLocaleId());
        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionInternals().getStatus(), savedCollectionInternals.getStatus());
        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionInternals().getCreatedOn(), savedCollectionInternals.getCreatedOn());
        Assertions.assertEquals(newSimpleCollectionType1a.getCollectionInternals().getLastModified(), savedCollectionInternals.getLastModified());


        Assertions.assertEquals(2, savedCollectionType.getImageFields().size());
        Assertions.assertTrue(savedCollectionType.getImageFields().get(0).getFieldType().equals("if_fieldType_1a") || savedCollectionType.getImageFields().get(1).getFieldType().equals("if_fieldType_1a"));
        ImageField imageField1 = savedCollectionType.getImageFields().get(0).getFieldType().equals("if_fieldType_1a") ? savedCollectionType.getImageFields().get(0) : savedCollectionType.getImageFields().get(1);
        ImageField imageField2 = savedCollectionType.getImageFields().get(0).getFieldType().equals("if_fieldType_1a_2") ? savedCollectionType.getImageFields().get(0) : savedCollectionType.getImageFields().get(1);
        Assertions.assertEquals("if_fieldType_1a", imageField1.getFieldType());
        Assertions.assertEquals("if_fieldName_1a", imageField1.getFieldName());
        Assertions.assertEquals(new Image("url_1a", 1, 2), imageField1.getFieldValue());
        Assertions.assertEquals("if_fieldType_1a_2", imageField2.getFieldType());
        Assertions.assertEquals("if_fieldName_1a_2", imageField2.getFieldName());
        Assertions.assertEquals(new Image("url_1a_2", 3, 4), imageField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.getNumericFields().size());
        Assertions.assertTrue(savedCollectionType.getNumericFields().get(0).getFieldType().equals("nf_fieldType_1a") || savedCollectionType.getNumericFields().get(1).getFieldType().equals("nf_fieldType_1a"));
        NumericField numField1 = savedCollectionType.getNumericFields().get(0).getFieldType().equals("nf_fieldType_1a") ? savedCollectionType.getNumericFields().get(0) : savedCollectionType.getNumericFields().get(1);
        NumericField numField2 = savedCollectionType.getNumericFields().get(0).getFieldType().equals("nf_fieldType_1a_2") ? savedCollectionType.getNumericFields().get(0) : savedCollectionType.getNumericFields().get(1);
        Assertions.assertEquals("nf_fieldType_1a", numField1.getFieldType());
        Assertions.assertEquals("nf_fieldName_1a", numField1.getFieldName());
        Assertions.assertEquals(34L, numField1.getFieldValue());
        Assertions.assertEquals("nf_fieldType_1a_2", numField2.getFieldType());
        Assertions.assertEquals("nf_fieldName_1a_2", numField2.getFieldName());
        Assertions.assertEquals(37L, numField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.getBooleanFields().size());
        Assertions.assertTrue(savedCollectionType.getBooleanFields().get(0).getFieldType().equals("bf_fieldType_1a") || savedCollectionType.getBooleanFields().get(1).getFieldType().equals("bf_fieldType_1a"));
        BooleanField boolField1 = savedCollectionType.getBooleanFields().get(0).getFieldType().equals("bf_fieldType_1a") ? savedCollectionType.getBooleanFields().get(0) : savedCollectionType.getBooleanFields().get(1);
        BooleanField boolField2 = savedCollectionType.getBooleanFields().get(0).getFieldType().equals("bf_fieldType_1a_2") ? savedCollectionType.getBooleanFields().get(0) : savedCollectionType.getBooleanFields().get(1);
        Assertions.assertEquals("bf_fieldType_1a", boolField1.getFieldType());
        Assertions.assertEquals("bf_fieldName_1a", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());
        Assertions.assertEquals("bf_fieldType_1a_2", boolField2.getFieldType());
        Assertions.assertEquals("bf_fieldName_1a_2", boolField2.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.getTextFields().size());
        Assertions.assertTrue(savedCollectionType.getTextFields().get(0).getFieldType().equals("tf_fieldType_1a") || savedCollectionType.getTextFields().get(1).getFieldType().equals("tf_fieldType_1a"));
        TextField textField1 = savedCollectionType.getTextFields().get(0).getFieldType().equals("tf_fieldType_1a") ? savedCollectionType.getTextFields().get(0) : savedCollectionType.getTextFields().get(1);
        TextField textField2 = savedCollectionType.getTextFields().get(0).getFieldType().equals("tf_fieldType_1a_2") ? savedCollectionType.getTextFields().get(0) : savedCollectionType.getTextFields().get(1);
        Assertions.assertEquals("tf_fieldType_1a", textField1.getFieldType());
        Assertions.assertEquals("tf_fieldName_1a", textField1.getFieldName());
        Assertions.assertEquals("tf_fieldValue_1a", textField1.getFieldValue());
        Assertions.assertEquals("tf_fieldType_1a_2", textField2.getFieldType());
        Assertions.assertEquals("tf_fieldName_1a_2", textField2.getFieldName());
        Assertions.assertEquals("tf_fieldValue_1a_2", textField2.getFieldValue());

        Assertions.assertEquals(1, savedCollectionType.getLinkedCollectionFields().size());
        LinkedCollectionField linkedCollField = savedCollectionType.getLinkedCollectionFields().get(0);
        Assertions.assertEquals("linkf_fieldType_1a", linkedCollField.getFieldType());
        Assertions.assertEquals("linkf_fieldName_1a", linkedCollField.getFieldName());
        Assertions.assertEquals(savedChild.getId(), linkedCollField.getFieldValue());

    }


    class IdRowMapper implements RowMapper<Long>{
    
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        
            return rs.getLong("id");

        }
    }

    
}