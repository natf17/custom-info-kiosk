package com.ppublica.apps.kiosk.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.ppublica.apps.kiosk.service.collection.EventSeasonService;
import com.ppublica.apps.kiosk.service.collection.SeasonalEventService;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonView;
import com.ppublica.apps.kiosk.service.views.data.seasonalevent.SeasonalEventView;

@GraphQlTest(controllers=SeasonalEventController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class SeasonalEventControllerTest {
    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private EventSeasonService eventSeasonService;

    @MockBean
    private SeasonalEventService seasonalEventService;


    @Test
    public void GET_seasonalEventsInLocale_returns_event() {

        // set up mocks
        List<SeasonalEventView> seasonalEventsMock = new ArrayList<>();
        seasonalEventsMock.add(new SeasonalEventView(1L, "type", "20240124", "en", 1L));
        seasonalEventsMock.add(new SeasonalEventView(2L, "type", "20240125", "en", 1L));
        seasonalEventsMock.add(new SeasonalEventView(3L, "type", "20240126", "en", 2L));

        Map<Long,EventSeasonView> eventSeasonsMock = new HashMap<Long,EventSeasonView>();
        eventSeasonsMock.put(1L, new EventSeasonView(1L, "type", 3, "theme", 2023, "", "three days"));
        eventSeasonsMock.put(2L, new EventSeasonView(2L, "type", 4, "theme2", 2023, "", "four days"));

        when(seasonalEventService.getSeasonalEvents("en", "startDate:asc")).thenReturn(seasonalEventsMock);
        when(eventSeasonService.getBatchEventSeasons(any())).thenReturn(eventSeasonsMock);

        graphqlTester.documentName("seasonalEventsQuery")
            .variable("locale", "en")
            .variable("sort", "startDate:asc")
            .execute()
            .path("seasonalEvents", seasonalEvents -> { seasonalEvents
                .entityList(SeasonalEventView.class).hasSize(3);
            });

    }

    @Test
    public void GET_seasonalEventInLocale_returns_event() {

        // set up mocks
        Optional<SeasonalEventView> seasonalEventMock = Optional.of(new SeasonalEventView(1L, "type", "20240124", "en", 1L));
    
        Map<Long,EventSeasonView> eventSeasonsMock = new HashMap<Long,EventSeasonView>();
        eventSeasonsMock.put(1L, new EventSeasonView(1L, "type", 3, "theme", 2023, "", "three days"));

        when(seasonalEventService.getSeasonalEvent(1L, "en")).thenReturn(seasonalEventMock);
        when(eventSeasonService.getBatchEventSeasons(any())).thenReturn(eventSeasonsMock);

        graphqlTester.documentName("seasonalEventQuery")
            .variable("locale", "en")
            .variable("id", "1")
            .execute()
            .path("seasonalEvent", seasonalEvent -> { seasonalEvent
                .path("id").entity(String.class).isEqualTo("1")
                .path("seasonalType").entity(String.class).isEqualTo("type")
                .path("startDate").entity(String.class).isEqualTo("20240124")
                .path("eventLanguage").entity(String.class).isEqualTo("en")
                .path("event_season", eventSeason -> { eventSeason
                    .path("serviceYear").entity(Integer.class).isEqualTo(2023)
                    .path("seasonYears").entity(String.class).isEqualTo("")
                    .path("type").entity(String.class).isEqualTo("type")
                    .path("theme").entity(String.class).isEqualTo("theme");
                    
                });
            }); 

    }



    

    
}
