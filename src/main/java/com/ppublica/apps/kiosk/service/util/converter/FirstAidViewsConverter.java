package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.FirstAidType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidAdminView;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidImage;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidView;

@Component
public class FirstAidViewsConverter {
    public FirstAidView buildView(FirstAidType firstAid) {
        KioskImage featureImage = firstAid.featImg().fieldValue();
        FirstAidImage image = featureImage != null ? new FirstAidImage(featureImage.location(), featureImage.width(), featureImage.height()): null;

        return new FirstAidView(Long.toString(firstAid.collectionId()), 
                                firstAid.name().fieldValue(), 
                                firstAid.isWheelChairAccessible().fieldValue(),
                                firstAid.svgElemId().fieldValue(),
                                firstAid.note().fieldValue(),
                                firstAid.location().fieldValue().linkedCollectionId(),
                                image);
    }

    // expects at least one element in the list
    public FirstAidAdminView buildAdminView(List<? extends FirstAidType> firstAids) {
        FirstAidType arbitraryFirstAid = firstAids.get(0);

        String id = arbitraryFirstAid.collectionId() != null ? Long.toString(arbitraryFirstAid.collectionId()): null;

        List<LocalizedField> nameLoc = new ArrayList<>();
        List<LocalizedField> svgElemIdLoc = new ArrayList<>();
        List<LocalizedField> noteLoc = new ArrayList<>();
        List<LocalizedView<FirstAidImage>> featImgLoc = new ArrayList<>();

        // process localizable fields
        for (FirstAidType firstAid : firstAids) {
            String locale = firstAid.kioskCollectionMetadata().getCollectionInternals().getKioskLocale();

            String name = firstAid.name().fieldValue();
            if(name != null) {
                nameLoc.add(new LocalizedField(locale, name));
            }

            String svgElem = firstAid.svgElemId().fieldValue();

            if(svgElem != null) {
                svgElemIdLoc.add(new LocalizedField(locale, svgElem));
            }

            String note = firstAid.note().fieldValue();
            if(note != null) {
                noteLoc.add(new LocalizedField(locale, note));
            }

            KioskImage featureImage = firstAid.featImg().fieldValue();
            
            FirstAidImage image = featureImage != null ? new FirstAidImage(featureImage.location(), featureImage.width(), featureImage.height()): null;
            featImgLoc.add(new LocalizedView<FirstAidImage>(locale, image));

        }

        // process non-locale fields
        Boolean isWheelchairAccessible = arbitraryFirstAid.isWheelChairAccessible().fieldValue();
        Long locationId = arbitraryFirstAid.location().fieldValue().linkedCollectionId();


        return new FirstAidAdminView(id, nameLoc, isWheelchairAccessible, svgElemIdLoc, noteLoc, locationId, featImgLoc);
    }
}
