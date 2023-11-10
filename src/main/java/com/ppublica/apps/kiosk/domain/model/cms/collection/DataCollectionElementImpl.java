package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.ArrayList;
import java.util.List;


public class DataCollectionElementImpl implements DataCollectionElement {

    private Long id;
    private String type;
    private List<TextField> textFields;
    private List<NumericField> numericFields;
    private List<BooleanField> booleanFields;
    private LinkedCollectionField parentCollection;

    protected DataCollectionElementImpl(Long id, String type, List<TextField> textFields, 
                                    List<NumericField> numericFields, List<BooleanField> booleanFields, LinkedCollectionField parentCollection) {
        this.id = id;
        this.type = type;
        this.textFields = textFields;
        this.numericFields = numericFields;
        this.booleanFields = booleanFields;

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
    public LinkedCollectionField getParentCollection() {
        return this.parentCollection;
    }

    public static class Builder {
        private Long id;
        private String type;
        private List<TextField> textFields = new ArrayList<>();
        private List<NumericField> numericFields = new ArrayList<>();
        private List<BooleanField> booleanFields = new ArrayList<>();
        private LinkedCollectionField parentCollection;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
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
        
        public Builder parentCollection(LinkedCollectionField parentCollection) {
            this.parentCollection = parentCollection;
            return this;
        }

        public DataCollectionElementImpl build() {

            if(type == null) {
                throw new RuntimeException("A collection type is required");
            }
            /* is reference necessary?
            if(parentCollection == null) {
                throw new RuntimeException("This element needs a parent collection");
            }  */

            return new DataCollectionElementImpl(id, type, textFields, numericFields, booleanFields, parentCollection);
        }
    }
    
}
