package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.Amenity;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.WaterFountain;
import com.ppublica.apps.kiosk.domain.model.kiosk.WaterFountainType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.WaterFountainInfoConverter;

public class WaterFountainKioskCollectionAdapter extends AmenityKioskCollectionAdapter implements WaterFountainType {
    private WaterFountain waterFountainInfo;
    private WaterFountainInfoConverter waterFountainInfoConverter = new WaterFountainInfoConverter();
    
    protected WaterFountainKioskCollectionAdapter(WaterFountain waterFountainInfo, Amenity amenity, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(amenity, baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
        this.waterFountainInfo = waterFountainInfo;
    }

    protected WaterFountainKioskCollectionAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, null, localizedCmsPiece, sharedCmsPiece);
    }

    protected WaterFountainKioskCollectionAdapter(WaterFountain waterFountainInfo, Amenity amenity, KioskCollectionType baseKioskCollection) {
        this(waterFountainInfo, amenity, baseKioskCollection, null, null);
    }

    @Override
    protected void processCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder) {
        super.processCmsBuilders(sharedCmsBuilder, localizedCmsBuilder);
        waterFountainInfoConverter.transferKioskRepToCmsBuilders(sharedCmsBuilder, localizedCmsBuilder, this.waterFountainInfo);

    }

    protected WaterFountain getDonationsInfo() {
        if(this.waterFountainInfo == null) {
            buildAndSetFirstAidInfo();
        }

        return this.waterFountainInfo;
    }

    protected void buildAndSetFirstAidInfo() {
        this.waterFountainInfo = waterFountainInfoConverter.convert(getSharedCmsPiece(), getLocalizedCmsPiece());
    }



    public static class Builder extends CmsCollectionAdapterBuilder<Builder, WaterFountainType, WaterFountainKioskCollectionAdapter> {

        @Override
        protected WaterFountainKioskCollectionAdapter buildChild() {
            return new WaterFountainKioskCollectionAdapter(super.kioskCollection, super.kioskCollection, super.kioskCollection, super.localizedCmsPiece, super.sharedCmsPiece);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }
}
