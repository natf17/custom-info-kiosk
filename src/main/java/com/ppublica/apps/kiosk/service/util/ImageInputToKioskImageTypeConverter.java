package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.service.payloads.ImageInput;

public class ImageInputToKioskImageTypeConverter implements ValueConverter<ImageInput,KioskImage> {
    public KioskImage convert(ImageInput input) {
        return new KioskImage(input.url(), input.width(), input.height());
    }
}
