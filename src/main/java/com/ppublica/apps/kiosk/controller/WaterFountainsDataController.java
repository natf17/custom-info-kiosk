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

import com.ppublica.apps.kiosk.service.LocationsDataService;
import com.ppublica.apps.kiosk.service.WaterFountainsDataService;
import com.ppublica.apps.kiosk.service.payloads.DeletePayload;
import com.ppublica.apps.kiosk.service.payloads.waterfountains.WaterFountainPayload;
import com.ppublica.apps.kiosk.service.util.LocationKey;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainAdminView;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainView;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Controller
public class WaterFountainsDataController {
    @Autowired
    private WaterFountainsDataService service;

    @Autowired
    private LocationsDataService locationsService;

    public WaterFountainsDataController(BatchLoaderRegistry registry) {
        registry.forTypePair(LocationKey.class, LocationView.class).registerMappedBatchLoader((locationKeys, batchLoaderEnvironment) -> {
            return Mono.fromSupplier(() -> locationsService.getBatchLocations(locationKeys));
        });
    }
    
    @QueryMapping
    public List<WaterFountainView> waterFountains(@Argument String locale, @Argument String sort, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", locale);
        List<WaterFountainView> waterFountainsView = service.getWaterFountains(locale, sort);

        return waterFountainsView;
    }

    @QueryMapping
    public List<WaterFountainAdminView> waterFountainsAdmin(@Argument String sort, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        List<WaterFountainAdminView> waterFountainsAdminView = service.getWaterFountainsAdmin(sort);

        return waterFountainsAdminView;
    }

    @QueryMapping
    public WaterFountainAdminView waterFountainAdmin(@Argument Long waterFountainId, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        WaterFountainAdminView waterFountainAdminView = service.getWaterFountainAdmin(waterFountainId).get();

        return waterFountainAdminView;
    }


    @MutationMapping
    public WaterFountainAdminView createWaterFountain(@Argument WaterFountainPayload input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        WaterFountainAdminView waterFountainAdminView = service.createWaterFountain(input.data());

        return waterFountainAdminView;
    }

    @MutationMapping
    public WaterFountainAdminView updateWaterFountain(@Argument Long waterFountainId, @Argument WaterFountainPayload input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        WaterFountainAdminView waterFountainAdminView = service.updateWaterFountain(waterFountainId, input.data());

        return waterFountainAdminView;
    }

    
    @MutationMapping
    public MessageResponse deleteWaterFountain(@Argument DeletePayload input) {
        String id = input.where().id();
        service.deleteWaterFountain(Long.parseLong(id));

        return new MessageResponse("Deleted the water fountain with id=" + id);
    }

    @SchemaMapping(typeName = "WaterFountain")
    public CompletableFuture<LocationView> location(DataFetchingEnvironment env, WaterFountainView firstAidView, DataLoader<LocationKey, LocationView> loader) {
        String locale = env.getGraphQlContext().get("locale");
        return loader.load(new LocationKey(firstAidView.locationId(), locale));

    }

    @SchemaMapping(typeName = "WaterFountainAdmin", field = "location")
    public CompletableFuture<LocationView> locationAdmin(DataFetchingEnvironment env, WaterFountainAdminView firstAidAdminView, DataLoader<LocationKey, LocationView> loader) {
        String locale = env.getGraphQlContext().get("locale");
        return loader.load(new LocationKey(firstAidAdminView.locationId(), locale));

    }
}
