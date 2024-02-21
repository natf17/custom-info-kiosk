package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class SimpleCollectionTypeImpl implements SimpleCollectionType {

    private Long id;
    private String type;
    private CollectionNameField collectionNameField;
    private List<TextField> textFields;
    private List<NumericField> numericFields;
    private List<BooleanField> booleanFields;
    private List<ImageField> imageFields;
    private List<LinkedCollectionField> linkedCollectionFields;
    private CollectionInternals collectionInternals;
    

    public SimpleCollectionTypeImpl(Long id, String type, CollectionNameField collectionNameField, List<TextField> textFields, List<NumericField> numericFields,
                List<BooleanField> booleanFields, List<ImageField> imageFields, List<LinkedCollectionField> linkedCollectionFields, CollectionInternals collectionInternals) {
        
        this.id = id;
        this.type = type;
        this.collectionNameField = collectionNameField;
        this.textFields = textFields;
        this.numericFields = numericFields;
        this.booleanFields = booleanFields;
        this.imageFields = imageFields;
        this.linkedCollectionFields = linkedCollectionFields;
        this.collectionInternals = collectionInternals;
        
    }

    public SimpleCollectionTypeImpl(String type, CollectionNameField collectionNameField, List<TextField> textFields, List<NumericField> numericFields,
                List<BooleanField> booleanFields, List<ImageField> imageFields, List<LinkedCollectionField> linkedCollectionFields, CollectionInternals collectionInternals) {
        
        this(null, type, collectionNameField, textFields, numericFields, booleanFields, imageFields, linkedCollectionFields, collectionInternals);
        
    }

    @Override
    public SimpleCollectionType withId(Long id) {
        return new SimpleCollectionTypeImpl(id, type, collectionNameField, textFields, numericFields, booleanFields, imageFields, linkedCollectionFields, collectionInternals);
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
    public List<ImageField> getImageFields() {
        return this.imageFields;
    }

    @Override
    public CollectionInternals getCollectionInternals() {
        return this.collectionInternals;
    }

    @Override
    public List<LinkedCollectionField> getLinkedCollectionFields() {
        return this.linkedCollectionFields;
    }

    public static class Builder {
        private Long id;
        private String type;
        private CollectionNameField collectionNameField;
        private List<TextField> textFields = new ArrayList<>();
        private List<NumericField> numericFields = new ArrayList<>();
        private List<BooleanField> booleanFields = new ArrayList<>();
        private List<ImageField> imageFields = new ArrayList<>();
        private List<LinkedCollectionField> linkedCollectionFields = new ArrayList<>();
        private CollectionInternals collectionInternals;
        private Long kioskLocaleId;

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

        public Builder collectionInternals(CollectionInternals collectionInternals) {
            this.collectionInternals = collectionInternals;
            return this;
        }

        public Builder kioskLocaleId(Long kioskLocaleId) {
            this.kioskLocaleId = kioskLocaleId;
            return this;
        }

        public SimpleCollectionType build() {

            if(collectionInternals == null) {
                if(kioskLocaleId == null) {
                    throw new RuntimeException("The kioskLocaleId is required");
                }

                collectionInternals = new CollectionInternals(kioskLocaleId, null, PageStatus.DRAFT, LocalDate.now(), LocalDateTime.now());
            }

            if(type == null) {
                throw new RuntimeException("A collection type is required");
            }

            if(collectionNameField == null) {
                throw new RuntimeException("A CollectionNameField is required");
            }

            return new SimpleCollectionTypeImpl(id, type, collectionNameField, textFields, numericFields, booleanFields, imageFields, linkedCollectionFields, collectionInternals);
        }
    }

}
