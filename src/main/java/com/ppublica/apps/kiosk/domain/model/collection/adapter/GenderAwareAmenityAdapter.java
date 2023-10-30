package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.collection.AmenityType;
import com.ppublica.apps.kiosk.domain.model.collection.GenderAware;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;

public class GenderAwareAmenityAdapter extends AmenityWithBaseAdapter implements GenderAware {
    private GenderAware kioskRepGenderInfo;
    private GenderConverter genderCoverter;
    
    public GenderAwareAmenityAdapter(GenderAware genderInfo, AmenityType amenity, KioskCollectionType baseCollection, SimpleCollectionType cmsCollection) {
        super(amenity, baseCollection, cmsCollection);
        this.kioskRepGenderInfo = genderInfo;
    }

    @Override
    public KioskCollectionField<String> getGender() {
        return kioskRepGenderInfo.getGender();
    }

    @Override
    protected void processCmsBuilder(SimpleCollectionTypeImpl.Builder builder) {
        super.processCmsBuilder(builder);
        genderCoverter.transferKioskRep(builder, kioskRepGenderInfo);
    }

    protected GenderAware getGenderInfo() {
        if(this.kioskRepGenderInfo == null) {
            buildAndSetGenderInfo();
        }

        return this.kioskRepGenderInfo;
    }

    protected void buildAndSetGenderInfo() {
        this.kioskRepGenderInfo = genderCoverter.convert(getCmsCollection());
    }
    
}
