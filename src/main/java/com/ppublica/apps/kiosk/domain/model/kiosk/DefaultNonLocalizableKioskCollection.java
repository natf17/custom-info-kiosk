package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DefaultNonLocalizableKioskCollection implements NonLocalizableKioskCollectionType {
    private Long collectionId;
    private CollectionType type;
    private KioskCollectionField<String> collectionNameField;
    private NonLocalizableKioskCollectionMetadata kioskCollectionMetadata;

    public static String COLLECTION_NAME_FIELD_NAME_DEFAULT = "Collection Name";

    private DefaultNonLocalizableKioskCollection(CollectionType type, KioskCollectionField<String> collectionNameField, NonLocalizableKioskCollectionMetadata kioskCollectionMetadata, Long collectionId) {
        this.collectionId = collectionId;
        this.type = type;
        this.collectionNameField = collectionNameField;
        this.kioskCollectionMetadata = kioskCollectionMetadata;
    }

    @Override
    public Long collectionId() {
        return this.collectionId;
    }

    @Override
    public CollectionType kioskCollectionType() {
        return this.type;
    }

    @Override
    public KioskCollectionField<String> kioskCollectionNameField() {
        return this.collectionNameField;
    }

    @Override
    public NonLocalizableKioskCollectionMetadata kioskCollectionMetadata() {
        return this.kioskCollectionMetadata;
    }

    public static class Builder {
        protected Long id;

        protected CollectionType type;

        protected KioskCollectionField<String> collectionNameField;

        protected NonLocalizableKioskCollectionMetadata kioskCollectionMetadata;
        private Status status;
        private LocalDate createdOn;
        private LocalDateTime lastModified;


        protected Builder(CollectionType type) {
            this.type = type;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }


        public Builder collectionNameField(KioskCollectionField<String> editedCollectionNameField) {
            this.collectionNameField = editedCollectionNameField;
            return this;
        }

        public Builder kioskCollectionMetadata(NonLocalizableKioskCollectionMetadata kioskCollectionMetadata) {
            this.kioskCollectionMetadata = kioskCollectionMetadata;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder createdOn(LocalDate createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Builder lastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public NonLocalizableKioskCollectionType build() {
            validateAndPrepare();
               
            return new DefaultNonLocalizableKioskCollection(type, collectionNameField, kioskCollectionMetadata, id);
        }


        /*
         * Components can be either set directly, or in "pieces".
         * If the component is set directly, it overrides the "pieces".
         */
        private void validateAndPrepare() {

            if(this.type == null) {
                throw new RuntimeException("The collection type name is required");
            }


            if(collectionNameField == null) {   
                collectionNameField = new KioskCollectionField<String>(COLLECTION_NAME_FIELD_NAME_DEFAULT, true);
            }

            if(kioskCollectionMetadata == null) {
                if(this.status == null) {
                    throw new RuntimeException("Status is required");
                }

                if(this.createdOn == null) {
                    throw new RuntimeException("CreatedOn is required");
                }

                if(this.lastModified == null) {
                    throw new RuntimeException("LastModified is required");
                }

                LocalDateTime createdOnTime = this.createdOn.atStartOfDay();
                if(createdOnTime.isAfter(this.lastModified)) {
                    throw new RuntimeException("CreatedOn cannot be after lastModified");
                }

                kioskCollectionMetadata = new NonLocalizableKioskCollectionMetadata(status, createdOn, lastModified);

            }


            if(this.type == null) {
                throw new RuntimeException("The collection type is required");
            }

        }

    }
}
