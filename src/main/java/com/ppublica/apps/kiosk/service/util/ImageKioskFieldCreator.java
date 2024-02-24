package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;

public class ImageKioskFieldCreator implements KioskFieldCreator<KioskImage> {
    public KioskCollectionField<KioskImage> create(KioskImage value, boolean isLocalizable) {
        return new KioskCollectionField<>(value, isLocalizable);
    }
}
