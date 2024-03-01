package com.ppublica.apps.kiosk.domain.model.kiosk;

/*
 * Represents a full water fountain type that can be instantiated.
 */
public class DefaultWaterFountainType extends DefaultAmenityType implements WaterFountainType {


    public static final CollectionType KIOSK_COLLECTION_TYPE = CollectionType.WATER_FOUNTAIN;

    public DefaultWaterFountainType(AmenityType amenity, WaterFountain waterFountainPiece) {
        super(amenity, amenity);
    }

    @Override
    public CollectionType kioskCollectionType() {
        return KIOSK_COLLECTION_TYPE;
    }
    
}