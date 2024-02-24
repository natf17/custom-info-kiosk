package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;

public class StringKioskFieldCreator implements KioskFieldCreator<String> {
    public KioskCollectionField<String> create(String value, boolean isLocalizable) {
        return new KioskCollectionField<>(value, isLocalizable);
    }
}
