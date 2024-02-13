package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public record CollectionLocalizedPropertiesImpl(Long id, Long parentId, CollectionNameField collectionNameField, List<TextField> textFields, List<NumericField> numericFields, List<BooleanField> booleanFields, List<ImageField> imageFields, CollectionInternals collectionInternals) implements CollectionLocalizedProperties {
    @Override
    public CollectionLocalizedProperties withId(Long id) {
        return new CollectionLocalizedPropertiesImpl(id, this.parentId, this.collectionNameField, this.textFields, this.numericFields, this.booleanFields, this.imageFields, this.collectionInternals);
    }

    public static class Builder {
        private Long id;
        private CollectionNameField collectionNameField;
        private List<TextField> textFields = new ArrayList<>();
        private List<NumericField> numericFields = new ArrayList<>();
        private List<BooleanField> booleanFields = new ArrayList<>();
        private List<ImageField> imageFields = new ArrayList<>();
        private CollectionInternals collectionInternals;
        private Long kioskLocaleId;
        private Long parentId;

        Builder(Long parentId) {
            this.parentId = parentId;
        }

        public Builder withId(Long id) {
            this.id = id;
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

        public Builder collectionInternals(CollectionInternals collectionInternals) {
            this.collectionInternals = collectionInternals;
            return this;
        }

        public Builder kioskLocaleId(Long kioskLocaleId) {
            this.kioskLocaleId = kioskLocaleId;
            return this;
        }

        public CollectionLocalizedProperties build() {

            if(collectionInternals == null) {
                if(kioskLocaleId == null) {
                    throw new RuntimeException("The kioskLocaleId is required");
                }

                collectionInternals = new CollectionInternals(kioskLocaleId, PageStatus.DRAFT, LocalDate.now(), LocalDateTime.now());
            }

            return new CollectionLocalizedPropertiesImpl(id, this.parentId, this.collectionNameField, this.textFields, this.numericFields, this.booleanFields, this.imageFields, this.collectionInternals);
        }
    }

}
