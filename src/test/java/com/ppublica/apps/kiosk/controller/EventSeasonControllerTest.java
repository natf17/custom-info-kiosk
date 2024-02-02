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

import com.ppublica.apps.kiosk.service.collection.EventSeasonService;
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
    private EventSeasonService eventSeasonService;


    @Test
    public void GET_eventSeasonInLocale_returns_season() {

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
        localizedInputThemesEn.put("locale", "en");
        localizedInputThemesEn.put("value", "theme");

        Map<String,Object> localizedInputThemesEs = new HashMap<>();
        localizedInputThemesEn.put("locale", "es");
        localizedInputThemesEn.put("value", "tema");

        Map<String,Object> localizedInputDurationTextEn = new HashMap<>();
        localizedInputDurationTextEn.put("locale", "en");
        localizedInputDurationTextEn.put("value", "three");

        Map<String,Object> localizedInputDurationTextEs = new HashMap<>();
        localizedInputDurationTextEs.put("locale", "es");
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
        EventSeasonView eventSeasonEnMock = new EventSeasonView(10L, "type", 3, "theme", 2023, "", "three");
        EventSeasonView eventSeasonEsMock = new EventSeasonView(11L, "type", 3, "tema", 2023, "", "tres");
        EventSeasonAdminView eventSeasonAdminViewMock = new EventSeasonAdminView(List.of(eventSeasonEnMock, eventSeasonEsMock));

        when(eventSeasonService.createEventSeason(any())).thenReturn(eventSeasonAdminViewMock);

        graphqlTester.documentName("eventSeasonMutationPost")
            .variable("input", payload)
            .execute()
            .path("createEventSeason", response -> { response
                .path("seasons", seasons -> {
                    seasons.entityList(EventSeasonView.class).containsExactly(eventSeasonEnMock, eventSeasonEsMock);
                });
            });

    }

    @Test
    public void PUT_eventSeasonWithLocales_returns_seasonAdmin() {

        // set up input
        Map<String,Object> input = new HashMap<>();
        input.put("type", "type");
        input.put("durationDays", 3);
        input.put("theme", "newTheme");
        input.put("serviceYear", 2023);
        input.put("durationText", "newDurationText");

        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        // set up mocks
        EventSeasonView eventSeasonEnMock = new EventSeasonView(10L, "type", 3, "newTheme", 2023, "", "newDurationText");
        EventSeasonView eventSeasonEsMock = new EventSeasonView(11L, "type", 3, "tema", 2023, "", "tres");
        EventSeasonAdminView eventSeasonAdminViewMock = new EventSeasonAdminView(List.of(eventSeasonEnMock, eventSeasonEsMock));

        when(eventSeasonService.updateEventSeason(any(), any())).thenReturn(eventSeasonAdminViewMock);

        graphqlTester.documentName("eventSeasonMutationPut")
            .variable("input", payload)
            .variable("eventSeasonId", 10L)
            .execute()
            .path("updateEventSeason", response -> { response
                .path("seasons", seasons -> {
                    seasons.entityList(EventSeasonView.class).containsExactly(eventSeasonEnMock, eventSeasonEsMock);
                });
            });

    }

    @Test
    public void DELETE_eventSeason_returns_success() {

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
