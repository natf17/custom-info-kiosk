package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEventType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.SeasonalEventKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.data.seasonalevent.SeasonalEventInput;
import com.ppublica.apps.kiosk.service.util.converter.SeasonalEventInputConverter;
import com.ppublica.apps.kiosk.service.util.converter.SeasonalEventViewsConverter;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventAdminView;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventView;

@Service
public class SeasonalEventDataService extends NonLocalizedCollectionServiceBase<SeasonalEventKioskCollectionAdapter, SeasonalEventType, SeasonalEventKioskCollectionAdapter.Builder> {

    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;

    @Autowired
    private SeasonalEventViewsConverter seasonalEventViewsConverter;

    @Autowired
    private SeasonalEventInputConverter seasonalEventInputConverter;

    @Autowired
    private AdapterBuilderGenerator<SeasonalEventKioskCollectionAdapter.Builder> adapterBuilderGenerator;

    // TODO: remove locale
    public List<SeasonalEventView> getSeasonalEvents(String locale, String sort) {
        List<SeasonalEventView> seasonalEventViews = loadAdapters(CollectionType.SEASONAL_EVENT, collSharedPropsRepo)
                                                .stream()
                                                .map(seasonalevent -> seasonalEventViewsConverter.buildView(seasonalevent))
                                                .collect(Collectors.toList());


        return seasonalEventViews;
    }

    //TODO: remove seasonLocale
    public Optional<SeasonalEventView> getSeasonalEvent(Long seasonalEventId, String seasonLocale) {
        SeasonalEventKioskCollectionAdapter adapter = loadAdapter(seasonalEventId, collSharedPropsRepo);

        if(adapter == null) {
            return Optional.empty();
        }

        return Optional.of(seasonalEventViewsConverter.buildView(adapter));
    }

    public List<SeasonalEventView> createSeasonalEventsBatch(Long eventSeasonId, List<SeasonalEventInput> data) {
        throw new UnsupportedOperationException();
    }

    public SeasonalEventAdminView createSeasonalEvent(Long eventSeasonId, SeasonalEventInput data) {

        SeasonalEventType seasonalEvent = seasonalEventInputConverter.toSeasonalEvent(data);

        SeasonalEventKioskCollectionAdapter newSeasonalEventAdapter = save(seasonalEvent, collSharedPropsRepo);
        
        return seasonalEventViewsConverter.buildAdminView(List.of(newSeasonalEventAdapter));
    }

    public SeasonalEventAdminView updateSeasonalEvent(Long seasonalEventId, SeasonalEventInput data) {
        SeasonalEventType seasonalEvent = seasonalEventInputConverter.toSeasonalEvent(data);

        SeasonalEventKioskCollectionAdapter updatedSeasonalEventAdapter = update(seasonalEvent, seasonalEventId, collSharedPropsRepo);

        return seasonalEventViewsConverter.buildAdminView(List.of(updatedSeasonalEventAdapter));
    }

    public void deleteSeasonalEvent(Long seasonalEventId) {
        delete(seasonalEventId, collSharedPropsRepo);
    }

    @Override
    protected SeasonalEventKioskCollectionAdapter.Builder getAdapterBuilder() {
        return adapterBuilderGenerator.newAdapterBuilder();
    }
    
}
