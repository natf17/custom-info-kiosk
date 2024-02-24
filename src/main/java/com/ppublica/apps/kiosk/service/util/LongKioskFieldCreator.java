package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;

public class LongKioskFieldCreator implements KioskFieldCreator<Long> {
    public KioskCollectionField<Long> create(Long value, boolean isLocalizable) {
        return new KioskCollectionField<>(value, isLocalizable);
    }
}
