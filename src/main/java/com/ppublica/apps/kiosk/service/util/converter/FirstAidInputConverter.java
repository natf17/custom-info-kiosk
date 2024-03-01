package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultFirstAidType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EmptyFirstAidPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.FirstAidType;
import com.ppublica.apps.kiosk.service.payloads.firstaid.FirstAidInput;

/*
 * This converter only processes fields in the AmenityType
 */
public class FirstAidInputConverter {
    private AmenityTypeInputConverter amenityTypeConverter = new AmenityTypeInputConverter();


    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    public List<FirstAidType> toLocalizedFirstAids(FirstAidInput adminInput) {
        Map<Long, DefaultAmenityType.Builder> amenityBuilders = amenityTypeConverter.toAmenityTypeBuilders(adminInput, CollectionType.FIRST_AID);

        List<FirstAidType> firstAids = new ArrayList<>();
        for(DefaultAmenityType.Builder builder : amenityBuilders.values()) {
            firstAids.add(new DefaultFirstAidType(builder.build(), new EmptyFirstAidPiece()));
        }

        return firstAids;
    }
}
