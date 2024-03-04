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

import com.ppublica.apps.kiosk.service.FirstAidDataService;
import com.ppublica.apps.kiosk.service.LocationsDataService;
import com.ppublica.apps.kiosk.service.payloads.DeletePayload;
import com.ppublica.apps.kiosk.service.payloads.firstaid.FirstAidPayload;
import com.ppublica.apps.kiosk.service.util.LocationKey;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidAdminView;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Controller
public class FirstAidDataController {
    @Autowired
    private FirstAidDataService service;

    @Autowired
    private LocationsDataService locationsService;

    public FirstAidDataController(BatchLoaderRegistry registry) {
        registry.forTypePair(LocationKey.class, LocationView.class).registerMappedBatchLoader((locationKeys, batchLoaderEnvironment) -> {
            return Mono.fromSupplier(() -> locationsService.getBatchLocations(locationKeys));
        });
    }
    
    @QueryMapping
    public List<FirstAidView> firstAids(@Argument String locale, @Argument String sort, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", locale);
        List<FirstAidView> firstAidsView = service.getFirstAids(locale, sort);

        return firstAidsView;
    }

    @QueryMapping
    public List<FirstAidAdminView> firstAidsAdmin(@Argument String sort, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        List<FirstAidAdminView> firstAidsAdminView = service.getFirstAidsAdmin(sort);

        return firstAidsAdminView;
    }

    @QueryMapping
    public FirstAidAdminView firstAidAdmin(@Argument Long firstAidId, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        FirstAidAdminView firstAidAdminView = service.getFirstAidAdmin(firstAidId).get();

        return firstAidAdminView;
    }


    @MutationMapping
    public FirstAidAdminView createFirstAid(@Argument FirstAidPayload input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        FirstAidAdminView firstAidAdminView = service.createFirstAid(input.data());

        return firstAidAdminView;
    }

    @MutationMapping
    public FirstAidAdminView updateFirstAid(@Argument Long firstAidId, @Argument FirstAidPayload input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        FirstAidAdminView firstAidAdminView = service.updateFirstAid(firstAidId, input.data());

        return firstAidAdminView;
    }

    
    @MutationMapping
    public MessageResponse deleteFirstAid(@Argument DeletePayload input) {
        String id = input.where().id();
        service.deleteFirstAid(Long.parseLong(id));

        return new MessageResponse("Deleted the first aid amenity with id=" + id);
    }

    @SchemaMapping(typeName = "FirstAid")
    public CompletableFuture<LocationView> location(DataFetchingEnvironment env, FirstAidView firstAidView, DataLoader<LocationKey, LocationView> loader) {
        String locale = env.getGraphQlContext().get("locale");
        return loader.load(new LocationKey(firstAidView.locationId(), locale));

    }

    @SchemaMapping(typeName = "FirstAidAdmin", field = "location")
    public CompletableFuture<LocationView> locationAdmin(DataFetchingEnvironment env, FirstAidAdminView firstAidAdminView, DataLoader<LocationKey, LocationView> loader) {
        String locale = env.getGraphQlContext().get("locale");
        return loader.load(new LocationKey(firstAidAdminView.locationId(), locale));

    }
    
}
