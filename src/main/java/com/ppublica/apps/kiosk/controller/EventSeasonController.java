package com.ppublica.apps.kiosk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.EventSeasonService;
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
    
}
