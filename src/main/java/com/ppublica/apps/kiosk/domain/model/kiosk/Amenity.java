package com.ppublica.apps.kiosk.domain.model.kiosk;


public interface Amenity {
    KioskCollectionField<KioskImage> featImg();
    KioskCollectionField<Long> svgElemId();
    KioskCollectionField<Boolean> isWheelChairAccessible();
    KioskCollectionField<String> name();
    KioskCollectionField<String> note();
    KioskCollectionField<LinkedCollectionReference> location();
}
