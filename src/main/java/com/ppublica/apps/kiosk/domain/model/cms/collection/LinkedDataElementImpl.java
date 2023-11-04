package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class LinkedDataElementImpl implements LinkedDataElement {

    private Long id;
    private String type;
    private CollectionNameField collectionNameField;
    private List<TextField> textFields;
    private List<NumericField> numericFields;
    private List<BooleanField> booleanFields;
    private LinkedCollectionField linkedCollectionField;
    private DataElementCollectionInternals dataElementCollectionInternals;

    protected LinkedDataElementImpl(Long id, String type, CollectionNameField collectionNameField, List<TextField> textFields, 
                                    List<NumericField> numericFields, List<BooleanField> booleanFields, LinkedCollectionField linkedCollectionField, 
                                    DataElementCollectionInternals dataElementCollectionInternals) {

    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public CollectionNameField getCollectionNameField() {
        return this.collectionNameField;
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
    public List<BooleanField> getBooleanFields() {
        return this.booleanFields;
    }

    @Override
    public DataElementCollectionInternals getDataElementCollectionInternals() {
        return this.dataElementCollectionInternals;
    }

    @Override
    public LinkedCollectionField getLinkedCollectionField() {
        return this.linkedCollectionField;
    }

    public static class Builder {
        private Long id;
        private String type;
        private CollectionNameField collectionNameField;
        private List<TextField> textFields = new ArrayList<>();
        private List<NumericField> numericFields = new ArrayList<>();
        private List<BooleanField> booleanFields = new ArrayList<>();
        private LinkedCollectionField linkedCollectionField;
        private DataElementCollectionInternals dataElementCollectionInternals;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder collectionNameField(CollectionNameField collectionNameField) {
            this.collectionNameField = collectionNameField;
            return this;
        }

        public Builder textFields(List<TextField> textFields) {
            if(textFields == null) {
                throw new RuntimeException("A non-null argument is required");
            }
            this.textFields = textFields;
            return this;
        }

        public Builder addTextField(TextField textField) {
            this.textFields.add(textField);
            return this;
        }

        public Builder numericFields(List<NumericField> numericFields) {
            if(numericFields == null) {
                throw new RuntimeException("A non-null argument is required");
            }
            this.numericFields = numericFields;
            return this;
        }

        public Builder addNumericField(NumericField numericField) {
            this.numericFields.add(numericField);
            return this;
        }

        public Builder booleanFields(List<BooleanField> booleanFields) {
            if(booleanFields == null) {
                throw new RuntimeException("A non-null argument is required");
            }
            this.booleanFields = booleanFields;
            return this;
        }

        public Builder addBooleanField(BooleanField booleanField) {
            this.booleanFields.add(booleanField);
            return this;
        }

        public Builder linkedCollectionField(LinkedCollectionField linkedCollectionField) {
            this.linkedCollectionField = linkedCollectionField;
            return this;
        }

        public Builder collectionInternals(DataElementCollectionInternals dataElementCollectionInternals) {
            this.dataElementCollectionInternals = dataElementCollectionInternals;
            return this;
        }

        public LinkedDataElementImpl build() {

            if(dataElementCollectionInternals == null) {

                dataElementCollectionInternals = new DataElementCollectionInternals(PageStatus.DRAFT, LocalDate.now(), LocalDateTime.now());
            }

            if(type == null) {
                throw new RuntimeException("A collection type is required");
            }

            if(collectionNameField == null) {
                throw new RuntimeException("A CollectionNameField is required");
            }

            return new LinkedDataElementImpl(id, type, collectionNameField, textFields, numericFields, booleanFields, linkedCollectionField, dataElementCollectionInternals);
        }
    }
    
}
