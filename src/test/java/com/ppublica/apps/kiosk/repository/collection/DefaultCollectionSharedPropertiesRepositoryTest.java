package com.ppublica.apps.kiosk.repository.collection;

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
import org.springframework.test.context.TestPropertySource;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionRelationship;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

@JdbcTest
@TestPropertySource("classpath:test.properties")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Import(DefaultCollectionSharedPropertiesRepository.class)
public class DefaultCollectionSharedPropertiesRepositoryTest {
    @Autowired
    CollectionSharedPropertiesRepository repo;

    @Autowired
    JdbcTemplate template;

    CollectionSharedPropertiesImpl.Builder newCollectionBuilder;
    CollectionSharedProperties relatedCollection1;
    CollectionSharedProperties relatedCollection2;
    //SimpleCollectionType newSimpleCollectionType1b;

    //SimpleCollectionType newSimpleCollectionType1bDiffLang;

    //SimpleCollectionType newSimpleCollectionType2;

    //SimpleCollectionType newSimpleCollectionType1aUpdated;

    @BeforeEach
    public void setup() {

        LocalDate testDate = LocalDate.of(2023, 9, 12);
        LocalDateTime testDateTime = LocalDateTime.of(2023, 1, 9, 9, 30);

        this.relatedCollection1 = new CollectionSharedPropertiesImpl.Builder("OTHER")
                                            .collectionNameField(new CollectionNameField("coll_nf_fieldName_related", "coll_nf_fieldValue_related"))
                                            .collectionSharedInternals(new CollectionSharedInternals(PageStatus.PUBLISHED, testDate, testDateTime))
                                            .build();

        this.relatedCollection2 = new CollectionSharedPropertiesImpl.Builder("OTHER_2")
                                            .collectionNameField(new CollectionNameField("coll_nf_fieldName_related_2", "coll_nf_fieldValue_related_2"))
                                            .collectionSharedInternals(new CollectionSharedInternals(PageStatus.DRAFT, testDate, testDateTime))
                                            .build();

        this.newCollectionBuilder = new CollectionSharedPropertiesImpl.Builder("TEST")
                                            .subType("TEST_subType")
                                            .collectionNameField(new CollectionNameField("coll_nf_fieldName_1a", "coll_nf_fieldValue_1a"))
                                            .addTextField(new TextField("tf_fieldType_1a", "tf_fieldName_1a", "tf_fieldValue_1a"))
                                            .addTextField(new TextField("tf_fieldType_1a_2", "tf_fieldName_1a_2", "tf_fieldValue_1a_2"))
                                            .addNumericField(new NumericField("nf_fieldType_1a", "nf_fieldName_1a", 34L))
                                            .addNumericField(new NumericField("nf_fieldType_1a_2", "nf_fieldName_1a_2", 37L))
                                            .addBooleanField(new BooleanField("bf_fieldType_1a", "bf_fieldName_1a", true))
                                            .addBooleanField(new BooleanField("bf_fieldType_1a_2", "bf_fieldName_1a_2", true))
                                            .addImageField(new ImageField("if_fieldType_1a", "if_fieldName_1a", new Image("url_1a", 1, 2)))
                                            .addImageField(new ImageField("if_fieldType_1a_2", "if_fieldName_1a_2", new Image("url_1a_2", 3, 4)))
                                            .collectionSharedInternals(new CollectionSharedInternals(PageStatus.PUBLISHED, testDate, testDateTime));
/*
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

         */
    }


    @Test
    public void givenNewSharedProps_saveAndFind_success() {

        CollectionSharedProperties savedRelatedCollection1 = repo.saveInstance(relatedCollection1);
        CollectionSharedProperties savedRelatedCollection2 = repo.saveInstance(relatedCollection2);

        CollectionSharedProperties newCollection = newCollectionBuilder
                            .addLinkedCollectionField(new LinkedCollectionField("linkf_fieldType_1a", "linkf_fieldName_1a", savedRelatedCollection1.id()))
                            .addLinkedCollectionField(new LinkedCollectionField("linkf_fieldType_1a_2", "linkf_fieldName_1a_2", savedRelatedCollection2.id()))
                            .addCollectionRelationship(new CollectionRelationship(savedRelatedCollection1.id(), "coll_rel_type_1a"))
                            .addCollectionRelationship(new CollectionRelationship(savedRelatedCollection2.id(), "coll_rel_type_1a_2"))
                            .build();

        repo.saveInstance(newCollection);

        List<CollectionSharedProperties> savedCollections = repo.findByCollectionType(newCollection.type(), newCollection.subType());
        Assertions.assertEquals(1, savedCollections.size());

        CollectionSharedProperties savedCollectionType = savedCollections.get(0);
        CollectionSharedInternals savedCollectionInternals = savedCollectionType.collectionSharedInternals();
        
        Assertions.assertNotNull(savedCollectionType.id());
        Assertions.assertEquals(newCollection.type(), savedCollectionType.type());
        Assertions.assertEquals(newCollection.subType(), savedCollectionType.subType());

        Assertions.assertEquals(newCollection.collectionSharedInternals().getStatus(), savedCollectionInternals.getStatus());
        Assertions.assertEquals(newCollection.collectionSharedInternals().getCreatedOn(), savedCollectionInternals.getCreatedOn());
        Assertions.assertEquals(newCollection.collectionSharedInternals().getLastModified(), savedCollectionInternals.getLastModified());

        Assertions.assertEquals(newCollection.collectionNameField().getFieldName(), savedCollectionType.collectionNameField().getFieldName());


        Assertions.assertEquals(2, savedCollectionType.textFields().size());
        Assertions.assertTrue(savedCollectionType.textFields().get(0).getFieldType().equals("tf_fieldType_1a") || savedCollectionType.textFields().get(1).getFieldType().equals("tf_fieldType_1a"));
        TextField textField1 = savedCollectionType.textFields().get(0).getFieldType().equals("tf_fieldType_1a") ? savedCollectionType.textFields().get(0) : savedCollectionType.textFields().get(1);
        TextField textField2 = savedCollectionType.textFields().get(0).getFieldType().equals("tf_fieldType_1a_2") ? savedCollectionType.textFields().get(0) : savedCollectionType.textFields().get(1);
        Assertions.assertEquals("tf_fieldType_1a", textField1.getFieldType());
        Assertions.assertEquals("tf_fieldName_1a", textField1.getFieldName());
        Assertions.assertEquals("tf_fieldValue_1a", textField1.getFieldValue());
        Assertions.assertEquals("tf_fieldType_1a_2", textField2.getFieldType());
        Assertions.assertEquals("tf_fieldName_1a_2", textField2.getFieldName());
        Assertions.assertEquals("tf_fieldValue_1a_2", textField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.numericFields().size());
        Assertions.assertTrue(savedCollectionType.numericFields().get(0).getFieldType().equals("nf_fieldType_1a") || savedCollectionType.numericFields().get(1).getFieldType().equals("nf_fieldType_1a"));
        NumericField numField1 = savedCollectionType.numericFields().get(0).getFieldType().equals("nf_fieldType_1a") ? savedCollectionType.numericFields().get(0) : savedCollectionType.numericFields().get(1);
        NumericField numField2 = savedCollectionType.numericFields().get(0).getFieldType().equals("nf_fieldType_1a_2") ? savedCollectionType.numericFields().get(0) : savedCollectionType.numericFields().get(1);
        Assertions.assertEquals("nf_fieldType_1a", numField1.getFieldType());
        Assertions.assertEquals("nf_fieldName_1a", numField1.getFieldName());
        Assertions.assertEquals(34L, numField1.getFieldValue());
        Assertions.assertEquals("nf_fieldType_1a_2", numField2.getFieldType());
        Assertions.assertEquals("nf_fieldName_1a_2", numField2.getFieldName());
        Assertions.assertEquals(37L, numField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.booleanFields().size());
        Assertions.assertTrue(savedCollectionType.booleanFields().get(0).getFieldType().equals("bf_fieldType_1a") || savedCollectionType.booleanFields().get(1).getFieldType().equals("bf_fieldType_1a"));
        BooleanField boolField1 = savedCollectionType.booleanFields().get(0).getFieldType().equals("bf_fieldType_1a") ? savedCollectionType.booleanFields().get(0) : savedCollectionType.booleanFields().get(1);
        BooleanField boolField2 = savedCollectionType.booleanFields().get(0).getFieldType().equals("bf_fieldType_1a_2") ? savedCollectionType.booleanFields().get(0) : savedCollectionType.booleanFields().get(1);
        Assertions.assertEquals("bf_fieldType_1a", boolField1.getFieldType());
        Assertions.assertEquals("bf_fieldName_1a", boolField1.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());
        Assertions.assertEquals("bf_fieldType_1a_2", boolField2.getFieldType());
        Assertions.assertEquals("bf_fieldName_1a_2", boolField2.getFieldName());
        Assertions.assertEquals(true, boolField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.imageFields().size());
        Assertions.assertTrue(savedCollectionType.imageFields().get(0).getFieldType().equals("if_fieldType_1a") || savedCollectionType.imageFields().get(1).getFieldType().equals("if_fieldType_1a"));
        ImageField imageField1 = savedCollectionType.imageFields().get(0).getFieldType().equals("if_fieldType_1a") ? savedCollectionType.imageFields().get(0) : savedCollectionType.imageFields().get(1);
        ImageField imageField2 = savedCollectionType.imageFields().get(0).getFieldType().equals("if_fieldType_1a_2") ? savedCollectionType.imageFields().get(0) : savedCollectionType.imageFields().get(1);
        Assertions.assertEquals("if_fieldType_1a", imageField1.getFieldType());
        Assertions.assertEquals("if_fieldName_1a", imageField1.getFieldName());
        Assertions.assertEquals(new Image("url_1a", 1, 2), imageField1.getFieldValue());
        Assertions.assertEquals("if_fieldType_1a_2", imageField2.getFieldType());
        Assertions.assertEquals("if_fieldName_1a_2", imageField2.getFieldName());
        Assertions.assertEquals(new Image("url_1a_2", 3, 4), imageField2.getFieldValue());


        Assertions.assertEquals(2, savedCollectionType.linkedCollectionFields().size());
        Assertions.assertTrue(savedCollectionType.linkedCollectionFields().get(0).getFieldType().equals("linkf_fieldType_1a") || savedCollectionType.linkedCollectionFields().get(1).getFieldType().equals("linkf_fieldType_1a"));
        LinkedCollectionField linkedCollField1 = savedCollectionType.linkedCollectionFields().get(0).getFieldType().equals("linkf_fieldType_1a") ? savedCollectionType.linkedCollectionFields().get(0) : savedCollectionType.linkedCollectionFields().get(1);
        LinkedCollectionField linkedCollField2 = savedCollectionType.linkedCollectionFields().get(0).getFieldType().equals("linkf_fieldType_1a_2") ? savedCollectionType.linkedCollectionFields().get(0) : savedCollectionType.linkedCollectionFields().get(1);
        Assertions.assertEquals("linkf_fieldType_1a", linkedCollField1.getFieldType());
        Assertions.assertEquals("linkf_fieldName_1a", linkedCollField1.getFieldName());
        Assertions.assertEquals(savedRelatedCollection1.id(), linkedCollField1.getFieldValue());
        Assertions.assertEquals("linkf_fieldType_1a_2", linkedCollField2.getFieldType());
        Assertions.assertEquals("linkf_fieldName_1a_2", linkedCollField2.getFieldName());
        Assertions.assertEquals(savedRelatedCollection2.id(), linkedCollField2.getFieldValue());

        Assertions.assertEquals(2, savedCollectionType.collectionRelationships().size());
        Assertions.assertTrue(savedCollectionType.collectionRelationships().get(0).type().equals("coll_rel_type_1a") || savedCollectionType.collectionRelationships().get(1).type().equals("coll_rel_type_1a"));
        CollectionRelationship collRel1 = savedCollectionType.collectionRelationships().get(0).type().equals("coll_rel_type_1a") ? savedCollectionType.collectionRelationships().get(0) : savedCollectionType.collectionRelationships().get(1);
        CollectionRelationship collRel2 = savedCollectionType.collectionRelationships().get(0).type().equals("coll_rel_type_1a_2") ? savedCollectionType.collectionRelationships().get(0) : savedCollectionType.collectionRelationships().get(1);
        Assertions.assertEquals("coll_rel_type_1a", collRel1.type());
        Assertions.assertEquals(savedRelatedCollection1.id(), collRel1.relatedEntityId());
        Assertions.assertEquals("coll_rel_type_1a_2", collRel2.type());
        Assertions.assertEquals(savedRelatedCollection2.id(), collRel2.relatedEntityId());

    }
}
