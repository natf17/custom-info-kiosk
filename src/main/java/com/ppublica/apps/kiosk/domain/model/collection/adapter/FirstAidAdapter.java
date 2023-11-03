package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.FirstAidType;

public class FirstAidAdapter extends AmenityWithBaseAdapter implements FirstAidType {
    public FirstAidAdapter(FirstAidType kioskRep) {
        super(kioskRep, kioskRep, null);
    }

    public FirstAidAdapter(SimpleCollectionType cmsRep) {
        super(null, null, cmsRep);
    }
    
}
