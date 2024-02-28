package com.ppublica.apps.kiosk.domain.model.kiosk;


public interface Amenity {
    KioskCollectionField<KioskImage> featImg();
    KioskCollectionField<String> svgElemId();
    KioskCollectionField<Boolean> isWheelChairAccessible();
    KioskCollectionField<String> name();
    KioskCollectionField<String> note();
    KioskCollectionField<LinkedCollectionReference> location();
}
