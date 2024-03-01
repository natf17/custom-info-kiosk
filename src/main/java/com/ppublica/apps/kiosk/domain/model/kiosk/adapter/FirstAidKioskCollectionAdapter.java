package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.Amenity;
import com.ppublica.apps.kiosk.domain.model.kiosk.FirstAid;
import com.ppublica.apps.kiosk.domain.model.kiosk.FirstAidType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.FirstAidInfoConverter;

public class FirstAidKioskCollectionAdapter extends AmenityKioskCollectionAdapter implements FirstAidType {
    private FirstAid firstAidInfo;
    private FirstAidInfoConverter firstAidInfoConverter = new FirstAidInfoConverter();
    
    protected FirstAidKioskCollectionAdapter(FirstAid firstAidInfo, Amenity amenity, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(amenity, baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
        this.firstAidInfo = firstAidInfo;
    }

    protected FirstAidKioskCollectionAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, null, localizedCmsPiece, sharedCmsPiece);
    }

    protected FirstAidKioskCollectionAdapter(FirstAid firstAidInfo, Amenity amenity, KioskCollectionType baseKioskCollection) {
        this(firstAidInfo, amenity, baseKioskCollection, null, null);
    }

    @Override
    protected void processCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder) {
        super.processCmsBuilders(sharedCmsBuilder, localizedCmsBuilder);
        firstAidInfoConverter.transferKioskRepToCmsBuilders(sharedCmsBuilder, localizedCmsBuilder, this.firstAidInfo);

    }

    protected FirstAid getDonationsInfo() {
        if(this.firstAidInfo == null) {
            buildAndSetFirstAidInfo();
        }

        return this.firstAidInfo;
    }

    protected void buildAndSetFirstAidInfo() {
        this.firstAidInfo = firstAidInfoConverter.convert(getSharedCmsPiece(), getLocalizedCmsPiece());
    }



    public static class Builder extends CmsCollectionAdapterBuilder<Builder, FirstAidType, FirstAidKioskCollectionAdapter> {

        @Override
        protected FirstAidKioskCollectionAdapter buildChild() {
            return new FirstAidKioskCollectionAdapter(super.kioskCollection, super.kioskCollection, super.kioskCollection, super.localizedCmsPiece, super.sharedCmsPiece);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }
}
