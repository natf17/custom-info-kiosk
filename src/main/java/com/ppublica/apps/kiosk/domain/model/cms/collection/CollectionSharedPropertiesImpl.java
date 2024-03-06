package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public record CollectionSharedPropertiesImpl(Long id, String type, String subType, CollectionNameField collectionNameField, List<TextField> textFields, List<NumericField> numericFields, List<BooleanField> booleanFields, List<ImageField> imageFields, CollectionSharedInternals collectionSharedInternals, List<LinkedCollectionField> linkedCollectionFields, List<CollectionRelationship> collectionRelationships) implements CollectionSharedProperties {
    @Override
    public CollectionSharedProperties withId(Long id) {
        return new CollectionSharedPropertiesImpl(id, this.type, this.subType, this.collectionNameField, this.textFields, this.numericFields, this.booleanFields, this.imageFields, this.collectionSharedInternals, this.linkedCollectionFields, this.collectionRelationships);
    }
    
    public static class Builder {
        private Long id;
        private String type;
        private String subType;
        private CollectionNameField collectionNameField;
        private List<TextField> textFields = new ArrayList<>();
        private List<NumericField> numericFields = new ArrayList<>();
        private List<BooleanField> booleanFields = new ArrayList<>();
        private List<ImageField> imageFields = new ArrayList<>();
        private CollectionSharedInternals collectionSharedInternals;
        private List<LinkedCollectionField> linkedCollectionFields = new ArrayList<>();
        private List<CollectionRelationship> collectionRelationships = new ArrayList<>();

        public Builder(String type) {
            this.type = type;
        }

        public Builder type (String type) {
            this.type = type;
            return this;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder subType(String subType) {
            this.subType = subType;
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

        public Builder imageFields(List<ImageField> imageFields) {
            if(imageFields == null) {
                throw new RuntimeException("A non-null argument is required");
            }
            this.imageFields = imageFields;
            return this;
        }

        public Builder addImageField(ImageField imageField) {
            this.imageFields.add(imageField);
            return this;
        }

        public Builder collectionSharedInternals(CollectionSharedInternals collectionSharedInternals) {
            this.collectionSharedInternals = collectionSharedInternals;
            return this;
        }

        public Builder linkedCollectionFields(List<LinkedCollectionField> linkedCollectionFields) {
            if(linkedCollectionFields == null) {
                throw new RuntimeException("A non-null argument is required");
            }
            this.linkedCollectionFields = linkedCollectionFields;
            return this;
        }

        public Builder addLinkedCollectionField(LinkedCollectionField linkedCollectionField) {
            this.linkedCollectionFields.add(linkedCollectionField);
            return this;
        }

        public Builder collectionRelationships(List<CollectionRelationship> collectionRelationships) {
            if(collectionRelationships == null) {
                throw new RuntimeException("A non-null argument is required");
            }
            this.collectionRelationships = collectionRelationships;
            return this;
        }

        public Builder addCollectionRelationship(CollectionRelationship collectionRelationship) {
            this.collectionRelationships.add(collectionRelationship);
            return this;
        }

        

        public CollectionSharedProperties build() {

            if(collectionSharedInternals == null) {

                collectionSharedInternals = new CollectionSharedInternals(PageStatus.DRAFT, LocalDate.now(), LocalDateTime.now());
            }

            return new CollectionSharedPropertiesImpl(id, type, subType, collectionNameField, textFields, numericFields, booleanFields, imageFields, collectionSharedInternals, linkedCollectionFields, collectionRelationships);
        }
    }

}
