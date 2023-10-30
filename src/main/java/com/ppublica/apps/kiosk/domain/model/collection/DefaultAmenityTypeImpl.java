package com.ppublica.apps.kiosk.domain.model.collection;

public class DefaultAmenityTypeImpl extends AmenityTypeImpl {
    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.AMENITY;

    public DefaultAmenityTypeImpl(KioskCollectionType kioskCollectionType, Amenity amenity) {
        super(kioskCollectionType, amenity);
    }

    public static class Builder extends AmenityTypeImpl.Builder<Builder, DefaultAmenityTypeImpl> {

        public Builder() {
            super(KIOSK_COLLECTION_TYPE_NAME);
        }

        public Builder(CollectionTypeName kioskCollectionTypeName) {
            super(kioskCollectionTypeName);
        }

        @Override
        protected void validateAndPrepareAmenityChild() { }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected DefaultAmenityTypeImpl buildAmenityChild(KioskCollectionType kioskCollectionType, Amenity amenity) {
            return new DefaultAmenityTypeImpl(kioskCollectionType, amenity);
        }

    }

    
}
