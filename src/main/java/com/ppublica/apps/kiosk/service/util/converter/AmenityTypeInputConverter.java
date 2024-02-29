package com.ppublica.apps.kiosk.service.util.converter;

import java.util.HashMap;
import java.util.Map;

import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.service.payloads.AmenityInput;
import com.ppublica.apps.kiosk.service.util.BooleanKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.DefaultAmenityTypeLocalizedFieldsProcessor;
import com.ppublica.apps.kiosk.service.util.ImageInputToKioskImageTypeConverter;
import com.ppublica.apps.kiosk.service.util.ImageKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.LinkedCollectionReferenceKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.LongToLinkedCollectionRefTypeConverter;
import com.ppublica.apps.kiosk.service.util.SameTypeConverter;
import com.ppublica.apps.kiosk.service.util.StringKioskFieldCreator;

public class AmenityTypeInputConverter {
    private DefaultAmenityTypeLocalizedFieldsProcessor localizedFieldsProcessor = new DefaultAmenityTypeLocalizedFieldsProcessor();


    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    public Map<Long, DefaultAmenityType.Builder> toAmenityTypeBuilders(AmenityInput adminInput) {
        Map<Long, DefaultAmenityType.Builder> amenityBuilders = new HashMap<>();

        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.name(), (builder, field) -> builder.name(field), new SameTypeConverter<String>(), new StringKioskFieldCreator());
        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.svgElemId(), (builder, field) -> builder.svgElemId(field), new SameTypeConverter<String>(), new StringKioskFieldCreator());
        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.note(), (builder, field) -> builder.note(field), new SameTypeConverter<String>(), new StringKioskFieldCreator());
        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.featImg(), (builder, field) -> builder.featImg(field), new ImageInputToKioskImageTypeConverter(), new ImageKioskFieldCreator());

        localizedFieldsProcessor.processParentFieldWithBuilder(amenityBuilders, adminInput.isWheelchairAccessible(), (builder, field) -> builder.isWheelChairAccessible(field), new SameTypeConverter<Boolean>(), new BooleanKioskFieldCreator());
        localizedFieldsProcessor.processParentFieldWithBuilder(amenityBuilders, adminInput.locationId(), (builder, field) -> builder.location(field), new LongToLinkedCollectionRefTypeConverter(), new LinkedCollectionReferenceKioskFieldCreator());

        return amenityBuilders;
    }
}
