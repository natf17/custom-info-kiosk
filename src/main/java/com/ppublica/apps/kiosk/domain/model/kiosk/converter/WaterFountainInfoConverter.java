package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.EmptyWaterFountainPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.WaterFountain;

public class WaterFountainInfoConverter {
    public WaterFountain convert(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {
        return new EmptyWaterFountainPiece();
    }

    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder, WaterFountain waterFountainInfo) {}

}
