package com.ppublica.apps.kiosk.repository.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;

public class SimpleCollectionTypeQueryResultsAdapter implements SimpleCollectionType {
    private SimpleCollectionTypeQueryResults simpleCollectionTypeResult;
    private List<TextField> textFields;
    private List<NumericField> numericFields;
    private List<ImageField> imageFields;
    List<LinkedCollectionField> linkedCollectionFields;

    public SimpleCollectionTypeQueryResultsAdapter(SimpleCollectionTypeQueryResults simpleCollectionTypeResult, List<TextField> textFields, 
                                                    List<NumericField> numericFields, List<ImageField> imageFields, List<LinkedCollectionField> linkedCollectionFields) {
        this.simpleCollectionTypeResult = simpleCollectionTypeResult;
        this.textFields = textFields;
        this.numericFields = numericFields;
        this.imageFields = imageFields;
        this.linkedCollectionFields = linkedCollectionFields;
    }

    @Override
    public SimpleCollectionType withId(Long id) {
        return new SimpleCollectionTypeImpl(id,simpleCollectionTypeResult.getType(), simpleCollectionTypeResult.getCollectionNameField(), 
                                                textFields, numericFields, imageFields, linkedCollectionFields, simpleCollectionTypeResult.getCollectionInternals());
    }

    @Override
    public Long getId() {
        return simpleCollectionTypeResult.getId();
    }

    @Override
    public String getType() {
        return simpleCollectionTypeResult.getType();
    }

    @Override
    public CollectionNameField getCollectionNameField() {
        return simpleCollectionTypeResult.getCollectionNameField();
    }

    @Override
    public List<TextField> getTextFields() {
        return this.textFields;
    }

    @Override
    public List<NumericField> getNumericFields() {
        return this.numericFields;
    }

    @Override
    public List<ImageField> getImageFields() {
        return this.imageFields;
    }

    @Override
    public List<LinkedCollectionField> getLinkedCollectionFields() {
        return this.linkedCollectionFields;
    }

    @Override
    public CollectionInternals getCollectionInternals() {
        return simpleCollectionTypeResult.getCollectionInternals();
    }

    
    
}
