package com.ppublica.apps.kiosk.service.util;

import java.time.LocalDate;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;

public class LocalDateKioskFieldCreator implements KioskFieldCreator<LocalDate> {
    public KioskCollectionField<LocalDate> create(LocalDate value, boolean isLocalizable) {
        return new KioskCollectionField<>(value, isLocalizable);
    }
}
