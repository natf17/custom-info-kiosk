package com.ppublica.apps.kiosk.domain.model.collection;

public class BathroomTypeImpl extends AmenityTypeImpl implements BathroomType {

    private GenderAware genderInfo;

    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.BATHROOM;

    protected BathroomTypeImpl(AmenityType amenity, GenderInfo genderInfo) {
        super(amenity, amenity);
        this.genderInfo = genderInfo;
    }

    @Override
    public CollectionTypeName getKioskCollectionTypeName() {
        return KIOSK_COLLECTION_TYPE_NAME;
    }

    @Override
    public KioskCollectionField<String> getGender() {
        return this.genderInfo.getGender();
    }
    
}
