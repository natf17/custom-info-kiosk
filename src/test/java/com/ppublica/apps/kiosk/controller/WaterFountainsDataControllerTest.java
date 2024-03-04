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
import com.ppublica.apps.kiosk.service.WaterFountainsDataService;
import com.ppublica.apps.kiosk.service.util.LocationKey;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.MapImage;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainAdminView;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainImage;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainView;

@GraphQlTest(controllers=WaterFountainsDataController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class WaterFountainsDataControllerTest {

    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private LocationsDataService locationsService;

    @MockBean
    private WaterFountainsDataService service;

    LocationView location1ViewEn;
    LocationView location1ViewEs;
    LocationView location2ViewEn;
    LocationView location2ViewEs;
    Map<String,Object> input;

    Map<LocationKey, LocationView> mockBatchLocations;

    WaterFountainAdminView waterFountain1AdminView;
    WaterFountainAdminView waterFountain2AdminView;
    LocalizedView<WaterFountainImage> waterFountain1ImageEn;
    LocalizedView<WaterFountainImage> waterFountain1ImageEs;
    LocalizedView<WaterFountainImage> waterFountain2ImageEn;
    LocalizedView<WaterFountainImage> waterFountain2ImageEs;

    @BeforeEach
    public void setup() {

        // fields for input
        Map<String,Object> localizedFieldName_EN = new HashMap<>();
        localizedFieldName_EN.put("localeId", "1");
        localizedFieldName_EN.put("value", "wfName_1");

        Map<String,Object> localizedFieldName_ES = new HashMap<>();
        localizedFieldName_ES.put("localeId", "2");
        localizedFieldName_ES.put("value", "wfName_1_es");

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

        Map<String,Object> localizedWfImage_EN = new HashMap<>();
        localizedWfImage_EN.put("width", 1);
        localizedWfImage_EN.put("height", 2);
        localizedWfImage_EN.put("url", "url");

        Map<String,Object> localizedWfImageView_EN = new HashMap<>();
        localizedWfImageView_EN.put("localeId", "1");
        localizedWfImageView_EN.put("value", localizedWfImage_EN);

        Map<String,Object> localizedWfImage_ES = new HashMap<>();
        localizedWfImage_ES.put("width", 1);
        localizedWfImage_ES.put("height", 2);
        localizedWfImage_ES.put("url", "url_es");

        Map<String,Object> localizedWfImageView_ES = new HashMap<>();
        localizedWfImageView_ES.put("localeId", "2");
        localizedWfImageView_ES.put("value", localizedWfImage_ES);


        //input
        this.input = new HashMap<>();
        input.put("name", List.of(localizedFieldName_EN, localizedFieldName_ES));
        input.put("isWheelchairAccessible", true);
        input.put("svgElemId", List.of(localizedFieldSVGElem_EN, localizedFieldSVGElem_ES));
        input.put("note", List.of(localizedFieldNote_EN, localizedFieldNote_ES));
        input.put("locationId", "1");
        input.put("featImg", List.of(localizedWfImageView_EN, localizedWfImageView_ES));


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


        waterFountain1ImageEn  = new LocalizedView<>("en", new WaterFountainImage("url", 1, 2));
        waterFountain1ImageEs = new LocalizedView<>("es", new WaterFountainImage("url_es", 1, 2));
        waterFountain1AdminView = new WaterFountainAdminView("1",
                                                    List.of(new LocalizedField("en", "wfName_1"), new LocalizedField("es", "wfName_1_es")), 
                                                    true, 
                                                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                                                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                                                    1L, 
                                                    List.of(waterFountain1ImageEn, waterFountain1ImageEs));

        waterFountain2ImageEn = new LocalizedView<>("en", new WaterFountainImage("url2", 3, 4));
        waterFountain2ImageEs = new LocalizedView<>("es", new WaterFountainImage("url2_es", 3, 4));
        waterFountain2AdminView = new WaterFountainAdminView("2",
                                                    List.of(new LocalizedField("en", "wfName_2"), new LocalizedField("es", "wfName_2_es")), 
                                                    false,
                                                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                                                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                                                    2L, 
                                                    List.of(waterFountain2ImageEn, waterFountain2ImageEs));
    }

    @Test
    public void GET_waterFountainsGivenLocale_returns_waterFountains() {

        // set up mock
        WaterFountainView waterFountain1View = new WaterFountainView("1", "wfName_1", true, "svgElemId", "note", 1L, new WaterFountainImage("url", 1, 2));
        WaterFountainView waterFountain2View = new WaterFountainView("2", "wfName_2", false, "svgElemId2", "note2", 2L, new WaterFountainImage("url2", 3, 4));
       
        when(service.getWaterFountains("en", "location.level_num:asc")).thenReturn(List.of(waterFountain1View, waterFountain2View));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);


        graphqlTester.documentName("waterFountains")
            .variable("locale", "en")
            .variable("sort", "location.level_num:asc")
            .execute()
            .path("waterFountains").entityList(WaterFountainViewResponse.class).containsExactly(
                new WaterFountainViewResponse("1", "wfName_1", true, "svgElemId", "note", 
                    new LocationResponse("fullName_1", "levelName_1", 1), new WaterFountainImage("url", 1, 2)),
                new WaterFountainViewResponse("2", "wfName_2", false, "svgElemId2", "note2", 
                    new LocationResponse("fullName_2", "levelName_2", 2), new WaterFountainImage("url2", 3, 4)));
    }

   
    @Test
    public void GET_waterFountainsAdmin_returns_waterFountains() {

        when(service.getWaterFountainsAdmin(any())).thenReturn(List.of(waterFountain1AdminView, waterFountain2AdminView));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("waterFountainsAdmin")
            .variable("sort", "location.level_num:asc")
            .execute()
            .path("waterFountainsAdmin").entityList(WaterFountainAdminViewResponse.class).containsExactly(
                new WaterFountainAdminViewResponse("1",
                    List.of(new LocalizedField("en", "wfName_1"), new LocalizedField("es", "wfName_1_es")), 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1),
                    List.of(waterFountain1ImageEn, waterFountain1ImageEs)),
                new WaterFountainAdminViewResponse("2",
                    List.of(new LocalizedField("en", "wfName_2"), new LocalizedField("es", "wfName_2_es")), 
                    false, 
                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                    new LocationResponse("fullName_2", "levelName_2", 2), 
                    List.of(waterFountain2ImageEn, waterFountain2ImageEs)
                )
            );
    }

    @Test
    public void GET_waterFountainAdmin_returns_waterFountain() {

        when(service.getWaterFountainAdmin(2L)).thenReturn(Optional.of(waterFountain2AdminView));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("waterFountainAdmin")
            .variable("waterFountainId", 2L)
            .execute()
            .path("waterFountainAdmin").entity(WaterFountainAdminViewResponse.class).isEqualTo(
                new WaterFountainAdminViewResponse("2",
                    List.of(new LocalizedField("en", "wfName_2"), new LocalizedField("es", "wfName_2_es")), 
                    false, 
                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                    new LocationResponse("fullName_2", "levelName_2", 2), 
                    List.of(waterFountain2ImageEn, waterFountain2ImageEs)
                )
            );
    }


    @Test
    public void POST_waterFountain_returns_waterFountainAdmin() {        


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.createWaterFountain(any())).thenReturn(waterFountain1AdminView);
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("waterFountainMutationPost")
            .variable("input", payload)
            .execute()
            .path("createWaterFountain")
            .entity(WaterFountainAdminViewResponse.class)
            .isEqualTo(
                new WaterFountainAdminViewResponse("1",
                    List.of(new LocalizedField("en", "wfName_1"), new LocalizedField("es", "wfName_1_es")), 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1),
                    List.of(waterFountain1ImageEn, waterFountain1ImageEs))
            );

    }


    @Test
    public void PUT_waterFountain_returns_waterFountainAdmin() {        

        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.updateWaterFountain(any(), any())).thenReturn(waterFountain1AdminView);
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("waterFountainMutationPut")
            .variable("input", payload)
            .variable("waterFountainId", 1L)
            .execute()
            .path("updateWaterFountain")
            .entity(WaterFountainAdminViewResponse.class)
            .isEqualTo(
                new WaterFountainAdminViewResponse("1",
                    List.of(new LocalizedField("en", "wfName_1"), new LocalizedField("es", "wfName_1_es")), 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1),
                    List.of(waterFountain1ImageEn, waterFountain1ImageEs))
            );

    }
    
    @Test
    public void DELETE_firstAid_returns_message() {

        // set up input
        Map<String,Object> locale = new HashMap<>();
        locale.put("id", 2L);

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", locale);

        graphqlTester.documentName("waterFountainMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteWaterFountain", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the water fountain with id=2");
            });

    }

    record WaterFountainViewResponse(String id, String name, Boolean isWheelchairAccessible, String svgElemId, String note, LocationResponse location, WaterFountainImage featImg) {}
    
    record LocationResponse(String fullname, String level_name, Integer level_num) {}

    record WaterFountainAdminViewResponse(String id, List<LocalizedField> name, Boolean isWheelchairAccessible, List<LocalizedField> svgElemId, List<LocalizedField> note, LocationResponse location, List<LocalizedView<WaterFountainImage>> featImg) {}

    
    
}
