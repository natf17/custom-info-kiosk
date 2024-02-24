package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;

public interface KioskFieldCreator<T> {
    public KioskCollectionField<T> create(T value, boolean isLocalizable);
}
