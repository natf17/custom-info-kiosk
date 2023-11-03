package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.WaterFountainType;

public class WaterFountainAdapter extends AmenityWithBaseAdapter implements WaterFountainType {
    public WaterFountainAdapter(WaterFountainType kioskRep) {
        super(kioskRep, kioskRep, null);
    }

    public WaterFountainAdapter(SimpleCollectionType cmsRep) {
        super(null, null, cmsRep);
    }
}
