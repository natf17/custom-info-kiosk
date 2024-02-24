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

import com.ppublica.apps.kiosk.service.BathroomsDataService;
import com.ppublica.apps.kiosk.service.LocationsDataService;
import com.ppublica.apps.kiosk.service.collection.LocationKey;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomImage;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.MapImage;

@GraphQlTest(controllers=BathroomsDataController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class BathroomsDataControllerTest {
    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private LocationsDataService locationsService;

    @MockBean
    private BathroomsDataService service;

    LocationView location1ViewEn;
    LocationView location1ViewEs;
    LocationView location2ViewEn;
    LocationView location2ViewEs;
    Map<String,Object> input;

    Map<LocationKey, LocationView> mockBatchLocations;

    BathroomAdminView bathroom1AdminView;
    BathroomAdminView bathroom2AdminView;
    LocalizedView<BathroomImage> bathroom1ImageEn;
    LocalizedView<BathroomImage> bathroom1ImageEs;
    LocalizedView<BathroomImage> bathroom2ImageEn;
    LocalizedView<BathroomImage> bathroom2ImageEs;

    @BeforeEach
    public void setup() {

        // fields for input
        Map<String,Object> localizedFieldName_EN = new HashMap<>();
        localizedFieldName_EN.put("localeId", "1");
        localizedFieldName_EN.put("value", "brName_1");

        Map<String,Object> localizedFieldName_ES = new HashMap<>();
        localizedFieldName_ES.put("localeId", "2");
        localizedFieldName_ES.put("value", "brName_1_es");

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

        Map<String,Object> localizedBrImage_EN = new HashMap<>();
        localizedBrImage_EN.put("width", 1);
        localizedBrImage_EN.put("height", 2);
        localizedBrImage_EN.put("url", "url");

        Map<String,Object> localizedBrImageView_EN = new HashMap<>();
        localizedBrImageView_EN.put("localeId", "1");
        localizedBrImageView_EN.put("value", localizedBrImage_EN);

        Map<String,Object> localizedBrImage_ES = new HashMap<>();
        localizedBrImage_ES.put("width", 1);
        localizedBrImage_ES.put("height", 2);
        localizedBrImage_ES.put("url", "url_es");

        Map<String,Object> localizedBrImageView_ES = new HashMap<>();
        localizedBrImageView_ES.put("localeId", "2");
        localizedBrImageView_ES.put("value", localizedBrImage_ES);


        //input
        this.input = new HashMap<>();
        input.put("name", List.of(localizedFieldName_EN, localizedFieldName_ES));
        input.put("gender", "uni");
        input.put("isWheelchairAccessible", true);
        input.put("svgElemId", List.of(localizedFieldSVGElem_EN, localizedFieldSVGElem_ES));
        input.put("note", List.of(localizedFieldNote_EN, localizedFieldNote_ES));
        input.put("locationId", "1");
        input.put("featImg", List.of(localizedBrImageView_EN, localizedBrImageView_ES));


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


        bathroom1ImageEn = new LocalizedView<>("en", new BathroomImage("url", 1, 2));
        bathroom1ImageEs = new LocalizedView<>("es", new BathroomImage("url_es", 1, 2));
        bathroom1AdminView = new BathroomAdminView("1",
                                                    List.of(new LocalizedField("en", "brName_1"), new LocalizedField("es", "brName_1_es")), 
                                                    "uni", 
                                                    true, 
                                                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                                                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                                                    1L, 
                                                    List.of(bathroom1ImageEn, bathroom1ImageEs));

        bathroom2ImageEn = new LocalizedView<>("en", new BathroomImage("url2", 3, 4));
        bathroom2ImageEs = new LocalizedView<>("es", new BathroomImage("url2_es", 3, 4));
        bathroom2AdminView = new BathroomAdminView("2",
                                                    List.of(new LocalizedField("en", "brName_2"), new LocalizedField("es", "brName_2_es")), 
                                                    "mens", 
                                                    false, 
                                                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                                                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                                                    2L, 
                                                    List.of(bathroom2ImageEn, bathroom2ImageEs));
    }

    @Test
    public void GET_bathroomsGivenLocale_returns_bathrooms() {

        // set up mock
        BathroomView bathroom1View = new BathroomView("1", "brName_1", "uni", true, "svgElemId", "note", 1L, new BathroomImage("url", 1, 2));
        BathroomView bathroom2View = new BathroomView("2", "brName_2", "mens", false, "svgElemId2", "note2", 2L, new BathroomImage("url2", 3, 4));
       
        when(service.getBathrooms("en", "location.level_num:asc")).thenReturn(List.of(bathroom1View, bathroom2View));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);


        graphqlTester.documentName("bathrooms")
            .variable("locale", "en")
            .variable("sort", "location.level_num:asc")
            .execute()
            .path("bathrooms").entityList(BathroomViewReponse.class).containsExactly(
                new BathroomViewReponse("1", "brName_1", "uni", true, "svgElemId", "note", 
                    new LocationResponse("fullName_1", "levelName_1", 1), new BathroomImage("url", 1, 2)),
                new BathroomViewReponse("2", "brName_2", "mens", false, "svgElemId2", "note2", 
                    new LocationResponse("fullName_2", "levelName_2", 2), new BathroomImage("url2", 3, 4)));
    }

    
    @Test
    public void GET_bathroomAdmin_returns_bathrooms() {

        when(service.getBathroomsAdmin(any())).thenReturn(List.of(bathroom1AdminView, bathroom2AdminView));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("bathroomsAdmin")
            .variable("sort", "location.level_num:asc")
            .execute()
            .path("bathroomsAdmin").entityList(BathroomAdminViewReponse.class).containsExactly(
                new BathroomAdminViewReponse("1",
                    List.of(new LocalizedField("en", "brName_1"), new LocalizedField("es", "brName_1_es")), 
                    "uni", 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1), 
                    List.of(bathroom1ImageEn, bathroom1ImageEs)),
                new BathroomAdminViewReponse("2",
                    List.of(new LocalizedField("en", "brName_2"), new LocalizedField("es", "brName_2_es")), 
                    "mens", 
                    false, 
                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                    new LocationResponse("fullName_2", "levelName_2", 2), 
                    List.of(bathroom2ImageEn, bathroom2ImageEs)
                )
            );
    }

  
    @Test
    public void GET_bathroomAdmin_returns_bathroom() {

        when(service.getBathroomAdmin(2L)).thenReturn(Optional.of(bathroom2AdminView));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("bathroomAdmin")
            .variable("bathroomId", 2L)
            .execute()
            .path("bathroomAdmin")
            .entity(BathroomAdminViewReponse.class)
            .isEqualTo(new BathroomAdminViewReponse("2",
                List.of(new LocalizedField("en", "brName_2"), new LocalizedField("es", "brName_2_es")), 
                "mens", 
                false, 
                List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                new LocationResponse("fullName_2", "levelName_2", 2), 
                List.of(bathroom2ImageEn, bathroom2ImageEs))
            );
    }

    @Test
    public void POST_bathroom_returns_bathroomAdmin() {        


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.createBathroom(any())).thenReturn(bathroom1AdminView);
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("bathroomMutationPost")
            .variable("input", payload)
            .execute()
            .path("createBathroom")
            .entity(BathroomAdminViewReponse.class)
            .isEqualTo(
                new BathroomAdminViewReponse("1",
                    List.of(new LocalizedField("en", "brName_1"), new LocalizedField("es", "brName_1_es")), 
                    "uni", 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1), 
                    List.of(bathroom1ImageEn, bathroom1ImageEs))
            );

    }

    @Test
    public void PUT_bathroom_returns_bathroomAdmin() {        


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.updateBathroom(any(), any())).thenReturn(bathroom1AdminView);
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("bathroomMutationPut")
            .variable("input", payload)
            .variable("bathroomId", 1L)
            .execute()
            .path("updateBathroom")
            .entity(BathroomAdminViewReponse.class)
            .isEqualTo(
                new BathroomAdminViewReponse("1",
                    List.of(new LocalizedField("en", "brName_1"), new LocalizedField("es", "brName_1_es")), 
                    "uni", 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1), 
                    List.of(bathroom1ImageEn, bathroom1ImageEs))
            );

    }
    

    @Test
    public void DELETE_bathroom_returns_message() {

        // set up input
        Map<String,Object> locale = new HashMap<>();
        locale.put("id", 2L);

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", locale);

        graphqlTester.documentName("bathroomMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteBathroom", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the bathroom with id=2");
            });

    }

    record BathroomViewReponse(String id, String name, String gender, Boolean isWheelchairAccessible, String svgElemId, String note, LocationResponse location, BathroomImage featImg) {}
    
    record LocationResponse(String fullname, String level_name, Integer level_num) {}

    record BathroomAdminViewReponse(String id, List<LocalizedField> name, String gender, Boolean isWheelchairAccessible, List<LocalizedField> svgElemId, List<LocalizedField> note, LocationResponse location, List<LocalizedView<BathroomImage>> featImg) {}

}
