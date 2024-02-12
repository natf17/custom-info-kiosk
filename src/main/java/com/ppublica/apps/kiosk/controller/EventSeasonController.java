package com.ppublica.apps.kiosk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.collection.EventSeasonService;
import com.ppublica.apps.kiosk.service.payloads.DeletePayload;
import com.ppublica.apps.kiosk.service.payloads.data.eventseason.EventSeasonPayload;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonAdminView;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonView;

@Controller
public class EventSeasonController {

    @Autowired
    private EventSeasonService service;
    
    @QueryMapping
    public List<EventSeasonView> eventSeasons(@Argument String locale) {

        List<EventSeasonView> eventSeasonsView = service.getEventSeasons(locale);

        return eventSeasonsView;
    }

    @MutationMapping
    public EventSeasonAdminView createEventSeason(@Argument EventSeasonPayload input) {
        EventSeasonAdminView eventSeasonAdminView = service.createEventSeason(input.data());

        return eventSeasonAdminView;
    }

    @MutationMapping
    public EventSeasonAdminView updateEventSeason(@Argument Long seasonalEventId, @Argument EventSeasonPayload input) {
        EventSeasonAdminView updatedEventSeasonAdminView = service.updateEventSeason(seasonalEventId, input.data());

        return updatedEventSeasonAdminView;
    }

    
    @MutationMapping
    public MessageResponse deleteEventSeason(@Argument DeletePayload input) {
        Long idToDelete = Long.parseLong(input.where().id());
        service.deleteEventSeason(idToDelete);

        return new MessageResponse("Deleted the event season with id = " + idToDelete);
    }

}
