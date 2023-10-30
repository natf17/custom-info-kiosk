package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class DefaultKioskCollection implements KioskCollectionType {

    private Long id;
    private CollectionTypeName type;
    private KioskCollectionField<String> collectionNameField;
    private KioskCollectionMetadata kioskCollectionMetadata;

    public static String COLLECTION_NAME_FIELD_NAME_DEFAULT = "Collection Name";

    private DefaultKioskCollection(CollectionTypeName type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata, Long id) {
        this.id = id;
        this.type = type;
        this.collectionNameField = collectionNameField;
        this.kioskCollectionMetadata = kioskCollectionMetadata;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public CollectionTypeName getKioskCollectionTypeName() {
        return this.type;
    }

    @Override
    public KioskCollectionField<String> getKioskCollectionNameField() {
        return this.collectionNameField;
    }

    @Override
    public KioskCollectionMetadata getKioskCollectionMetadata() {
        return this.kioskCollectionMetadata;
    }

    public static class Builder {
        protected Long id;

        protected CollectionTypeName type;

        protected KioskCollectionField<String> collectionNameField;

        protected KioskCollectionMetadata kioskCollectionMetadata;
        private PageStatus pageStatus;
        private LocalDate createdOn;
        private LocalDateTime lastModified;
        private Long kioskLocaleId;

        protected Builder(CollectionTypeName type) {
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

        public Builder kioskCollectionMetadata(KioskCollectionMetadata kioskCollectionMetadata) {
            this.kioskCollectionMetadata = kioskCollectionMetadata;
            return this;
        }

        public Builder pageStatus(PageStatus pageStatus) {
            this.pageStatus = pageStatus;
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

        public Builder withLocaleId(Long kioskLocaleId) {
            this.kioskLocaleId = kioskLocaleId;
            return this;
        }

        public KioskCollectionType build() {
            validateAndPrepare();
               
            return new DefaultKioskCollection(type, collectionNameField, kioskCollectionMetadata, id);
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
                collectionNameField = new KioskCollectionField<String>(COLLECTION_NAME_FIELD_NAME_DEFAULT, null, true);
            }

            if(kioskCollectionMetadata == null) {
                if(this.pageStatus == null) {
                    throw new RuntimeException("PageStatus is required");
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

                if(this.kioskLocaleId == null) {
                    throw new RuntimeException("Locale id is required");
                }

                kioskCollectionMetadata = new KioskCollectionMetadata(kioskLocaleId, pageStatus, createdOn, lastModified);

            }


            if(this.type == null) {
                throw new RuntimeException("The collecion type is required");
            }

        }

    }
    
}
