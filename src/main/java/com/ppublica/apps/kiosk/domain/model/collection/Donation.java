package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public class Donation extends AmenityImpl {

    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.DONATIONS;

    protected Donation(CollectionTypeName type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata, Long id,
                    KioskCollectionField<Image> featImg, KioskCollectionField<Long> svgElemId, KioskCollectionField<Boolean> isWheelChairAccessible,
                    KioskCollectionField<String> name, KioskCollectionField<String> note, KioskCollectionField<LinkedCollectionReference> location) {
        super(type, collectionNameField, kioskCollectionMetadata, id, featImg, svgElemId, isWheelChairAccessible, name, note, location);
    }

    public static class Builder extends AmenityImpl.Builder<Builder, Donation> {

        public Builder() {
            super(KIOSK_COLLECTION_TYPE_NAME);
        }

        @Override
        protected void validateAndPrepareChild() {
        
            super.validateAndPrepareChild();

        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected Donation buildChild() {

            return new Donation(super.type, super.collectionNameField, super.kioskCollectionMetadata, super.id, super.featImg, 
                                super.svgElemId, super.isWheelChairAccessible, super.name, super.note, super.location);
        }
    }
    
}
