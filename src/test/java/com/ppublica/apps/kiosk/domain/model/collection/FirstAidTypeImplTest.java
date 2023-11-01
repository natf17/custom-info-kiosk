package com.ppublica.apps.kiosk.domain.model.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class FirstAidTypeImplTest {
    @Test
    public void testMethods() {

        LocalDate testDate = LocalDate.of(2023, 9, 12);
        LocalDateTime testDateTime = LocalDateTime.of(2023, 1, 9, 9, 30);

        Image testFeatImg = new Image("url", 1, 2);

        FirstAidType firstAidType = new FirstAidTypeImpl.Builder()
                                        .id(2L)
                                        .collectionNameField(new KioskCollectionField<String>("coll_name_fieldName", "coll_name_fieldValue", true))
                                        .kioskCollectionMetadata(new KioskCollectionMetadata(1L, PageStatus.PUBLISHED, testDate, testDateTime))
                                        .featImg(new KioskCollectionField<Image>("featImg_fieldName", testFeatImg, true))
                                        .svgElemId(new KioskCollectionField<Long>("svgElemId_fieldName", 6L, true))
                                        .isWheelChairAccessible(new KioskCollectionField<Boolean>("isWheelChairAccessible_fieldName", true, true))
                                        .note(new KioskCollectionField<String>("note_fieldName", "note_fieldValue", true))
                                        .name(new KioskCollectionField<String>("name_fieldName", "name_fieldValue", true))
                                        .location(new KioskCollectionField<LinkedCollectionReference>("location_fieldName", new LinkedCollectionReference(5L), true))
                                        .build();

        Assertions.assertEquals(2L, firstAidType.getId());
        Assertions.assertEquals("coll_name_fieldName", firstAidType.getKioskCollectionNameField().getFieldName());
        Assertions.assertEquals(1L, firstAidType.getKioskCollectionMetadata().getKioskLocaleId());
        Assertions.assertEquals("featImg_fieldName", firstAidType.getFeatImg().getFieldName());
        Assertions.assertEquals("svgElemId_fieldName", firstAidType.getSvgElemId().getFieldName());
        Assertions.assertEquals("isWheelChairAccessible_fieldName", firstAidType.isWheelChairAccessible().getFieldName());
        Assertions.assertEquals("note_fieldName", firstAidType.getNote().getFieldName());
        Assertions.assertEquals("name_fieldName", firstAidType.getName().getFieldName());
        Assertions.assertEquals("location_fieldName", firstAidType.getLocation().getFieldName());
        Assertions.assertEquals(CollectionTypeName.FIRST_AID, firstAidType.getKioskCollectionTypeName());

    }
    
}
