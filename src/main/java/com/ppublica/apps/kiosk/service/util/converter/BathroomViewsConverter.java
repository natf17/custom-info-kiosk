package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomImage;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;

public class BathroomViewsConverter {
    public BathroomView buildView(BathroomType bathroom) {
        KioskImage featureImage = bathroom.featImg().fieldValue();
        BathroomImage image = featureImage != null ? new BathroomImage(featureImage.location(), featureImage.width(), featureImage.height()): null;

        return new BathroomView(Long.toString(bathroom.collectionId()), 
                                bathroom.name().fieldValue(), 
                                bathroom.gender().fieldValue(),
                                bathroom.isWheelChairAccessible().fieldValue(),
                                bathroom.svgElemId().fieldValue(),
                                bathroom.note().fieldValue(),
                                bathroom.location().fieldValue().linkedCollectionId(),
                                image);
    }

    // expects at least one element in the list
    public BathroomAdminView buildAdminView(List<? extends BathroomType> bathrooms) {
        BathroomType arbitraryBathroom = bathrooms.get(0);

        String id = arbitraryBathroom.collectionId() != null ? Long.toString(arbitraryBathroom.collectionId()): null;

        List<LocalizedField> nameLoc = new ArrayList<>();
        List<LocalizedField> svgElemIdLoc = new ArrayList<>();
        List<LocalizedField> noteLoc = new ArrayList<>();
        List<LocalizedView<BathroomImage>> featImgLoc = new ArrayList<>();

        // process localizable fields
        for (BathroomType bathroom : bathrooms) {
            String locale = bathroom.kioskCollectionMetadata().getCollectionInternals().getKioskLocale();

            String name = bathroom.name().fieldValue();
            if(name != null) {
                nameLoc.add(new LocalizedField(locale, name));
            }

            String svgElem = bathroom.svgElemId().fieldValue();

            if(svgElem != null) {
                svgElemIdLoc.add(new LocalizedField(locale, svgElem));
            }

            String note = bathroom.note().fieldValue();
            if(note != null) {
                noteLoc.add(new LocalizedField(locale, note));
            }

            KioskImage featureImage = bathroom.featImg().fieldValue();
            
            BathroomImage image = featureImage != null ? new BathroomImage(featureImage.location(), featureImage.width(), featureImage.height()): null;
            featImgLoc.add(new LocalizedView<BathroomImage>(locale, image));

        }

        // process non-locale fields
        String gender = arbitraryBathroom.gender().fieldValue();
        Boolean isWheelchairAccessible = arbitraryBathroom.isWheelChairAccessible().fieldValue();
        Long locationId = arbitraryBathroom.location().fieldValue().linkedCollectionId();


        return new BathroomAdminView(id, nameLoc, gender, isWheelchairAccessible, svgElemIdLoc, noteLoc, locationId, featImgLoc);
    }
}
