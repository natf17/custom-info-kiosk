package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomImage;

public class BathroomImageToKioskImageTypeConverter implements ValueConverter<BathroomImage,KioskImage> {
    public KioskImage convert(BathroomImage input) {
        return new KioskImage(input.url(), input.width(), input.height());
    }
}