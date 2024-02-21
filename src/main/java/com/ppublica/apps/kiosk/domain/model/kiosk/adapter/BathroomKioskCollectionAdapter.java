package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.Amenity;
import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.GenderAware;

public class BathroomKioskCollectionAdapter extends GenderAwareAmenityAdapter implements BathroomType {
    
    private BathroomKioskCollectionAdapter(GenderAware genderInfo, Amenity amenity, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(genderInfo, amenity, baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
    }

    private BathroomKioskCollectionAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, null, localizedCmsPiece, sharedCmsPiece);
    }

    private BathroomKioskCollectionAdapter(GenderAware genderInfo, Amenity amenity, KioskCollectionType baseKioskCollection) {
        this(genderInfo, amenity, baseKioskCollection, null, null);
    }

    public static class Builder extends CmsCollectionAdapterBuilder<Builder, BathroomType, BathroomKioskCollectionAdapter> {

        @Override
        protected BathroomKioskCollectionAdapter buildChild() {
            return new BathroomKioskCollectionAdapter(super.kioskCollection, super.kioskCollection, super.kioskCollection, super.localizedCmsPiece, super.sharedCmsPiece);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }
}
