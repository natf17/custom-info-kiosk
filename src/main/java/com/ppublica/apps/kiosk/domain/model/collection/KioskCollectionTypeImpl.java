package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public abstract class KioskCollectionTypeImpl implements KioskCollectionType {

    private Long id;
    private CollectionTypeName type;
    private KioskCollectionField<String> collectionNameField;
    private KioskCollectionMetadata kioskCollectionMetadata;

    public static String COLLECTION_NAME_FIELD_NAME_DEFAULT = "Collection Name";

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

    protected KioskCollectionTypeImpl(CollectionTypeName type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata, Long id) {
        this.id = id;
        this.type = type;
        this.collectionNameField = collectionNameField;
        this.kioskCollectionMetadata = kioskCollectionMetadata;
    }

    public static abstract class Builder<B extends Builder<B, M>, M extends KioskCollectionTypeImpl> {
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

        public B id(Long id) {
            this.id = id;
            return self();
        }


        public B collectionNameField(KioskCollectionField<String> editedCollectionNameField) {
            this.collectionNameField = editedCollectionNameField;
            return self();
        }

        public B kioskCollectionMetadata(KioskCollectionMetadata kioskCollectionMetadata) {
            this.kioskCollectionMetadata = kioskCollectionMetadata;
            return self();
        }

        public B pageStatus(PageStatus pageStatus) {
            this.pageStatus = pageStatus;
            return self();
        }

        public B createdOn(LocalDate createdOn) {
            this.createdOn = createdOn;
            return self();
        }

        public B lastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return self();
        }

        public B withLocaleId(Long kioskLocaleId) {
            this.kioskLocaleId = kioskLocaleId;
            return self();
        }

        public M build() {
            validateAndPrepare();
               
            return buildChild();
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

            
            validateAndPrepareChild();

        }

        protected abstract void validateAndPrepareChild();

        protected abstract M buildChild();

        protected abstract B self();        

    }

}
