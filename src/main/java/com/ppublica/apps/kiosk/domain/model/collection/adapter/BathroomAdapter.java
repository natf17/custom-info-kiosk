package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.BathroomType;

public class BathroomAdapter extends GenderAwareAmenityAdapter implements BathroomType {


    public BathroomAdapter(BathroomType kioskRep) {
        super(kioskRep, kioskRep, kioskRep, null);
    }

    public BathroomAdapter(SimpleCollectionType cmsRep) {
        super(null, null, null, cmsRep);
    }


}
