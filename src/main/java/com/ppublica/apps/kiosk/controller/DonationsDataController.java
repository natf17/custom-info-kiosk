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

import com.ppublica.apps.kiosk.service.DonationsDataService;
import com.ppublica.apps.kiosk.service.LocationsDataService;
import com.ppublica.apps.kiosk.service.payloads.DeletePayload;
import com.ppublica.apps.kiosk.service.payloads.donations.DonationPayload;
import com.ppublica.apps.kiosk.service.util.LocationKey;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.donations.DonationAdminView;
import com.ppublica.apps.kiosk.service.views.donations.DonationView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Controller
public class DonationsDataController {
    @Autowired
    private DonationsDataService service;

    @Autowired
    private LocationsDataService locationsService;

    public DonationsDataController(BatchLoaderRegistry registry) {
        registry.forTypePair(LocationKey.class, LocationView.class).registerMappedBatchLoader((locationKeys, batchLoaderEnvironment) -> {
            return Mono.fromSupplier(() -> locationsService.getBatchLocations(locationKeys));
        });
    }
    
    @QueryMapping
    public List<DonationView> donations(@Argument String locale, @Argument String sort, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", locale);
        List<DonationView> donationsView = service.getDonations(locale, sort);

        return donationsView;
    }

    @QueryMapping
    public List<DonationAdminView> donationsAdmin(@Argument String sort, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        List<DonationAdminView> donationsAdminView = service.getDonationsAdmin(sort);

        return donationsAdminView;
    }

    @QueryMapping
    public DonationAdminView donationAdmin(@Argument Long donationId, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        DonationAdminView donationsAdminView = service.getDonationAdmin(donationId).get();

        return donationsAdminView;
    }


    @MutationMapping
    public DonationAdminView createDonation(@Argument DonationPayload input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        DonationAdminView donationsAdminView = service.createDonation(input.data());

        return donationsAdminView;
    }

    @MutationMapping
    public DonationAdminView updateDonation(@Argument Long donationId, @Argument DonationPayload input, DataFetchingEnvironment env) {
        env.getGraphQlContext().put("locale", "en");
        DonationAdminView donationsAdminView = service.updateDonation(donationId, input.data());

        return donationsAdminView;
    }

    
    @MutationMapping
    public MessageResponse deleteDonation(@Argument DeletePayload input) {
        String id = input.where().id();
        service.deleteDonation(Long.parseLong(id));

        return new MessageResponse("Deleted the donation amenity with id=" + id);
    }

    @SchemaMapping(typeName = "Donation")
    public CompletableFuture<LocationView> location(DataFetchingEnvironment env, DonationView donationView, DataLoader<LocationKey, LocationView> loader) {
        String locale = env.getGraphQlContext().get("locale");
        return loader.load(new LocationKey(donationView.locationId(), locale));

    }

    @SchemaMapping(typeName = "DonationAdmin", field = "location")
    public CompletableFuture<LocationView> locationAdmin(DataFetchingEnvironment env, DonationAdminView donationAdminView, DataLoader<LocationKey, LocationView> loader) {
        String locale = env.getGraphQlContext().get("locale");
        return loader.load(new LocationKey(donationAdminView.locationId(), locale));

    }

}
