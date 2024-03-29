package com.ppublica.apps.kiosk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EventSeasonType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.EventSeasonKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.data.eventseason.EventSeasonInput;
import com.ppublica.apps.kiosk.service.util.LocalizedViewKey;
import com.ppublica.apps.kiosk.service.util.converter.EventSeasonInputConverter;
import com.ppublica.apps.kiosk.service.util.converter.EventSeasonViewsConverter;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonAdminView;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonView;

@Service
public class EventSeasonDataService extends LocalizedCollectionServiceBase<EventSeasonKioskCollectionAdapter, EventSeasonType, EventSeasonKioskCollectionAdapter.Builder> {
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;

    @Autowired
    private EventSeasonViewsConverter eventSeasonViewsConverter;

    @Autowired
    private EventSeasonInputConverter eventSeasonInputConverter;

    @Autowired
    private AdapterBuilderGenerator<EventSeasonKioskCollectionAdapter.Builder> adapterBuilderGenerator;

    public List<EventSeasonView> getEventSeasons(String locale) {
         List<EventSeasonView> eventSeasonViews = loadAdapters(CollectionType.EVENT_SEASON, locale, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(eventSeason -> eventSeasonViewsConverter.buildView(eventSeason))
                                                .collect(Collectors.toList());


        return eventSeasonViews;

    }

    public Optional<EventSeasonAdminView> getEventSeasonAdmin(Long eventSeasonId) {
        List<EventSeasonKioskCollectionAdapter> adapters = loadAdapters(eventSeasonId, collSharedPropsRepo, collLocalizedPropsRepo);

        if(adapters.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(eventSeasonViewsConverter.buildAdminView(adapters));
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public EventSeasonAdminView createEventSeason(EventSeasonInput data) {
        // locaiton input to location type
        List<? extends EventSeasonType> eventSeasons = eventSeasonInputConverter.toLocalizedEventSeasons(data);

        List<EventSeasonKioskCollectionAdapter> newEventSeasonAdapters = save(eventSeasons, collSharedPropsRepo, collLocalizedPropsRepo);
        
        return eventSeasonViewsConverter.buildAdminView(newEventSeasonAdapters);
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public EventSeasonAdminView updateEventSeason(Long seasonalEventId, EventSeasonInput data) {
        List<? extends EventSeasonType> eventSeasons = eventSeasonInputConverter.toLocalizedEventSeasons(data);

        List<? extends EventSeasonType> updatedEventSeasonAdapters = update(eventSeasons, seasonalEventId, collSharedPropsRepo, collLocalizedPropsRepo);

        return eventSeasonViewsConverter.buildAdminView(updatedEventSeasonAdapters);
    }

    // maybe could be optimized?
    public Map<LocalizedViewKey,EventSeasonView> getBatchEventSeasons(Set<LocalizedViewKey> keys) {
        Map<LocalizedViewKey,EventSeasonView> eventSeasonsMap = new HashMap<>();
        for(LocalizedViewKey key : keys) {
            Optional<EventSeasonAdminView> adminViewOpt = getEventSeasonAdmin(key.viewId());
            
            if(adminViewOpt.isEmpty()) {
                eventSeasonsMap.put(key, null);
                continue;
            }
            EventSeasonAdminView adminView = adminViewOpt.get();
            
            EventSeasonView view = new EventSeasonView(adminView.id(), adminView.type(), adminView.durationDays(), fieldMatchingLocale(adminView.theme(), key.locale()), adminView.serviceYear(), adminView.seasonYears(), fieldMatchingLocale(adminView.durationText(), key.locale()));

            eventSeasonsMap.put(key, view);
        }

        return eventSeasonsMap;
    }

    public void deleteEventSeason(Long eventSeasonId) {
        delete(eventSeasonId, collSharedPropsRepo, collLocalizedPropsRepo);
    }

    @Override
    protected EventSeasonKioskCollectionAdapter.Builder getAdapterBuilder() {
        return adapterBuilderGenerator.newAdapterBuilder();
    }

    private String fieldMatchingLocale(List<LocalizedField> fields, String locale) {
        LocalizedField matchingField =  fields.stream()
                                                .filter(field -> field.locale().equals(locale))
                                                .findFirst()
                                            .get();

        return matchingField != null ? matchingField.value() : null;
    }
}
