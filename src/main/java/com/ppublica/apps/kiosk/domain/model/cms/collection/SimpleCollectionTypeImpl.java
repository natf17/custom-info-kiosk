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
    private List<ImageField> imageFields;
    private CollectionInternals collectionInternals;
    

    public SimpleCollectionTypeImpl(Long id, String type, CollectionNameField collectionNameField, List<TextField> textFields, List<NumericField> numericFields,
                List<ImageField> imageFields, CollectionInternals collectionInternals) {
        
        this.id = id;
        this.type = type;
        this.collectionNameField = collectionNameField;
        this.textFields = textFields;
        this.numericFields = numericFields;
        this.imageFields = imageFields;
        this.collectionInternals = collectionInternals;
        
    }

    public SimpleCollectionTypeImpl(String type, CollectionNameField collectionNameField, List<TextField> textFields, List<NumericField> numericFields,
                List<ImageField> imageFields, CollectionInternals collectionInternals) {
        
        this(null, type, collectionNameField, textFields, numericFields, imageFields, collectionInternals);
        
    }

    public SimpleCollectionType withId(Long id) {
        return new SimpleCollectionTypeImpl(id, type, collectionNameField, textFields, numericFields, imageFields, collectionInternals);
    }

    public Long getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
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

    public List<ImageField> getImageFields() {
        return this.imageFields;
    }

    public CollectionInternals getCollectionInternals() {
        return this.collectionInternals;
    }

    public static class Builder {
        private Long id;
        private String type;
        private CollectionNameField collectionNameField;
        private List<TextField> textFields = new ArrayList<>();
        private List<NumericField> numericFields = new ArrayList<>();
        private List<ImageField> imageFields = new ArrayList<>();
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
            if(textFields == null) {
                throw new RuntimeException("A non-null argument is required");
            }
            this.numericFields = numericFields;
            return this;
        }

        public Builder addNumericField(NumericField numericField) {
            this.numericFields.add(numericField);
            return this;
        }

        public Builder imageFields(List<ImageField> imageFields) {
            if(textFields == null) {
                throw new RuntimeException("A non-null argument is required");
            }
            this.imageFields = imageFields;
            return this;
        }

        public Builder addImageField(ImageField imageField) {
            this.imageFields.add(imageField);
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

                collectionInternals = new CollectionInternals(kioskLocaleId, PageStatus.DRAFT, LocalDate.now(), LocalDateTime.now());
            }

            if(type == null) {
                throw new RuntimeException("A collection type is required");
            }

            if(collectionNameField == null) {
                throw new RuntimeException("A CollectionNameField is required");
            }

            return new SimpleCollectionTypeImpl(id, type, collectionNameField, textFields, numericFields, imageFields, collectionInternals);
        }
    }

}
