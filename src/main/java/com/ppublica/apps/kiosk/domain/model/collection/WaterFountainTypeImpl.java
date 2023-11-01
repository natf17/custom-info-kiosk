package com.ppublica.apps.kiosk.domain.model.collection;

public class WaterFountainTypeImpl extends AmenityTypeImpl implements WaterFountainType {

    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.WATER_FOUNTAIN;

    protected WaterFountainTypeImpl(KioskCollectionType kioskCollectionType, Amenity amenity) {
        super(kioskCollectionType, amenity);
    }


    public static class Builder extends AmenityTypeImpl.Builder<Builder, WaterFountainTypeImpl> {

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
        protected WaterFountainTypeImpl buildAmenityChild(KioskCollectionType kioskCollectionType, Amenity amenity) {
            return new WaterFountainTypeImpl(kioskCollectionType, amenity);
        }
    }
    
}