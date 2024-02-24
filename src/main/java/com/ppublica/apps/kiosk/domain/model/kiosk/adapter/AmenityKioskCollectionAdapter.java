package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.Amenity;
import com.ppublica.apps.kiosk.domain.model.kiosk.AmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.AmenityConverter;

public class AmenityKioskCollectionAdapter extends KioskCollectionTypeBaseAdapter implements AmenityType {
    private Amenity amenity;
    private AmenityConverter amenityConverter = new AmenityConverter();

    public AmenityKioskCollectionAdapter(Amenity amenity, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
        this.amenity = amenity;
    }

    public AmenityKioskCollectionAdapter(AmenityType amenityType) {
        this(amenityType, amenityType, null, null);
    }

    public AmenityKioskCollectionAdapter(Amenity amenity, KioskCollectionType baseKioskCollection) {
        this(amenity, baseKioskCollection, null, null);
    }

    public AmenityKioskCollectionAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, localizedCmsPiece, sharedCmsPiece);
    }

    @Override
    public KioskCollectionField<KioskImage> featImg() {
        return getAmenity().featImg();
    }

    @Override
    public KioskCollectionField<Long> svgElemId() {
        return getAmenity().svgElemId();
    }

    @Override
    public KioskCollectionField<Boolean> isWheelChairAccessible() {
        return getAmenity().isWheelChairAccessible();
    }

    @Override
    public KioskCollectionField<String> name() {
        return getAmenity().name();
    }

    @Override
    public KioskCollectionField<String> note() {
        return getAmenity().note();
    }

    @Override
    public KioskCollectionField<LinkedCollectionReference> location() {
        return getAmenity().location();
    }

    @Override
    protected void processCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder) {
        amenityConverter.transferKioskRepToCmsBuilders(sharedCmsBuilder, localizedCmsBuilder, this.amenity);

    }

    protected Amenity getAmenity() {
        if(this.amenity == null) {
            buildAndSetAmenity();
        }

        return this.amenity;
    }

    protected void buildAndSetAmenity() {
        this.amenity = amenityConverter.convert(getSharedCmsPiece(), getLocalizedCmsPiece());
    }
}
