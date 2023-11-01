package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.Amenity;
import com.ppublica.apps.kiosk.domain.model.collection.AmenityType;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.LinkedCollectionReference;

public class AmenityWithBaseAdapter extends BaseAdapter implements Amenity {
    private Amenity kioskRepAmenity;
    private AmenityConverter amenityConverter = new AmenityConverter();

    public AmenityWithBaseAdapter(Amenity amenity, KioskCollectionType baseCollection, SimpleCollectionType baseCmsCollection) {
        super(baseCollection, baseCmsCollection);
        this.kioskRepAmenity = amenity;
    }

    public AmenityWithBaseAdapter(AmenityType amenityType) {
        this(amenityType, amenityType, null);
    }

    public AmenityWithBaseAdapter(SimpleCollectionType baseCmsCollection) {
        this(null, null, baseCmsCollection);
    }

    @Override
    public KioskCollectionField<Image> getFeatImg() {
        return getAmenity().getFeatImg();
    }

    @Override
    public KioskCollectionField<Long> getSvgElemId() {
        return getAmenity().getSvgElemId();
    }

    @Override
    public KioskCollectionField<Boolean> isWheelChairAccessible() {
        return getAmenity().isWheelChairAccessible();
    }

    @Override
    public KioskCollectionField<String> getName() {
        return getAmenity().getName();
    }

    @Override
    public KioskCollectionField<String> getNote() {
        return getAmenity().getNote();
    }

    @Override
    public KioskCollectionField<LinkedCollectionReference> getLocation() {
        return getAmenity().getLocation();
    }

    @Override
    protected void processCmsBuilder(SimpleCollectionTypeImpl.Builder builder) {
        amenityConverter.transferKioskRep(builder, kioskRepAmenity);
    }

    protected Amenity getAmenity() {
        if(this.kioskRepAmenity == null) {
            buildAndSetAmenity();
        }

        return this.kioskRepAmenity;
    }

    protected void buildAndSetAmenity() {
        this.kioskRepAmenity = amenityConverter.convert(getCmsCollection());
    }
    
}
