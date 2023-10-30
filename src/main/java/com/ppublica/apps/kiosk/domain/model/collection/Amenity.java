package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public interface Amenity {
    KioskCollectionField<Image> getFeatImg();
    KioskCollectionField<Long> getSvgElemId();
    KioskCollectionField<Boolean> isWheelChairAccessible();
    KioskCollectionField<String> getName();
    KioskCollectionField<String> getNote();
    KioskCollectionField<LinkedCollectionReference> getLocation();
}
