package com.ppublica.apps.kiosk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.MapPageService;
import com.ppublica.apps.kiosk.service.payloads.PageDeletePayload;
import com.ppublica.apps.kiosk.service.payloads.map.MapPagePayload;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.map.MapImageView;
import com.ppublica.apps.kiosk.service.views.map.MapImagesView;
import com.ppublica.apps.kiosk.service.views.map.MapPageView;

@Controller
public class MapPageController {
    @Autowired
    private MapPageService service;

    @QueryMapping
    public MapPageView mapPage(@Argument String locale) {
        MapPageView mapPageView = service.getMapPage(locale).get();

        return mapPageView;
    }

    @MutationMapping
    public MapPageView createMapPage(@Argument String locale, @Argument MapPagePayload input) {
        MapPageView mapPageView = service.createMapPage(locale, input.data());

        return mapPageView;

    }

    @MutationMapping
    public MapPageView updateMapPage(@Argument String locale, @Argument MapPagePayload input) {
        MapPageView mapPageView = service.updateMapPage(locale, input.data());

        return mapPageView;
    }
    
    @MutationMapping
    public MessageResponse deleteMapPage(@Argument PageDeletePayload input) {
        String locale = input.where().locale();
        service.deleteMapPage(locale);

        return new MessageResponse("Deleted the mapPage in " + locale);
    }

    @SchemaMapping(typeName="MapImages", field="default")
    public MapImageView getDefault(MapImagesView mapImagesView) {
        //throw new RuntimeException("tthththththt + " + mapImagesView.defaultMap().url());
        return mapImagesView.defaultMap();
    }
}
