package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class BathroomTest {

    @Test
    public void buildBathroomInstance() {

        LocalDate testDate = LocalDate.of(2023, 9, 12);
        LocalDateTime testDateTime = LocalDateTime.of(2023, 1, 9, 9, 30);

        Image testFeatImg = new Image("url", 1, 2);

        Bathroom bathroomInstance = new Bathroom.Builder()
                                        .id(2L)
                                        .collectionNameField(new KioskCollectionField<String>("coll_name_fieldName", "coll_name_fieldValue", true))
                                        .kioskCollectionMetadata(new KioskCollectionMetadata(1L, PageStatus.PUBLISHED, testDate, testDateTime))
                                        .featImg(new KioskCollectionField<Image>("featImg_fieldName", testFeatImg, true))
                                        .svgElemId(new KioskCollectionField<Long>("svgElemId_fieldName", 6L, true))
                                        .isWheelChairAccessible(new KioskCollectionField<Boolean>("isWheelChairAccessible_fieldName", true, true))
                                        .build();

        Assertions.assertEquals(2L, bathroomInstance.getId());
    }
    
}
