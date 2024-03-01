package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultWaterFountainType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EmptyWaterFountainPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.WaterFountainType;
import com.ppublica.apps.kiosk.service.payloads.waterfountains.WaterFountainInput;

/*
 * This converter only processes fields in the AmenityType
 */
public class WaterFountainInputConverter {
    private AmenityTypeInputConverter amenityTypeConverter = new AmenityTypeInputConverter();


    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    public List<WaterFountainType> toLocalizedWaterFountains(WaterFountainInput adminInput) {
        Map<Long, DefaultAmenityType.Builder> amenityBuilders = amenityTypeConverter.toAmenityTypeBuilders(adminInput, CollectionType.WATER_FOUNTAIN);

        List<WaterFountainType> waterFountains = new ArrayList<>();
        for(DefaultAmenityType.Builder builder : amenityBuilders.values()) {
            waterFountains.add(new DefaultWaterFountainType(builder.build(), new EmptyWaterFountainPiece()));
        }

        return waterFountains;
    }
}