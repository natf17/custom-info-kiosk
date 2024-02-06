package com.ppublica.apps.kiosk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.LocationsDataService;
import com.ppublica.apps.kiosk.service.payloads.DeletePayload;
import com.ppublica.apps.kiosk.service.payloads.locations.LocationPayload;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.LocationAdminView;

@Controller
public class LocationsDataController {
    
    @Autowired
    private LocationsDataService service;
    
    @QueryMapping
    public List<LocationView> locations(@Argument String locale, @Argument String sort) {

        List<LocationView> locationsView = service.getLocations(locale, sort);

        return locationsView;
    }

    @QueryMapping
    public List<LocationAdminView> locationsAdmin(@Argument String sort) {

        List<LocationAdminView> locationsAdminView = service.getLocationsAdmin(sort);

        return locationsAdminView;
    }

    @QueryMapping
    public LocationAdminView locationAdmin(@Argument Long locationId) {

        LocationAdminView locationsAdminView = service.getLocationAdmin(locationId).get();

        return locationsAdminView;
    }


    @MutationMapping
    public LocationAdminView createLocation(@Argument LocationPayload input) {
        LocationAdminView locationAdminView = service.createLocation(input.data());

        return locationAdminView;
    }

    @MutationMapping
    public LocationAdminView updateLocation(@Argument Long locationId, @Argument LocationPayload input) {
        LocationAdminView locationAdminView = service.updateLocation(locationId, input.data());

        return locationAdminView;
    }

    
    @MutationMapping
    public MessageResponse deleteLocation(@Argument DeletePayload input) {
        String id = input.where().id();
        service.deleteLocation(Long.parseLong(id));

        return new MessageResponse("Deleted the location with id=" + id);
    }

}
