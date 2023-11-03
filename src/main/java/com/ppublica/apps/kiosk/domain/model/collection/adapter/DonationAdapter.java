package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.DonationType;

public class DonationAdapter extends AmenityWithBaseAdapter implements DonationType {
    public DonationAdapter(DonationType kioskRep) {
        super(kioskRep, kioskRep, null);
    }

    public DonationAdapter(SimpleCollectionType cmsRep) {
        super(null, null, cmsRep);
    }
}
