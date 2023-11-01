package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class LocationTypeImplTest {
    
    @Test
    public void testMethods() {

        LocalDate testDate = LocalDate.of(2023, 9, 12);
        LocalDateTime testDateTime = LocalDateTime.of(2023, 1, 9, 9, 30);

        Image testMap = new Image("url", 1, 2);

        LocationType locationType = new LocationTypeImpl.Builder()
                                        .id(2L)
                                        .collectionNameField(new KioskCollectionField<String>("coll_name_fieldName", "coll_name_fieldValue", true))
                                        .kioskCollectionMetadata(new KioskCollectionMetadata(1L, PageStatus.PUBLISHED, testDate, testDateTime))
                                        .levelName(new KioskCollectionField<String>("levelName_fieldName", "levelName_fieldValue", true))
                                        .levelNum(new KioskCollectionField<Long>("levelNum_fieldName", 7L, true))
                                        .fullName(new KioskCollectionField<String>("fullName_fieldName", "fullName_fieldValue", true))
                                        .map(new KioskCollectionField<Image>("map_fieldName", testMap, true))
                                        .build();


        Assertions.assertEquals(2L, locationType.getId());
    }
}
