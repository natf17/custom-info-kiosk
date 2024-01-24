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
    private EventSeasonService eventSeasonService;


    //@Test
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

        Map<String,String> payload = new HashMap<>();
        input.put("data", input);

        // set up mocks
        List<LocalizedField> themesMock = new ArrayList<>();
        themesMock.add(new LocalizedField("en", "theme"));
        themesMock.add(new LocalizedField("es", "tema"));

        List<LocalizedField> durationTextsMock = new ArrayList<>();
        durationTextsMock.add(new LocalizedField("en", "three"));
        durationTextsMock.add(new LocalizedField("es", "tres"));
        EventSeasonAdminView eventSeasonAdminViewMock = new EventSeasonAdminView(10L, "type", 3, themesMock, 2023, durationTextsMock);

        when(eventSeasonService.createEventSeason(any())).thenReturn(eventSeasonAdminViewMock);

        graphqlTester.documentName("eventSeasonMutationPost")
            .variable("input", payload)
            .execute()
            .path("createEventSeason", eventSeason -> { eventSeason
                .path("id").entity(String.class).isEqualTo("10")
                .path("type").entity(String.class).isEqualTo("type")
                .path("durationDays").entity(Integer.class).isEqualTo(3)
                .path("theme").entityList(LocalizedField.class).hasSize(2)
                .path("serviceYear").entity(Integer.class).isEqualTo(2023)
                .path("durationText").entityList(LocalizedField.class).hasSize(2);
            });

    }

    
    
}
