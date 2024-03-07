package com.ppublica.apps.kiosk.domain.model.kiosk;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * The non-localizable equivalent of KioskCollectionTypeBase.
 */
public abstract class NonLocalizableKioskCollectionTypeBase implements NonLocalizableKioskCollectionType {
    private NonLocalizableKioskCollectionType kioskCollectionType;

    @Override
    public Long collectionId() {
        return this.kioskCollectionType.collectionId();
    }

    @Override
    public CollectionType kioskCollectionType() {
        return this.kioskCollectionType.kioskCollectionType();
    }

    @Override
    public KioskCollectionField<String> kioskCollectionNameField() {
        return this.kioskCollectionType.kioskCollectionNameField();
    }

    @Override
    public NonLocalizableKioskCollectionMetadata kioskCollectionMetadata() {
        return this.kioskCollectionType.kioskCollectionMetadata();
    }

    protected NonLocalizableKioskCollectionTypeBase(NonLocalizableKioskCollectionType kioskCollectionType) {
        this.kioskCollectionType = kioskCollectionType;
    }

    public static abstract class Builder<B extends Builder<B, M>, M extends NonLocalizableKioskCollectionTypeBase> {
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

        public B id(Long id) {
            this.id = id;
            return self();
        }

        public B collectionNameField(KioskCollectionField<String> editedCollectionNameField) {
            this.collectionNameField = editedCollectionNameField;
            return self();
        }

        public B kioskCollectionMetadata(NonLocalizableKioskCollectionMetadata kioskCollectionMetadata) {
            this.kioskCollectionMetadata = kioskCollectionMetadata;
            return self();
        }

        public B status(Status status) {
            this.status = status;
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

        public M build() {
            validateAndPrepare();

            DefaultNonLocalizableKioskCollection.Builder kioskCollectionBuilder = new DefaultNonLocalizableKioskCollection.Builder(type)
                                                                    .collectionNameField(collectionNameField)
                                                                    .id(id);
            if(this.kioskCollectionMetadata == null) {
                kioskCollectionBuilder.status(status)
                                        .createdOn(createdOn)
                                        .lastModified(lastModified);
            } else {
                kioskCollectionBuilder.kioskCollectionMetadata(kioskCollectionMetadata);
            }
            
            NonLocalizableKioskCollectionType kioskCollectionType = kioskCollectionBuilder.build();

            return buildChild(kioskCollectionType);
        }


        /*
         * anything in addition to  validation already provided by the underlying DefaultKioskCollection builder...
         */
        private void validateAndPrepare() {
            validateAndPrepareChild();
            
        }

        protected abstract void validateAndPrepareChild();

        protected abstract M buildChild(NonLocalizableKioskCollectionType kioskCollectionType);

        protected abstract B self();        

    }

}
