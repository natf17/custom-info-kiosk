package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

/*
 * Base class for composite kiosk collections.
 */
public abstract class KioskCollectionTypeImpl implements KioskCollectionType {
    private KioskCollectionType kioskCollectionType;
    /*
    private Long id;
    private CollectionTypeName type;
    private KioskCollectionField<String> collectionNameField;
    private KioskCollectionMetadata kioskCollectionMetadata;
 */
    @Override
    public Long getId() {
        return this.kioskCollectionType.getId();
    }

    @Override
    public CollectionTypeName getKioskCollectionTypeName() {
        return this.kioskCollectionType.getKioskCollectionTypeName();
    }

    @Override
    public KioskCollectionField<String> getKioskCollectionNameField() {
        return this.kioskCollectionType.getKioskCollectionNameField();
    }

    @Override
    public KioskCollectionMetadata getKioskCollectionMetadata() {
        return this.kioskCollectionType.getKioskCollectionMetadata();
    }

    /*
    protected KioskCollectionTypeImpl(CollectionTypeName type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata, Long id) {
        this.id = id;
        this.type = type;
        this.collectionNameField = collectionNameField;
        this.kioskCollectionMetadata = kioskCollectionMetadata;
    }  */

    protected KioskCollectionTypeImpl(KioskCollectionType kioskCollectionType) {
        this.kioskCollectionType = kioskCollectionType;
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

            DefaultKioskCollection.Builder kioskCollectionBuilder = new DefaultKioskCollection.Builder(type)
                                                                    .collectionNameField(collectionNameField)
                                                                    .id(id);
            if(this.kioskCollectionMetadata == null) {
                kioskCollectionBuilder.pageStatus(pageStatus)
                                        .createdOn(createdOn)
                                        .withLocaleId(kioskLocaleId)
                                        .lastModified(lastModified);
            } else {
                kioskCollectionBuilder.kioskCollectionMetadata(kioskCollectionMetadata);
            }
            
            KioskCollectionType kioskCollectionType = kioskCollectionBuilder.build();

            return buildChild(kioskCollectionType);
        }


        /*
         * anything in addition to  validation already provided by the underlying DefaultKioskCollection builder...
         */
        private void validateAndPrepare() {
            validateAndPrepareChild();
            
        }

        protected abstract void validateAndPrepareChild();

        protected abstract M buildChild(KioskCollectionType kioskCollectionType);

        protected abstract B self();        

    }

}
