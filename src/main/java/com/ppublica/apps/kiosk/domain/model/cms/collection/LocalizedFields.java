package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.ArrayList;
import java.util.List;


public class LocalizedFields {
    private CollectionNameField collectionNameField;
    private List<TextField> textFields;
    private List<NumericField> numericFields;
    private List<BooleanField> booleanFields;
    private CollectionInternals collectionLocalizedInternals;

    public LocalizedFields(CollectionNameField collectionNameField, List<TextField> textFields, List<NumericField> numericFields, List<BooleanField> booleanFields, 
                            CollectionInternals collectionLocalizedInternals) {
        this.collectionNameField = collectionNameField;
        this.textFields = textFields != null ? textFields : new ArrayList<>();
        this.numericFields = numericFields != null ? numericFields : new ArrayList<>();
        this.booleanFields = booleanFields != null ? booleanFields : new ArrayList<>();
        this.collectionLocalizedInternals = collectionLocalizedInternals;
    }

    public CollectionNameField getCollectionNameField() {
        return this.collectionNameField;
    }

    public List<TextField> getTextFields() {
        return this.textFields;
    }

    public List<NumericField> getNumericFields() {
        return this.numericFields;
    }

    public List<BooleanField> getBooleanFields() {
        return this.booleanFields;
    }

    public CollectionInternals getCollectionLocalizedInternals() {
        return this.collectionLocalizedInternals;
    }

}
