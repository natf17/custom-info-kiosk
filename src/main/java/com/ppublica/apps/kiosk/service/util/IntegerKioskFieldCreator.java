package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;

public class IntegerKioskFieldCreator implements KioskFieldCreator<Integer> {
    public KioskCollectionField<Integer> create(Integer value, boolean isLocalizable) {
        return new KioskCollectionField<>(value, isLocalizable);
    }
}
