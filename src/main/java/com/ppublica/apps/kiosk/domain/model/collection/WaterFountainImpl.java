package com.ppublica.apps.kiosk.domain.model.collection;

public class WaterFountainImpl extends AmenityTypeImpl implements WaterFountainType {

    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.WATER_FOUNTAIN;

    protected WaterFountainImpl(KioskCollectionType kioskCollectionType, Amenity amenity) {
        super(kioskCollectionType, amenity);
    }


    public static class Builder extends AmenityTypeImpl.Builder<Builder, WaterFountainImpl> {

        public Builder() {
            super(KIOSK_COLLECTION_TYPE_NAME);
        }

        @Override
        protected void validateAndPrepareAmenityChild() { }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected WaterFountainImpl buildAmenityChild(KioskCollectionType kioskCollectionType, Amenity amenity) {
            return new WaterFountainImpl(kioskCollectionType, amenity);
        }
    }
    
}