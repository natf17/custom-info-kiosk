package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public interface Location extends KioskCollectionType {

    KioskCollectionField<String> getLevelNameField();

    KioskCollectionField<Long> getLevelNumField();

    KioskCollectionField<String> getFullNameField();

    KioskCollectionField<Image> getMapField();

}
