package com.ppublica.apps.kiosk.service.util.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultLocationType;
import com.ppublica.apps.kiosk.domain.model.kiosk.LocationType;
import com.ppublica.apps.kiosk.service.payloads.locations.LocationInput;
import com.ppublica.apps.kiosk.service.util.ImageKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.IntegerKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.LocalizedFieldsProcessor;
import com.ppublica.apps.kiosk.service.util.MapImageInputToKioskImageTypeConverter;
import com.ppublica.apps.kiosk.service.util.SameTypeConverter;
import com.ppublica.apps.kiosk.service.util.StringKioskFieldCreator;

@Component
public class LocationInputConverter {
    private LocationFieldsProcessor localizedFieldsProcessor = new LocationFieldsProcessor();


    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    public List<LocationType> toLocalizedLocations(LocationInput adminInput) {
        Map<Long, DefaultLocationType.Builder> locationBuilders = new HashMap<>();

        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(locationBuilders, adminInput.level_name(), (builder, field) -> builder.level_name(field), new SameTypeConverter<String>(), new StringKioskFieldCreator(), CollectionType.LOCATION);
        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(locationBuilders, adminInput.fullname(), (builder, field) -> builder.fullName(field), new SameTypeConverter<String>(), new StringKioskFieldCreator(), CollectionType.LOCATION);
        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(locationBuilders, adminInput.map(), (builder, field) -> builder.map(field), new MapImageInputToKioskImageTypeConverter(), new ImageKioskFieldCreator(), CollectionType.LOCATION);

        localizedFieldsProcessor.processParentFieldWithBuilder(locationBuilders, adminInput.level_num(), (builder, field) -> builder.level_num(field), new SameTypeConverter<Integer>(), new IntegerKioskFieldCreator());

        return locationBuilders.values().stream()
                                        .map(builder -> builder.build())
                                        .collect(Collectors.toList());
    }

    class LocationFieldsProcessor extends LocalizedFieldsProcessor<DefaultLocationType.Builder, DefaultLocationType> {
        @Override
        public DefaultLocationType.Builder getBuilder(CollectionType colltype) {
            return new DefaultLocationType.Builder();
        }
    }
}
