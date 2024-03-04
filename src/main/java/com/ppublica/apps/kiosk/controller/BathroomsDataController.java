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

import com.ppublica.apps.kiosk.service.BathroomsDataService;
import com.ppublica.apps.kiosk.service.LocationsDataService;
import com.ppublica.apps.kiosk.service.payloads.DeletePayload;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomPayload;
import com.ppublica.apps.kiosk.service.util.LocationKey;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Controller
public class BathroomsDataController {
    
    @Autowired
    private BathroomsDataService service;

    @Autowired
    private LocationsDataService locationsService;

    public BathroomsDataController(BatchLoaderRegistry registry) {
        registry.forTypePair(LocationKey.class, LocationView.class).registerMappedBatchLoader((locationKeys, batchLoaderEnvironment) -> {
            return Mono.fromSupplier(() -> locationsService.getBatchLocations(locationKeys));
        });
    }
    
    @QueryMapping
    public List<BathroomView> bathrooms(@Argument String locale, @Argument String sort, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", locale);
        List<BathroomView> bathroomsView = service.getBathrooms(locale, sort);

        return bathroomsView;
    }

    @QueryMapping
    public List<BathroomAdminView> bathroomsAdmin(@Argument String sort, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        List<BathroomAdminView> bathroomsAdminView = service.getBathroomsAdmin(sort);

        return bathroomsAdminView;
    }

    @QueryMapping
    public BathroomAdminView bathroomAdmin(@Argument Long bathroomId, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        BathroomAdminView bathroomAdminView = service.getBathroomAdmin(bathroomId).get();

        return bathroomAdminView;
    }


    @MutationMapping
    public BathroomAdminView createBathroom(@Argument BathroomPayload input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        BathroomAdminView bathroomAdminView = service.createBathroom(input.data());

        return bathroomAdminView;
    }

    @MutationMapping
    public BathroomAdminView updateBathroom(@Argument Long bathroomId, @Argument BathroomPayload input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        BathroomAdminView bathroomAdminView = service.updateBathroom(bathroomId, input.data());

        return bathroomAdminView;
    }

    
    @MutationMapping
    public MessageResponse deleteBathroom(@Argument DeletePayload input) {
        String id = input.where().id();
        service.deleteBathroom(Long.parseLong(id));

        return new MessageResponse("Deleted the bathroom with id=" + id);
    }

    @SchemaMapping(typeName = "Bathroom")
    public CompletableFuture<LocationView> location(DataFetchingEnvironment env, BathroomView bathroomView, DataLoader<LocationKey, LocationView> loader) {
        String locale = env.getGraphQlContext().get("locale");
        return loader.load(new LocationKey(bathroomView.locationId(), locale));

    }

    @SchemaMapping(typeName = "BathroomAdmin", field = "location")
    public CompletableFuture<LocationView> locationAdmin(DataFetchingEnvironment env, BathroomAdminView bathroomAdminView, DataLoader<LocationKey, LocationView> loader) {
        String locale = env.getGraphQlContext().get("locale");
        return loader.load(new LocationKey(bathroomAdminView.locationId(), locale));

    }

}
