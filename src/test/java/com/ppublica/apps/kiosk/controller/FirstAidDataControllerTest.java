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

import com.ppublica.apps.kiosk.service.FirstAidDataService;
import com.ppublica.apps.kiosk.service.LocationsDataService;
import com.ppublica.apps.kiosk.service.collection.LocationKey;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidAdminView;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidImage;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.MapImage;

@GraphQlTest(controllers=FirstAidDataController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class FirstAidDataControllerTest {

    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private LocationsDataService locationsService;

    @MockBean
    private FirstAidDataService service;

    LocationView location1ViewEn;
    LocationView location1ViewEs;
    LocationView location2ViewEn;
    LocationView location2ViewEs;
    Map<String,Object> input;

    Map<LocationKey, LocationView> mockBatchLocations;

    FirstAidAdminView firstAid1AdminView;
    FirstAidAdminView firstAid2AdminView;
    LocalizedView<FirstAidImage> firstAid1ImageEn;
    LocalizedView<FirstAidImage> firstAid1ImageEs;
    LocalizedView<FirstAidImage> firstAid2ImageEn;
    LocalizedView<FirstAidImage> firstAid2ImageEs;

    @BeforeEach
    public void setup() {

        // fields for input
        Map<String,Object> localizedFieldName_EN = new HashMap<>();
        localizedFieldName_EN.put("localeId", "1");
        localizedFieldName_EN.put("value", "faName_1");

        Map<String,Object> localizedFieldName_ES = new HashMap<>();
        localizedFieldName_ES.put("localeId", "2");
        localizedFieldName_ES.put("value", "faName_1_es");

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

        Map<String,Object> localizedFaImage_EN = new HashMap<>();
        localizedFaImage_EN.put("width", 1);
        localizedFaImage_EN.put("height", 2);
        localizedFaImage_EN.put("url", "url");

        Map<String,Object> localizedFaImageView_EN = new HashMap<>();
        localizedFaImageView_EN.put("localeId", "1");
        localizedFaImageView_EN.put("value", localizedFaImage_EN);

        Map<String,Object> localizedFaImage_ES = new HashMap<>();
        localizedFaImage_ES.put("width", 1);
        localizedFaImage_ES.put("height", 2);
        localizedFaImage_ES.put("url", "url_es");

        Map<String,Object> localizedFaImageView_ES = new HashMap<>();
        localizedFaImageView_ES.put("localeId", "2");
        localizedFaImageView_ES.put("value", localizedFaImage_ES);


        //input
        this.input = new HashMap<>();
        input.put("name", List.of(localizedFieldName_EN, localizedFieldName_ES));
        input.put("isWheelchairAccessible", true);
        input.put("svgElemId", List.of(localizedFieldSVGElem_EN, localizedFieldSVGElem_ES));
        input.put("note", List.of(localizedFieldNote_EN, localizedFieldNote_ES));
        input.put("locationId", "1");
        input.put("featImg", List.of(localizedFaImageView_EN, localizedFaImageView_ES));


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


        firstAid1ImageEn  = new LocalizedView<>("en", new FirstAidImage("url", 1, 2));
        firstAid1ImageEs = new LocalizedView<>("es", new FirstAidImage("url_es", 1, 2));
        firstAid1AdminView = new FirstAidAdminView("1",
                                                    List.of(new LocalizedField("en", "faName_1"), new LocalizedField("es", "faName_1_es")), 
                                                    true, 
                                                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                                                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                                                    1L, 
                                                    List.of(firstAid1ImageEn, firstAid1ImageEs));

        firstAid2ImageEn = new LocalizedView<>("en", new FirstAidImage("url2", 3, 4));
        firstAid2ImageEs = new LocalizedView<>("es", new FirstAidImage("url2_es", 3, 4));
        firstAid2AdminView = new FirstAidAdminView("2",
                                                    List.of(new LocalizedField("en", "faName_2"), new LocalizedField("es", "faName_2_es")), 
                                                    false,
                                                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                                                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                                                    2L, 
                                                    List.of(firstAid2ImageEn, firstAid2ImageEs));
    }

    @Test
    public void GET_firstAidsGivenLocale_returns_firstAids() {

        // set up mock
        FirstAidView firstAid1View = new FirstAidView("1", "faName_1", true, "svgElemId", "note", 1L, new FirstAidImage("url", 1, 2));
        FirstAidView firstAid2View = new FirstAidView("2", "faName_2", false, "svgElemId2", "note2", 2L, new FirstAidImage("url2", 3, 4));
       
        when(service.getFirstAids("en", "location.level_num:asc")).thenReturn(List.of(firstAid1View, firstAid2View));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);


        graphqlTester.documentName("firstAids")
            .variable("locale", "en")
            .variable("sort", "location.level_num:asc")
            .execute()
            .path("firstAids").entityList(FirstAidViewResponse.class).containsExactly(
                new FirstAidViewResponse("1", "faName_1", true, "svgElemId", "note", 
                    new LocationResponse("fullName_1", "levelName_1", 1), new FirstAidImage("url", 1, 2)),
                new FirstAidViewResponse("2", "faName_2", false, "svgElemId2", "note2", 
                    new LocationResponse("fullName_2", "levelName_2", 2), new FirstAidImage("url2", 3, 4)));
    }

   
    @Test
    public void GET_firstAidsAdmin_returns_firstAids() {

        when(service.getFirstAidsAdmin(any())).thenReturn(List.of(firstAid1AdminView, firstAid2AdminView));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("firstAidsAdmin")
            .variable("sort", "location.level_num:asc")
            .execute()
            .path("firstAidsAdmin").entityList(FirstAidAdminViewResponse.class).containsExactly(
                new FirstAidAdminViewResponse("1",
                    List.of(new LocalizedField("en", "faName_1"), new LocalizedField("es", "faName_1_es")), 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1),
                    List.of(firstAid1ImageEn, firstAid1ImageEs)),
                new FirstAidAdminViewResponse("2",
                    List.of(new LocalizedField("en", "faName_2"), new LocalizedField("es", "faName_2_es")), 
                    false, 
                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                    new LocationResponse("fullName_2", "levelName_2", 2), 
                    List.of(firstAid2ImageEn, firstAid2ImageEs)
                )
            );
    }

    @Test
    public void GET_firstAidAdmin_returns_firstAid() {

        when(service.getFirstAidAdmin(2L)).thenReturn(Optional.of(firstAid2AdminView));
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("firstAidAdmin")
            .variable("firstAidId", 2L)
            .execute()
            .path("firstAidAdmin").entity(FirstAidAdminViewResponse.class).isEqualTo(
                new FirstAidAdminViewResponse("2",
                    List.of(new LocalizedField("en", "faName_2"), new LocalizedField("es", "faName_2_es")), 
                    false, 
                    List.of(new LocalizedField("en", "svgElemId2"), new LocalizedField("es", "svgElemId2_es")), 
                    List.of(new LocalizedField("en", "note2"), new LocalizedField("es", "note2_es")), 
                    new LocationResponse("fullName_2", "levelName_2", 2), 
                    List.of(firstAid2ImageEn, firstAid2ImageEs)
                )
            );
    }


    @Test
    public void POST_firstAid_returns_firstAidAdmin() {        


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.createFirstAid(any())).thenReturn(firstAid1AdminView);
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("firstAidMutationPost")
            .variable("input", payload)
            .execute()
            .path("createFirstAid")
            .entity(FirstAidAdminViewResponse.class)
            .isEqualTo(
                new FirstAidAdminViewResponse("1",
                    List.of(new LocalizedField("en", "faName_1"), new LocalizedField("es", "faName_1_es")), 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1),
                    List.of(firstAid1ImageEn, firstAid1ImageEs))
            );

    }


    @Test
    public void PUT_firstAid_returns_firstAidAdmin() {        

        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        when(service.updateFirstAid(any(), any())).thenReturn(firstAid1AdminView);
        when(locationsService.getBatchLocations(any())).thenReturn(mockBatchLocations);

        graphqlTester.documentName("firstAidMutationPut")
            .variable("input", payload)
            .variable("firstAidId", 1L)
            .execute()
            .path("updateFirstAid")
            .entity(FirstAidAdminViewResponse.class)
            .isEqualTo(
                new FirstAidAdminViewResponse("1",
                    List.of(new LocalizedField("en", "faName_1"), new LocalizedField("es", "faName_1_es")), 
                    true, 
                    List.of(new LocalizedField("en", "svgElemId"), new LocalizedField("es", "svgElemId_es")), 
                    List.of(new LocalizedField("en", "note"), new LocalizedField("es", "note_es")), 
                    new LocationResponse("fullName_1", "levelName_1", 1),
                    List.of(firstAid1ImageEn, firstAid1ImageEs))
            );

    }
    
    @Test
    public void DELETE_firstAid_returns_message() {

        // set up input
        Map<String,Object> locale = new HashMap<>();
        locale.put("id", 2L);

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", locale);

        graphqlTester.documentName("firstAidMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteFirstAid", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the first aid amenity with id=2");
            });

    }
 
    record FirstAidViewResponse(String id, String name, Boolean isWheelchairAccessible, String svgElemId, String note, LocationResponse location, FirstAidImage featImg) {}
    
    record LocationResponse(String fullname, String level_name, Integer level_num) {}

    record FirstAidAdminViewResponse(String id, List<LocalizedField> name, Boolean isWheelchairAccessible, List<LocalizedField> svgElemId, List<LocalizedField> note, LocationResponse location, List<LocalizedView<FirstAidImage>> featImg) {}

    
}
