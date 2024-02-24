package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;

public class LinkedCollectionReferenceKioskFieldCreator implements KioskFieldCreator<LinkedCollectionReference> {
    public KioskCollectionField<LinkedCollectionReference> create(LinkedCollectionReference value, boolean isLocalizable) {
        return new KioskCollectionField<>(value, isLocalizable);
    }
}
