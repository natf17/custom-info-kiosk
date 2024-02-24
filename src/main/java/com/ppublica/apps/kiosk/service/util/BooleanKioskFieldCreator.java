package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;

public class BooleanKioskFieldCreator implements KioskFieldCreator<Boolean> {
    public KioskCollectionField<Boolean> create(Boolean value, boolean isLocalizable) {
        return new KioskCollectionField<>(value, isLocalizable);
    }
}
