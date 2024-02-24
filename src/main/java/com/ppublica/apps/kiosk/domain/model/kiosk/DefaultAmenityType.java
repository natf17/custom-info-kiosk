package com.ppublica.apps.kiosk.domain.model.kiosk;

/*
 * Represents a full amenity type that can be instantiated.
 */
public class DefaultAmenityType extends AmenityTypeBase {
    public static final CollectionType KIOSK_COLLECTION_TYPE_NAME = CollectionType.AMENITY;

    public DefaultAmenityType(KioskCollectionType kioskCollectionType, Amenity amenity) {
        super(kioskCollectionType, amenity);
    }

    public static class Builder extends AmenityTypeBase.Builder<Builder, DefaultAmenityType> {

        public Builder() {
            super(KIOSK_COLLECTION_TYPE_NAME);
        }

        public Builder(CollectionType kioskCollectionTypeName) {
            super(kioskCollectionTypeName);
        }

        @Override
        protected void validateAndPrepareAmenityChild() { }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected DefaultAmenityType buildAmenityChild(KioskCollectionType kioskCollectionType, Amenity amenity) {
            return new DefaultAmenityType(kioskCollectionType, amenity);
        }

    }

    
}

