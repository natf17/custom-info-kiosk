package com.ppublica.apps.kiosk.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.ppublica.apps.kiosk.service.DonationsDataService;
import com.ppublica.apps.kiosk.service.LocationsDataService;
import com.ppublica.apps.kiosk.service.collection.LocationKey;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.donations.DonationAdminView;
import com.ppublica.apps.kiosk.service.views.donations.DonationImage;
import com.ppublica.apps.kiosk.service.views.donations.DonationView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.MapImage;

@GraphQlTest(controllers=DonationsDataController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class DonationsDataControllerTest {
     @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private LocationsDataService locationsService;

    @MockBean
    private DonationsDataService service;

    LocationView location1ViewEn;
    LocationView location1ViewEs;
    LocationView location2ViewEn;
    LocationView location2ViewEs;
    Map<String,Object> input;

    Map<LocationKey, LocationView> mockBatchLocations;

    DonationAdminView donation1AdminView;
    DonationAdminView donation2AdminView;
    LocalizedView<DonationImage> donation1ImageEn;
    LocalizedView<DonationImage> donation1ImageEs;
    LocalizedView<DonationImage> donation2ImageEn;
    LocalizedView<DonationImage> donation2ImageEs;

    @BeforeEach
    public void setup() {

        // fields for input
        Map<String,Object> localizedFieldName_EN = new HashMap<>();
        localizedFieldName_EN.put("localeId", "1");
        localizedFieldName_EN.put("value", "donName_1");

        Map<String,Object> localizedFieldName_ES = new HashMap<>();
        localizedFieldName_ES.put("localeId", "2");
        localizedFieldName_ES.put("value", "donName_1_es");

        Map<String,Object> localizedFieldSVGElem_EN = new HashMap<>();
        localizedFieldSVGElem_EN.put("localeId", "1");
        localizedFieldSVGElem_EN.put("value", "svgElemId");

        Map<String,Object> localizedFieldSVGElem_ES = new HashMap<>();
        localizedFieldSVGElem_ES.put("localeId", "2");
        localizedFieldSVGElem_ES.put("value", "svgElemId_es");

        Map<String,Object> localizedFieldNote_EN = new HashMap<>();
        localizedFieldNote_EN.put("localeId", "1");
        localizedFieldNote_EN.put("value", "note");

        Map<String,Object> localizedFieldNote_ES = new HashMap<>();
        localizedFieldNote_ES.put("localeId", "2");
        localizedFieldNote_ES.put("value", "note_es");

        Map<String,Object> localizedDonImage_EN = new HashMap<>();
        localizedDonImage_EN.put("width", 1);
        localizedDonImage_EN.put("height", 2);
        localizedDonImage_EN.put("url", "url");

        Map<String,Object> localizedDonImageView_EN = new HashMap<>();
        localizedDonImageView_EN.put("localeId", "1");
        localizedDonImageView_EN.put("value", localizedDonImage_EN);

        Map<String,Object> localizedDonImage_ES = new HashMap<>();
        localizedDonImage_ES.put("width", 1);
        localizedDonImage_ES.put("height", 2);
        localizedDonImage_ES.put("url", "url_es");

        Map<String,Object> localizedDonImageView_ES = new HashMap<>();
        localizedDonImageView_ES.put("localeId", "2");
        localizedDonImageView_ES.put("value", localizedDonImage_ES);


        //input
        this.input = new HashMap<>();
        input.put("name", List.of(localizedFieldName_EN, localizedFieldName_ES));
        input.put("isWheelchairAccessible", true);
        input.put("svgElemId", List.of(localizedFieldSVGElem_EN, localizedFieldSVGElem_ES));
        input.put("note", List.of(localizedFieldNote_EN, localizedFieldNote_ES));
        input.put("locationId", "1");
        input.put("paymentTypesAccepted", "cash");
        input.put("featImg", List.of(localizedDonImageView_EN, localizedDonImageView_ES));


        // views
        
        location1ViewEn = new LocationView("1", "fullName_1", 1, "levelName_1", new MapImage(1, 2, "url"));
        location1ViewEs = new LocationView("1", "fullName_1_es", 1, "levelName_1_es", new MapImage(1, 2, "url_es"));
        location2ViewEn = new LocationView("2", "fullName_2", 2, "levelName_2", new MapImage(3, 4, "url2"));
        location2ViewEs = new LocationView("2", "fullName_2_es", 2, "levelName_2_es", new MapImage(3, 4, "url2_es"));

        mockBatchLocations = new HashMap<>();
        mockBatchLocations.put(new LocationKey(1L, "en"), location1ViewEn);
        mockBatchLocations.put(new LocationKey(1L, "es"), location1ViewEs);
        mockBatchLocations.put(new LocationKey(2L, "en"), location2ViewEn);
        mockBatchLocations.put(new LocationKey(2L, "es"), location2ViewEs);


        donation1ImageEn = new LocalizedView<>("en", new DonationImage("url", 1, 2));
        donation1ImageEs = new LocalizedView<>("es", new DonationImage("url_es", 1, 2));
        donation1AdminView = new DonationAdminView("1",
                                                    List.of(new LocalizedField("en", "donName_1"), new LocalizedField("es", "donName_1_es")), 
                                                    true, 
                                                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                                                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                                                    1L, 
                                                    "cash",
                                                    List.of(donation1ImageEn, donation1ImageEs));

        donation2ImageEn = new LocalizedView<>("en", new DonationImage("url2", 3, 4));
        donation2ImageEs = new LocalizedView<>("es", new DonationImage("url2_es", 3, 4));
        donation2AdminView = new DonationAdminView("2",
                                                    List.of(new LocalizedField("en", "donName_2"), new LocalizedField("es", "donName_2_es")), 
                                                    false,
                                                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                                                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                                                    2L, 
                                                    "credit",
                                                    List.of(donation2ImageEn, donation2ImageEs));
    }

    @Test
    public void GET_donationsGivenLocale_returns_donations() {

        // set up mock
        DonationView donation1View = new DonationView("1", "donName_1", true, "svgElemId", "note", 1L, "cash", new DonationImage("url", 1, 2));
        DonationView donation2View = new DonationView("2", "donName_2", false, "svgElemId2", "note2", 2L, "credit", new DonationImage("url2", 3, 4));
       
        when(service.getDonations("en", "location.level_num:asc")).thenReturn(List.of(donation1View, donation2View));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);


        graphqlTester.documentName("donations")
            .variable("locale", "en")
            .variable("sort", "location.level_num:asc")
            .execute()
            .path("donations").entityList(DonationViewResponse.class).containsExactly(
                new DonationViewResponse("1", "donName_1", true, "svgElemId", "note", 
                    new LocationResponse("fullName_1", "levelName_1", 1), "cash", new DonationImage("url", 1, 2)),
                new DonationViewResponse("2", "donName_2", false, "svgElemId2", "note2", 
                    new LocationResponse("fullName_2", "levelName_2", 2), "credit", new DonationImage("url2", 3, 4)));
    }

   
    @Test
    public void GET_donationsAdmin_returns_donations() {

        when(service.getDonationsAdmin(any())).thenReturn(List.of(donation1AdminView, donation2AdminView));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("donationsAdmin")
            .variable("sort", "location.level_num:asc")
            .execute()
            .path("donationsAdmin").entityList(DonationsAdminViewResponse.class).containsExactly(
                new DonationsAdminViewResponse("1",
                    List.of(new LocalizedField("en", "donName_1"), new LocalizedField("es", "donName_1_es")), 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1),
                    "cash",
                    List.of(donation1ImageEn, donation1ImageEs)),
                new DonationsAdminViewResponse("2",
                    List.of(new LocalizedField("en", "donName_2"), new LocalizedField("es", "donName_2_es")), 
                    false, 
                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                    new LocationResponse("fullName_2", "levelName_2", 2), 
                    "credit",
                    List.of(donation2ImageEn, donation2ImageEs)
                )
            );
    }
  
    @Test
    public void GET_donationAdmin_returns_donation() {

        when(service.getDonationAdmin(2L)).thenReturn(Optional.of(donation2AdminView));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("donationAdmin")
            .variable("donationId", 2L)
            .execute()
            .path("donationAdmin").entity(DonationsAdminViewResponse.class).isEqualTo(
                new DonationsAdminViewResponse("2",
                    List.of(new LocalizedField("en", "donName_2"), new LocalizedField("es", "donName_2_es")), 
                    false, 
                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                    new LocationResponse("fullName_2", "levelName_2", 2), 
                    "credit",
                    List.of(donation2ImageEn, donation2ImageEs)
                )
            );
    }

    @Test
    public void POST_donation_returns_donationAdmin() {        


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.createDonation(any())).thenReturn(donation1AdminView);
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("donationMutationPost")
            .variable("input", payload)
            .execute()
            .path("createDonation")
            .entity(DonationsAdminViewResponse.class)
            .isEqualTo(
                new DonationsAdminViewResponse("1",
                    List.of(new LocalizedField("en", "donName_1"), new LocalizedField("es", "donName_1_es")), 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1),
                    "cash",
                    List.of(donation1ImageEn, donation1ImageEs))
            );

    }

    @Test
    public void PUT_donation_returns_donationAdmin() {        


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.updateDonation(any(), any())).thenReturn(donation1AdminView);
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("donationMutationPut")
            .variable("input", payload)
            .variable("donationId", 1L)
            .execute()
            .path("updateDonation")
            .entity(DonationsAdminViewResponse.class)
            .isEqualTo(
                new DonationsAdminViewResponse("1",
                    List.of(new LocalizedField("en", "donName_1"), new LocalizedField("es", "donName_1_es")), 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1),
                    "cash",
                    List.of(donation1ImageEn, donation1ImageEs))
            );

    }
    
    @Test
    public void DELETE_donation_returns_message() {

        // set up input
        Map<String,Object> locale = new HashMap<>();
        locale.put("id", 2L);

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", locale);

        graphqlTester.documentName("donationMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteDonation", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the donation amenity with id=2");
            });

    }

    record DonationViewResponse(String id, String name, Boolean isWheelchairAccessible, String svgElemId, String note, LocationResponse location, String paymentTypesAccepted, DonationImage featImg) {}
    
    record LocationResponse(String fullname, String level_name, Integer level_num) {}

    record DonationsAdminViewResponse(String id, List<LocalizedField> name, Boolean isWheelchairAccessible, List<LocalizedField> svgElemId, List<LocalizedField> note, LocationResponse location, String paymentTypesAccepted, List<LocalizedView<DonationImage>> featImg) {}

}
