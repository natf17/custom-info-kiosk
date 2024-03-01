package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.FirstAid;

public class FirstAidInfoConverter {
    public FirstAid convert(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {
        return new EmptyFirstAidPiece();
    }

    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder, FirstAid firstAidInfo) {}

    class EmptyFirstAidPiece implements FirstAid {}
}
