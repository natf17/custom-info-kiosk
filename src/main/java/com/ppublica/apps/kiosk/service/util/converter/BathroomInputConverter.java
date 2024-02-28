package com.ppublica.apps.kiosk.service.util.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultBathroomType;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;
import com.ppublica.apps.kiosk.service.util.BathroomImageToKioskImageTypeConverter;
import com.ppublica.apps.kiosk.service.util.BooleanKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.DefaultAmenityTypeLocalizedFieldsProcessor;
import com.ppublica.apps.kiosk.service.util.GenderInfoProcessor;
import com.ppublica.apps.kiosk.service.util.ImageKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.LinkedCollectionReferenceKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.LongToLinkedCollectionRefTypeConverter;
import com.ppublica.apps.kiosk.service.util.SameTypeConverter;
import com.ppublica.apps.kiosk.service.util.StringKioskFieldCreator;

public class BathroomInputConverter {
    @Autowired
    private DefaultAmenityTypeLocalizedFieldsProcessor localizedFieldsProcessor;

    @Autowired
    private GenderInfoProcessor genderInfoProcessor;

    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    public List<BathroomType> toLocalizedBathrooms(BathroomInput adminInput) {
        Map<Long, DefaultAmenityType.Builder> amenityBuilders = new HashMap<>();

        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.name(), (builder, field) -> builder.name(field), new SameTypeConverter<String>(), new StringKioskFieldCreator());
        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.svgElemId(), (builder, field) -> builder.svgElemId(field), new SameTypeConverter<String>(), new StringKioskFieldCreator());
        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.note(), (builder, field) -> builder.note(field), new SameTypeConverter<String>(), new StringKioskFieldCreator());
        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(amenityBuilders, adminInput.featImg(), (builder, field) -> builder.featImg(field), new BathroomImageToKioskImageTypeConverter(), new ImageKioskFieldCreator());

        localizedFieldsProcessor.processParentFieldWithBuilder(amenityBuilders, adminInput.isWheelchairAccessible(), (builder, field) -> builder.isWheelChairAccessible(field), new SameTypeConverter<Boolean>(), new BooleanKioskFieldCreator());
        localizedFieldsProcessor.processParentFieldWithBuilder(amenityBuilders, adminInput.locationId(), (builder, field) -> builder.location(field), new LongToLinkedCollectionRefTypeConverter(), new LinkedCollectionReferenceKioskFieldCreator());

        List<BathroomType> bathrooms = new ArrayList<>();
        for(DefaultAmenityType.Builder builder : amenityBuilders.values()) {
            bathrooms.add(new DefaultBathroomType(builder.build(), genderInfoProcessor.createGenderPiece(adminInput.gender(), new SameTypeConverter<String>(), new StringKioskFieldCreator())));
        }

        return bathrooms;
    }
}
