package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.WaterFountainType;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainAdminView;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainImage;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainView;

public class WaterFountainViewsConverter {
    public WaterFountainView buildView(WaterFountainType waterFountain) {
        KioskImage featureImage = waterFountain.featImg().fieldValue();
        WaterFountainImage image = featureImage != null ? new WaterFountainImage(featureImage.location(), featureImage.width(), featureImage.height()): null;

        return new WaterFountainView(Long.toString(waterFountain.collectionId()), 
                                waterFountain.name().fieldValue(), 
                                waterFountain.isWheelChairAccessible().fieldValue(),
                                waterFountain.svgElemId().fieldValue(),
                                waterFountain.note().fieldValue(),
                                waterFountain.location().fieldValue().linkedCollectionId(),
                                image);
    }

    // expects at least one element in the list
    public WaterFountainAdminView buildAdminView(List<? extends WaterFountainType> waterFountains) {
        WaterFountainType arbitraryWaterFountain = waterFountains.get(0);

        String id = arbitraryWaterFountain.collectionId() != null ? Long.toString(arbitraryWaterFountain.collectionId()): null;

        List<LocalizedField> nameLoc = new ArrayList<>();
        List<LocalizedField> svgElemIdLoc = new ArrayList<>();
        List<LocalizedField> noteLoc = new ArrayList<>();
        List<LocalizedView<WaterFountainImage>> featImgLoc = new ArrayList<>();

        // process localizable fields
        for (WaterFountainType waterFountain : waterFountains) {
            String locale = waterFountain.kioskCollectionMetadata().getCollectionInternals().getKioskLocale();

            String name = waterFountain.name().fieldValue();
            if(name != null) {
                nameLoc.add(new LocalizedField(locale, name));
            }

            String svgElem = waterFountain.svgElemId().fieldValue();

            if(svgElem != null) {
                svgElemIdLoc.add(new LocalizedField(locale, svgElem));
            }

            String note = waterFountain.note().fieldValue();
            if(note != null) {
                noteLoc.add(new LocalizedField(locale, note));
            }

            KioskImage featureImage = waterFountain.featImg().fieldValue();
            
            WaterFountainImage image = featureImage != null ? new WaterFountainImage(featureImage.location(), featureImage.width(), featureImage.height()): null;
            featImgLoc.add(new LocalizedView<WaterFountainImage>(locale, image));

        }

        // process non-locale fields
        Boolean isWheelchairAccessible = arbitraryWaterFountain.isWheelChairAccessible().fieldValue();
        Long locationId = arbitraryWaterFountain.location().fieldValue().linkedCollectionId();


        return new WaterFountainAdminView(id, nameLoc, isWheelchairAccessible, svgElemIdLoc, noteLoc, locationId, featImgLoc);
    }
}
