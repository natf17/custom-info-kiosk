package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;

public interface KioskFieldBuilderProcessor<T> {
    public void processBuilder(DefaultAmenityType.Builder builder, KioskCollectionField<T> field);
}
