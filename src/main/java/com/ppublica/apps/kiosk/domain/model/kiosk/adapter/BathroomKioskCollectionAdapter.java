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

    public static class Builder {
        private CollectionLocalizedProperties localizedCmsPiece;
        private CollectionSharedProperties sharedCmsPiece;
        private BathroomType bathroomType;

        public Builder localizedCmsPiece(CollectionLocalizedProperties localizedCmsPiece) {
            this.bathroomType =  null;
            this.localizedCmsPiece = localizedCmsPiece;
            return this;
        }

        public Builder sharedCmsPiece(CollectionSharedProperties sharedCmsPiece) {
            this.bathroomType =  null;
            this.sharedCmsPiece = sharedCmsPiece;
            return this;
        }

        public Builder bathroom(BathroomType bathroomType) {
            this.sharedCmsPiece = null;
            this.localizedCmsPiece = null;
            this.bathroomType = bathroomType;
            return this;
        }

        public BathroomKioskCollectionAdapter build() {
            if(localizedCmsPiece != null) {
                if(sharedCmsPiece != null) {
                    // TODO: validate the cms pieces

                    return new BathroomKioskCollectionAdapter(localizedCmsPiece, sharedCmsPiece);
                }

                throw new RuntimeException("Both cms pieces must be provided.");
            }

            if(bathroomType != null) {
                return new BathroomKioskCollectionAdapter(bathroomType, bathroomType, bathroomType);
            }

            throw new RuntimeException("The cms pieces and the bathroom type cannot both be null.");

        }


    }
}
