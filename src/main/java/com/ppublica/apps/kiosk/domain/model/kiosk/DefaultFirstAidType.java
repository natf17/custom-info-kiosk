package com.ppublica.apps.kiosk.domain.model.kiosk;

/*
 * Represents a full donations type that can be instantiated.
 */
public class DefaultFirstAidType extends DefaultAmenityType implements FirstAidType {


    public static final CollectionType KIOSK_COLLECTION_TYPE = CollectionType.FIRST_AID;

    public DefaultFirstAidType(AmenityType amenity, FirstAid firstAidPiece) {
        super(amenity, amenity);
    }

    @Override
    public CollectionType kioskCollectionType() {
        return KIOSK_COLLECTION_TYPE;
    }
    
}

