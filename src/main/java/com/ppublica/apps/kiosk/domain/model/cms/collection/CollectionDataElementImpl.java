package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.ArrayList;
import java.util.List;

public record CollectionDataElementImpl(Long id, String type, String subType, List<TextField> textFields, List<NumericField> numericFields, List<BooleanField> booleanFields, Long parent) implements CollectionDataElement{
    
    @Override
    public CollectionDataElement withId(Long id) {
        return new CollectionDataElementImpl(id, this.type, this.subType, this.textFields, this.numericFields, this.booleanFields, this.parent);
    }

    public static class Builder {
        private Long id;
        private String type;
        private String subType;
        private List<TextField> textFields = new ArrayList<>();
        private List<NumericField> numericFields = new ArrayList<>();
        private List<BooleanField> booleanFields = new ArrayList<>();
        private Long parent;

        Builder(Long parent) {
            this.parent = parent;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder subType(String subType) {
            this.subType = subType;
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

        public CollectionDataElementImpl build() {

            return new CollectionDataElementImpl(id, type, subType, textFields, numericFields, booleanFields, parent);
        }
    }

    
}
