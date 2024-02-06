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

import com.ppublica.apps.kiosk.service.LocationsDataService;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.locations.LocationAdminView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.MapImage;

@GraphQlTest(controllers=LocationsDataController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class LocationsDataControllerTest {
    
    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private LocationsDataService service;

    LocationAdminView location1AdminView;
    LocationAdminView location2AdminView;
    Map<String,Object> input;

    @BeforeEach
    public void setup() {
        List<LocalizedField> location1NameFields = List.of(new LocalizedField("en", "fullName1"), new LocalizedField("es", "nombre1"));
        List<LocalizedField> location2NameFields = List.of(new LocalizedField("en", "fullName2"), new LocalizedField("es", "nombre2"));

        List<LocalizedField> location1LevelNameFields = List.of(new LocalizedField("en", "levelName1"), new LocalizedField("es", "nombreDeNivel1"));
        List<LocalizedField> location2LevelNameFields = List.of(new LocalizedField("en", "levelName2"), new LocalizedField("es", "nombreDeNivel2"));

        List<LocalizedView<MapImage>> map1 = List.of(new LocalizedView<MapImage>("en", new MapImage(1, 2, "url")), new LocalizedView<MapImage>("es", new MapImage(1, 2, "url_es")));
        List<LocalizedView<MapImage>> map2 = List.of(new LocalizedView<MapImage>("en", new MapImage(3, 4, "url2")), new LocalizedView<MapImage>("es", new MapImage(3, 4, "url2_es")));

        this.location1AdminView = new LocationAdminView("1", location1NameFields, 1, location1LevelNameFields, map1);
        this.location2AdminView = new LocationAdminView("2", location2NameFields, 2, location2LevelNameFields, map2);


        //input corresponding to location1AdminView
        Map<String,Object> localizedFieldName_EN = new HashMap<>();
        localizedFieldName_EN.put("locale", "en");
        localizedFieldName_EN.put("value", "fullName1");

        Map<String,Object> localizedFieldName_ES = new HashMap<>();
        localizedFieldName_ES.put("locale", "es");
        localizedFieldName_ES.put("value", "nombre1");

        Map<String,Object> localizedFieldLevelName_EN = new HashMap<>();
        localizedFieldLevelName_EN.put("locale", "en");
        localizedFieldLevelName_EN.put("value", "levelName1");

        Map<String,Object> localizedFieldLevelName_ES = new HashMap<>();
        localizedFieldLevelName_ES.put("locale", "es");
        localizedFieldLevelName_ES.put("value", "nombreDeNivel1");



        Map<String,Object> localizedMapImage_EN = new HashMap<>();
        localizedMapImage_EN.put("width", 1);
        localizedMapImage_EN.put("height", 2);
        localizedMapImage_EN.put("url", "url");

        Map<String,Object> localizedMapImageView_EN = new HashMap<>();
        localizedMapImageView_EN.put("locale", "en");
        localizedMapImageView_EN.put("value", localizedMapImage_EN);

        Map<String,Object> localizedMapImage_ES = new HashMap<>();
        localizedMapImage_ES.put("width", 1);
        localizedMapImage_ES.put("height", 2);
        localizedMapImage_ES.put("url", "url_es");

        Map<String,Object> localizedMapImageView_ES = new HashMap<>();
        localizedMapImageView_ES.put("locale", "es");
        localizedMapImageView_ES.put("value", localizedMapImage_ES);


        this.input = new HashMap<>();
        input.put("fullname", List.of(localizedFieldName_EN, localizedFieldName_ES));
        input.put("level_num", 1);
        input.put("level_name", List.of(localizedFieldLevelName_EN, localizedFieldLevelName_ES));
        input.put("map", List.of(localizedMapImageView_EN, localizedMapImageView_ES));
    }

    @Test
    public void GET_locationsGivenLocale_returns_locations() {

        // set up mock
        LocationView location1View = new LocationView("1", "fullName_1", 1, "levelName_1", new MapImage(1, 2, "url"));
        LocationView location2View = new LocationView("2", "fullName_2", 2, "levelName_2", new MapImage(3, 4, "url2"));
       
        when(service.getLocations(any(), any())).thenReturn(List.of(location1View, location2View));


        graphqlTester.documentName("locations")
            .variable("locale", "en")
            .variable("sort", "level_num:asc")
            .execute()
            .path("locations").entityList(LocationView.class).containsExactly(location1View, location2View);
    }

    @Test
    public void GET_locationsAdmin_returns_locations() {

        when(service.getLocationsAdmin(any())).thenReturn(List.of(location1AdminView, location2AdminView));


        graphqlTester.documentName("locationsAdmin")
            .variable("sort", "level_num:asc")
            .execute()
            .path("locationsAdmin").entityList(LocationAdminView.class).containsExactly(location1AdminView, location2AdminView);
    }

    @Test
    public void GET_locationAdmin_returns_location() {

        when(service.getLocationAdmin(any())).thenReturn(Optional.of(location1AdminView));

        graphqlTester.documentName("locationAdmin")
            .variable("locationId", 2L)
            .execute()
            .path("locationAdmin").entity(LocationAdminView.class).isEqualTo(location1AdminView);
    }

    @Test
    public void POST_location_returns_locationAdmin() {        


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.createLocation(any())).thenReturn(location1AdminView);

        graphqlTester.documentName("locationMutationPost")
            .variable("input", payload)
            .execute()
            .path("createLocation").entity(LocationAdminView.class).isEqualTo(location1AdminView);

    }

    @Test
    public void PUT_location_returns_locationAdmin() {        


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.updateLocation(any(), any())).thenReturn(location1AdminView);

        graphqlTester.documentName("locationMutationPut")
            .variable("input", payload)
            .variable("locationId", 1L)
            .execute()
            .path("updateLocation").entity(LocationAdminView.class).isEqualTo(location1AdminView);

    }
    

    @Test
    public void DELETE_location_returns_message() {

        // set up input
        Map<String,Object> locale = new HashMap<>();
        locale.put("id", 2L);

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", locale);

        graphqlTester.documentName("locationMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteLocation", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the location with id=2");
            });

    }

}
