package com.ppublica.apps.kiosk.service.util.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultEventSeasonType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EventSeasonType;
import com.ppublica.apps.kiosk.service.payloads.data.eventseason.EventSeasonInput;
import com.ppublica.apps.kiosk.service.util.IntegerKioskFieldCreator;
import com.ppublica.apps.kiosk.service.util.LocalizedFieldsProcessor;
import com.ppublica.apps.kiosk.service.util.SameTypeConverter;
import com.ppublica.apps.kiosk.service.util.StringKioskFieldCreator;

@Component
public class EventSeasonInputConverter {
    private EventSeasonFieldsProcessor localizedFieldsProcessor = new EventSeasonFieldsProcessor();

    // will return list with >= 1 elements
    // always creates a KioskCollectionMetadata with published status and a creation/last modified time of "now"
    // grabs name from first localized field and sets is as the collection name
    public List<EventSeasonType> toLocalizedEventSeasons(EventSeasonInput adminInput) {
        Map<Long, DefaultEventSeasonType.Builder> eventSeasonBuilders = new HashMap<>();

        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(eventSeasonBuilders, adminInput.theme(), (builder, field) -> builder.theme(field), new SameTypeConverter<String>(), new StringKioskFieldCreator(), CollectionType.EVENT_SEASON);
        localizedFieldsProcessor.processLocalizedFieldsWithBuilder(eventSeasonBuilders, adminInput.durationText(), (builder, field) -> builder.durationText(field), new SameTypeConverter<String>(), new StringKioskFieldCreator(), CollectionType.EVENT_SEASON);

        localizedFieldsProcessor.processParentFieldWithBuilder(eventSeasonBuilders, adminInput.type(), (builder, field) -> builder.seasonType(field), new SameTypeConverter<String>(), new StringKioskFieldCreator());
        localizedFieldsProcessor.processParentFieldWithBuilder(eventSeasonBuilders, adminInput.durationDays(), (builder, field) -> builder.durationDays(field), new SameTypeConverter<Integer>(), new IntegerKioskFieldCreator());
        localizedFieldsProcessor.processParentFieldWithBuilder(eventSeasonBuilders, adminInput.serviceYear(), (builder, field) -> builder.serviceYear(field), new SameTypeConverter<Integer>(), new IntegerKioskFieldCreator());

        return eventSeasonBuilders.values().stream()
                                        .map(builder -> builder.build())
                                        .collect(Collectors.toList());
    }

    class EventSeasonFieldsProcessor extends LocalizedFieldsProcessor<DefaultEventSeasonType.Builder, DefaultEventSeasonType> {
        @Override
        public DefaultEventSeasonType.Builder getBuilder(CollectionType colltype) {
            return new DefaultEventSeasonType.Builder();
        }
    }
}
