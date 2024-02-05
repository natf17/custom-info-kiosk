package com.ppublica.apps.kiosk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.EventsPageService;
import com.ppublica.apps.kiosk.service.payloads.PageDeletePayload;
import com.ppublica.apps.kiosk.service.payloads.events.EventsPagePayload;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.events.EventsPageView;

@Controller
public class EventsPageController {

    @Autowired
    private EventsPageService service;

    @QueryMapping
    public EventsPageView eventsPage(@Argument String locale) {
        EventsPageView eventsPageView = service.getEventsPage(locale).get();

        return eventsPageView;
    }

    @MutationMapping
    public EventsPageView createEventsPage(@Argument String locale, @Argument EventsPagePayload input) {
        EventsPageView eventsPageView = service.createEventsPage(locale, input.data());

        return eventsPageView;
    }

    @MutationMapping
    public EventsPageView updateEventsPage(@Argument String locale, @Argument EventsPagePayload input) {
        EventsPageView eventsPageView = service.updateEventsPage(locale, input.data());

        return eventsPageView;
    }

    
    @MutationMapping
    public MessageResponse deleteEventsPage(@Argument PageDeletePayload input) {
        String locale = input.where().locale();
        service.deleteEventsPage(locale);

        return new MessageResponse("Deleted the eventsPage in " + locale);
    }
    
}
