package com.ppublica.apps.kiosk.domain.model.kiosk;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.LinkedCollectionReference;

public interface Amenity {
    KioskCollectionField<Image> featImg();
    KioskCollectionField<Long> svgElemId();
    KioskCollectionField<Boolean> isWheelChairAccessible();
    KioskCollectionField<String> name();
    KioskCollectionField<String> note();
    KioskCollectionField<LinkedCollectionReference> location();
}
