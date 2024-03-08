package com.ppublica.apps.kiosk.repository.collection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
import static com.ppublica.apps.kiosk.repository.collection.DefaultCollectionSharedPropertiesRepoSQL.*;

@Repository
public class DefaultCollectionSharedPropertiesRepository implements CollectionSharedPropertiesRepository{

    @Autowired
    private JdbcTemplate template;

    @Override
    public CollectionSharedProperties saveInstance(CollectionSharedProperties collectionInstance) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 1. Insert into collection_shared_properties_impl table
        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_COLL_SHARED_PROPS_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, collectionInstance.type());
            ps.setString(2, collectionInstance.subType());

            return ps;
        }, keyHolder);

        Long savedSharedPropsId = getKey(keyHolder);
        CollectionSharedProperties savedCollSharedProps = collectionInstance.withId(savedSharedPropsId);


        // 2. Insert CollectionSharedInternals
        CollectionSharedInternals collectionInternals = savedCollSharedProps.collectionSharedInternals();
        this.template.update(INSERT_COLL_INTERNALS_TABLE,
                            collectionInternals.getCreatedOn(),
                            collectionInternals.getLastModified(), 
                            collectionInternals.getStatus().toString(),
                            savedSharedPropsId);
            

        // 3. Insert CollectionNameField
        CollectionNameField collNameField = savedCollSharedProps.collectionNameField();
        this.template.update(INSERT_COLL_NAME_FIELD_TABLE,
                                collNameField.getFieldType(),
                                collNameField.getFieldName(),
                                collNameField.getFieldValue(),
                                savedSharedPropsId);

        // 4. Insert TextFields
        List<TextField> textFields = savedCollSharedProps.textFields();
        for (TextField textField : textFields) {
            this.template.update(INSERT_TEXT_FIELD_TABLE,
                                textField.getFieldType(),
                                textField.getFieldName(),
                                textField.getFieldValue(),
                                savedSharedPropsId);
        }
        
        // 5. Insert NumericFields
        List<NumericField> numericFields = savedCollSharedProps.numericFields();
        for (NumericField numericField : numericFields) {
            this.template.update(INSERT_NUMERIC_FIELD_TABLE,
                                numericField.getFieldType(),
                                numericField.getFieldName(),
                                numericField.getFieldValue(),
                                savedSharedPropsId);
        }

        // 6. Insert BooleanFields
        List<BooleanField> booleanFields = savedCollSharedProps.booleanFields();
        for (BooleanField booleanField : booleanFields) {
            this.template.update(INSERT_BOOLEAN_FIELD_TABLE,
                                booleanField.getFieldType(),
                                booleanField.getFieldName(),
                                booleanField.getFieldValue(),
                                savedSharedPropsId);
        }

        // 7. Insert ImageFields
        List<ImageField> imageFields = savedCollSharedProps.imageFields();
        KeyHolder keyHolderIm = new GeneratedKeyHolder();

        Long currentImageFieldId = null;
        Image currentImage = null;
        for (ImageField imageField : imageFields) {
            
            this.template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_IMAGE_FIELD_TABLE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, imageField.getFieldType());
                ps.setString(2, imageField.getFieldName());
                ps.setLong(3, savedSharedPropsId);

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
        List<LinkedCollectionField> linkedCollectionFields = savedCollSharedProps.linkedCollectionFields();
        for (LinkedCollectionField linkedCollectionField : linkedCollectionFields) {
            this.template.update(INSERT_LINKEDCOLL_FIELD_TABLE,
                                linkedCollectionField.getFieldType(),
                                linkedCollectionField.getFieldName(),
                                linkedCollectionField.getFieldValue(),
                                savedSharedPropsId);
        }


        // 9. Insert CollectionRelationships
        List<CollectionRelationship> collectionRelationships = savedCollSharedProps.collectionRelationships();
        for (CollectionRelationship collectionRelationship : collectionRelationships) {
            this.template.update(INSERT_COLLRELL_FIELD_TABLE,
                                savedSharedPropsId,
                                collectionRelationship.relatedEntityId(),
                                collectionRelationship.type());
        }

        return savedCollSharedProps;
    }

    // null subType matches all
    @Override
    public List<CollectionSharedProperties> findByCollectionType(String type, String subType) {
        List<CollectionSharedProperties> collections = new ArrayList<>();
        
        List<CollectionSharedPropertiesQueryResults> collectionResults = subType != null ? findShallowByCollectionType(type, subType) : findShallowByCollectionType(type);

        for(CollectionSharedPropertiesQueryResults collectionResult : collectionResults) {
            collections.add(getFullCollectionSharedProperties(collectionResult));
        }

        return collections;
    }

    @Override
    public CollectionSharedProperties findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void deleteCollectionInstance(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCollectionInstance'");
    }

    @Override
    public boolean doesInstanceOfCollectionExist(String type, String subType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doesInstanceOfCollectionExist'");
    }

    @Override
    public CollectionSharedProperties updateCollectionInstance(Long id, CollectionSharedProperties collectionInstance) {
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

    private List<CollectionSharedPropertiesQueryResults> findShallowByCollectionType(String collectionType) {
        List<CollectionSharedPropertiesQueryResults> shallowCollectionResults = this.template.query(FIND_COLL_TYPE_TABLE_BY_TYPE, new CollectionSharedPropertiesQueryRowMapper(), collectionType);
        
        return shallowCollectionResults;
    }

    private List<CollectionSharedPropertiesQueryResults> findShallowByCollectionType(String collectionType, String collectionSubType) {
        List<CollectionSharedPropertiesQueryResults> shallowCollectionResults = this.template.query(FIND_COLL_TYPE_TABLE_BY_TYPE_AND_SUBTYPE, new CollectionSharedPropertiesQueryRowMapper(), collectionType, collectionSubType);
        
        return shallowCollectionResults;
    }

    private CollectionSharedProperties getFullCollectionSharedProperties(CollectionSharedPropertiesQueryResults collectionResult) {

        Long collectionId = collectionResult.id();

        // Get TextFields
        List<TextField> textFields = template.query(FIND_TEXT_FIELD_TABLE, new TextFieldRowMapper(), collectionId);

        // Get NumericFields
        List<NumericField> numericFields = template.query(FIND_NUMERIC_FIELD_TABLE, new NumericFieldRowMapper(), collectionId);

        // Get BooleanFields
        List<BooleanField> booleanFields = template.query(FIND_BOOLEAN_FIELD_TABLE, new BooleanFieldRowMapper(), collectionId);

        // Get ImageFields
        List<ImageField> imageFields = template.query(FIND_IMAGE_FIELD_TABLE, new ImageQueryRowMapper(), collectionId);

        // Get LinkedCollectionFields
        List<LinkedCollectionField> linkedCollectionFields = template.query(FIND_LINKEDCOLL_FIELD_TABLE, new LinkedCollectionFieldQueryRowMapper(), collectionId);

        // Get CollectionRelationships
        List<CollectionRelationshipQueryResults> collectionRelationshipResults = template.query(FIND_COLL_RELATIONSHIPS_TABLE, new CollectionRelationshipQueryResultsRowMapper(), collectionId, collectionId);
        List<CollectionRelationship> collectionRelationships = collectionRelationshipResults
                                                                    .stream()
                                                                    .map(result -> {
                                                                            Long relatedEntityId = result.matchingColl1().equals(collectionId) ? result.matchingColl2() : result.matchingColl1();
                                                                            return new CollectionRelationship(relatedEntityId, result.type());
                                                                    })
                                                                    .collect(Collectors.toList());

        return new CollectionSharedPropertiesImpl.Builder(collectionResult.type())
                                                    .subType(collectionResult.subType())
                                                    .withId(collectionResult.id())
                                                    .collectionNameField(collectionResult.collectionNameField())
                                                    .textFields(textFields)
                                                    .numericFields(numericFields)
                                                    .booleanFields(booleanFields)
                                                    .imageFields(imageFields)
                                                    .collectionSharedInternals(collectionResult.collectionInternals())
                                                    .linkedCollectionFields(linkedCollectionFields)
                                                    .collectionRelationships(collectionRelationships)
                                                    .build();
        
    }
    
}
