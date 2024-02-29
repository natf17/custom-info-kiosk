package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultBathroomType;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;
import com.ppublica.apps.kiosk.service.util.GenderInfoProcessor;
import com.ppublica.apps.kiosk.service.util.SameTypeConverter;
import com.ppublica.apps.kiosk.service.util.StringKioskFieldCreator;

public class BathroomInputConverter {

    private GenderInfoProcessor genderInfoProcessor = new GenderInfoProcessor();
    private AmenityTypeInputConverter amenityTypeConverter = new AmenityTypeInputConverter();


    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    public List<BathroomType> toLocalizedBathrooms(BathroomInput adminInput) {
        Map<Long, DefaultAmenityType.Builder> amenityBuilders = amenityTypeConverter.toAmenityTypeBuilders(adminInput, CollectionType.BATHROOM);
        
        List<BathroomType> bathrooms = new ArrayList<>();
        for(DefaultAmenityType.Builder builder : amenityBuilders.values()) {
            bathrooms.add(new DefaultBathroomType(builder.build(), genderInfoProcessor.createGenderPiece(adminInput.gender(), new SameTypeConverter<String>(), new StringKioskFieldCreator())));
        }

        return bathrooms;
    }
}
