package com.ppublica.apps.kiosk.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.EventSeasonDataService;
import com.ppublica.apps.kiosk.service.SeasonalEventDataService;
import com.ppublica.apps.kiosk.service.payloads.DeletePayload;
import com.ppublica.apps.kiosk.service.payloads.data.GraphQLPayload;
import com.ppublica.apps.kiosk.service.payloads.data.seasonalevent.SeasonalEventInput;
import com.ppublica.apps.kiosk.service.payloads.data.seasonalevent.SeasonalEventPayload;
import com.ppublica.apps.kiosk.service.util.LocalizedViewKey;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonView;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventView;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Controller
public class SeasonalEventController {
    @Autowired
    private SeasonalEventDataService service;

    @Autowired
    private EventSeasonDataService seasonService;

    public SeasonalEventController(BatchLoaderRegistry registry) {
        registry.forTypePair(LocalizedViewKey.class, EventSeasonView.class).registerMappedBatchLoader((eventSeasonKeys, batchLoaderEnvironment) -> {
            return Mono.fromSupplier(() -> seasonService.getBatchEventSeasons(eventSeasonKeys));
        });
    }

    
    @QueryMapping
    public List<SeasonalEventView> seasonalEvents(@Argument String locale, @Argument String sort, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", locale);

        List<SeasonalEventView> seasonalEventsView = service.getSeasonalEvents(locale, sort);

        return seasonalEventsView;
    }

    @QueryMapping
    public SeasonalEventView seasonalEvent(@Argument String locale, @Argument Long id, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", locale);

        SeasonalEventView seasonalEventView = service.getSeasonalEvent(id, locale).get();

        return seasonalEventView;
    }

    @MutationMapping
    public List<SeasonalEventView> createSeasonalEvents(@Argument Long eventSeasonId, @Argument GraphQLPayload<List<SeasonalEventInput>> input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        List<SeasonalEventView> newSeasonalEventViews = service.createSeasonalEventsBatch(eventSeasonId, input.data());

        return newSeasonalEventViews;
    }

    @MutationMapping
    public SeasonalEventView updateSeasonalEvent(@Argument Long seasonalEventId, @Argument SeasonalEventPayload input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        SeasonalEventView updatedSeasonalEventView = service.updateSeasonalEvent(seasonalEventId, input.data());

        return updatedSeasonalEventView;
    }

    @MutationMapping
    public MessageResponse deleteSeasonalEvent(@Argument DeletePayload input) {
        Long idToDelete = Long.parseLong(input.where().id());
        service.deleteSeasonalEvent(idToDelete);

        return new MessageResponse("Deleted the seasonal event with id = " + idToDelete);
    }

    @SchemaMapping(typeName = "SeasonalEvent")
    public CompletableFuture<EventSeasonView> event_season(DataFetchingEnvironment env, SeasonalEventView seasonalEvent, DataLoader<LocalizedViewKey, EventSeasonView> loader) {
        String locale = env.getGraphQlContext().get("locale");
        return loader.load(new LocalizedViewKey(seasonalEvent.seasonId(), locale));

    }

}
