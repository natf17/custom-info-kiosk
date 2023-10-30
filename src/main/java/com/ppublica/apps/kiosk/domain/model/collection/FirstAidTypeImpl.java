package com.ppublica.apps.kiosk.domain.model.collection;

public class FirstAidTypeImpl extends AmenityTypeImpl implements FirstAidType {

    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.FIRST_AID;

    protected FirstAidTypeImpl(KioskCollectionType kioskCollectionType, Amenity amenity) {
        super(kioskCollectionType, amenity);
    }

    public static class Builder extends AmenityTypeImpl.Builder<Builder, FirstAidTypeImpl> {

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
        protected FirstAidTypeImpl buildAmenityChild(KioskCollectionType kioskCollectionType, Amenity amenity) {
            return new FirstAidTypeImpl(kioskCollectionType, amenity);
        }
    }
    
}
