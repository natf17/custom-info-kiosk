package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.Amenity;
import com.ppublica.apps.kiosk.domain.model.kiosk.GenderAware;

public class GenderAwareAmenityAdapter extends AmenityKioskCollectionAdapter implements GenderAware {
    private GenderAware genderInfo;

    public GenderAwareAmenityAdapter(GenderAware genderInfo, Amenity amenity, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(amenity, baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
        this.genderInfo = genderInfo;
    }

    public GenderAwareAmenityAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, null, localizedCmsPiece, sharedCmsPiece);
    }

    public GenderAwareAmenityAdapter(GenderAware genderInfo, Amenity amenity, KioskCollectionType baseKioskCollection) {
        this(genderInfo, amenity, baseKioskCollection, null, null);
    }

    @Override
    public KioskCollectionField<String> gender() {
        return genderInfo.gender();
    }
}
