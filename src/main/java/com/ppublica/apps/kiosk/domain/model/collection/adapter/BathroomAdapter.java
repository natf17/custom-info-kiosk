package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.BathroomType;

public class BathroomAdapter extends GenderAwareAmenityAdapter {

    public BathroomAdapter(BathroomType kioskRep, SimpleCollectionType cmsRep) {
        super(kioskRep, kioskRep, kioskRep, cmsRep);
    }


}
