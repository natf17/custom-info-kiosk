package com.ppublica.apps.kiosk.domain.model.collection;

public class DonationTypeImpl extends AmenityTypeImpl implements DonationType {
    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.DONATIONS;

    protected DonationTypeImpl(KioskCollectionType kioskCollectionType, Amenity amenity) {
        super(kioskCollectionType, amenity);
    }


    public static class Builder extends AmenityTypeImpl.Builder<Builder, DonationTypeImpl> {

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
        protected DonationTypeImpl buildAmenityChild(KioskCollectionType kioskCollectionType, Amenity amenity) {
            return new DonationTypeImpl(kioskCollectionType, amenity);
        }
    }
    
}
