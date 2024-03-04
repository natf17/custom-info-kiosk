package com.ppublica.apps.kiosk.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.ppublica.apps.kiosk.service.EventSeasonDataService;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonAdminView;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonView;

/*
 * 
 */
@GraphQlTest(controllers=EventSeasonController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class EventSeasonControllerTest {

    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private EventSeasonDataService eventSeasonService;


    @Test
    public void GET_eventSeasonsInLocale_returns_seasons() {

        // set up mocks
        EventSeasonView eventSeasonView = new EventSeasonView(10L, "type", 3, "theme", 2023, "", "three days");
        List<EventSeasonView> eventSeasonsMock = new ArrayList<>();
        eventSeasonsMock.add(eventSeasonView);

        when(eventSeasonService.getEventSeasons("en")).thenReturn(eventSeasonsMock);

        graphqlTester.documentName("eventSeasonQuery")
            .variable("locale", "en")
            .execute()
            .path("eventSeasons", eventSeasons -> { eventSeasons
                .entityList(EventSeasonView.class).hasSize(1).containsExactly(eventSeasonView);
        });

    }

    @Test
    public void POST_eventSeasonWithLocales_returns_seasonAdmin() {

        // set up input
        Map<String,Object> localizedInputThemesEn = new HashMap<>();
        localizedInputThemesEn.put("localeId", "1");
        localizedInputThemesEn.put("value", "theme");

        Map<String,Object> localizedInputThemesEs = new HashMap<>();
        localizedInputThemesEs.put("localeId", "2");
        localizedInputThemesEs.put("value", "tema");

        Map<String,Object> localizedInputDurationTextEn = new HashMap<>();
        localizedInputDurationTextEn.put("localeId", "1");
        localizedInputDurationTextEn.put("value", "three");

        Map<String,Object> localizedInputDurationTextEs = new HashMap<>();
        localizedInputDurationTextEs.put("localeId", "2");
        localizedInputDurationTextEs.put("value", "tres");

        Map<String,Object> input = new HashMap<>();
        input.put("type", "type");
        input.put("durationDays", 3);
        input.put("theme", List.of(localizedInputThemesEn, localizedInputThemesEs));
        input.put("serviceYear", 2023);
        input.put("durationText", List.of(localizedInputDurationTextEn, localizedInputDurationTextEs));

        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        // set up mocks
        EventSeasonAdminView eventSeasonAdminViewMock = new EventSeasonAdminView(10L, 
                                                                                "type", 
                                                                                3, 
                                                                                List.of(new LocalizedField("en", "theme"), new LocalizedField("es", "tema")),
                                                                                2023,
                                                                                "",
                                                                                List.of(new LocalizedField("en", "three"), new LocalizedField("es", "tres")));

        when(eventSeasonService.createEventSeason(any())).thenReturn(eventSeasonAdminViewMock);

        graphqlTester.documentName("eventSeasonMutationPost")
            .variable("input", payload)
            .execute()
            .path("createEventSeason", response -> { response
                .entity(EventSeasonAdminView.class).isEqualTo(eventSeasonAdminViewMock);
            });

    }

    @Test
    public void PUT_eventSeasonWithLocales_returns_seasonAdmin() {

        // set up input
        Map<String,Object> localizedInputThemesEn = new HashMap<>();
        localizedInputThemesEn.put("localeId", "1");
        localizedInputThemesEn.put("value", "theme");

        Map<String,Object> localizedInputThemesEs = new HashMap<>();
        localizedInputThemesEs.put("localeId", "2");
        localizedInputThemesEs.put("value", "tema");

        Map<String,Object> localizedInputDurationTextEn = new HashMap<>();
        localizedInputDurationTextEn.put("localeId", "1");
        localizedInputDurationTextEn.put("value", "three");

        Map<String,Object> localizedInputDurationTextEs = new HashMap<>();
        localizedInputDurationTextEs.put("localeId", "2");
        localizedInputDurationTextEs.put("value", "tres");

        Map<String,Object> input = new HashMap<>();
        input.put("type", "type");
        input.put("durationDays", 3);
        input.put("theme", List.of(localizedInputThemesEn, localizedInputThemesEs));
        input.put("serviceYear", 2023);
        input.put("durationText", List.of(localizedInputDurationTextEn, localizedInputDurationTextEs));

        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        // set up mocks
        EventSeasonAdminView eventSeasonAdminViewMock = new EventSeasonAdminView(10L, 
                                                                                "type", 
                                                                                3, 
                                                                                List.of(new LocalizedField("en", "theme"), new LocalizedField("es", "tema")),
                                                                                2023,
                                                                                "",
                                                                                List.of(new LocalizedField("en", "three"), new LocalizedField("es", "tres")));

        when(eventSeasonService.updateEventSeason(any(), any())).thenReturn(eventSeasonAdminViewMock);

        graphqlTester.documentName("eventSeasonMutationPut")
            .variable("input", payload)
            .variable("eventSeasonId", 10L)
            .execute()
            .path("updateEventSeason", response -> { response
                .entity(EventSeasonAdminView.class).isEqualTo(eventSeasonAdminViewMock);
            });

    }

    @Test
    public void DELETE_eventSeason_returns_message() {

        // set up input
        Map<String,Object> id = new HashMap<>();
        id.put("id", "2");

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", id);


        graphqlTester.documentName("eventSeasonMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteEventSeason", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the event season with id = 2");
            });
    }

    
    
}
