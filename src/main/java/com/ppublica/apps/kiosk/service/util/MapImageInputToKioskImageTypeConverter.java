package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.service.payloads.locations.MapImageInput;

public class MapImageInputToKioskImageTypeConverter implements ValueConverter<MapImageInput,KioskImage> {
    public KioskImage convert(MapImageInput input) {
        return new KioskImage(input.url(), input.width(), input.height());
    }
}
