package com.ppublica.apps.kiosk.domain.model.kiosk;

/*
 * Represents a full bathroom type that can be instantiated.
 */
public class DefaultBathroomType extends DefaultAmenityType implements BathroomType {

    private GenderAware genderInfo;

    public static final CollectionType KIOSK_COLLECTION_TYPE = CollectionType.BATHROOM;

    public DefaultBathroomType(AmenityType amenity, GenderInfo genderInfo) {
        super(amenity, amenity);
        this.genderInfo = genderInfo;
    }

    @Override
    public CollectionType kioskCollectionType() {
        return KIOSK_COLLECTION_TYPE;
    }

    @Override
    public KioskCollectionField<String> gender() {
        return this.genderInfo.gender();
    }
    
}
