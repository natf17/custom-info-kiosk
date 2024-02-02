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
import com.ppublica.apps.kiosk.service.payloads.data.seasonalevent.SeasonalEventInput;
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

    @Test
    public void POST_seasonalEventList_returns_seasonEventList() {

        // set up input
        Map<String,Object> seasonalEvent1 = new HashMap<>();
        seasonalEvent1.put("startDate", "20240124");
        seasonalEvent1.put("eventLanguage", "en");

        Map<String,Object> seasonalEvent2 = new HashMap<>();
        seasonalEvent2.put("startDate", "20240131");
        seasonalEvent2.put("eventLanguage", "es");

        Map<String,Object> payload = new HashMap<>();
        payload.put("data", List.of(seasonalEvent1, seasonalEvent2));

        // set up mocks
        SeasonalEventInput input1 = new SeasonalEventInput("20240124", "en");
        SeasonalEventInput input2 = new SeasonalEventInput("20240131", "es");
        List<SeasonalEventInput> inputList = new ArrayList<>();
        inputList.add(input1);
        inputList.add(input2);
        
        SeasonalEventView seasonalEventView1Mock = new SeasonalEventView(1L, "type", "20240124", "en", 1L);
        SeasonalEventView seasonalEventView2Mock = new SeasonalEventView(2L, "type", "20240131", "es", 1L);

        Map<Long,EventSeasonView> eventSeasonsMock = new HashMap<Long,EventSeasonView>();
        eventSeasonsMock.put(1L, new EventSeasonView(1L, "type", 3, "theme", 2023, "", "three days"));

        when(seasonalEventService.createSeasonalEventsBatch(any(), any())).thenReturn(List.of(seasonalEventView1Mock, seasonalEventView2Mock));
        when(eventSeasonService.getBatchEventSeasons(any())).thenReturn(eventSeasonsMock);

        graphqlTester.documentName("seasonalEventMutationPost")
            .variable("input", payload)
            .variable("eventSeasonId", "1")
            .execute()
            .path("createSeasonalEvents", seasonalEvents -> { seasonalEvents
                .entityList(SeasonalEventView.class).hasSize(2);
            });

    }

    @Test
    public void PUT_seasonalEvent_returns_seasonEvent() {

        // set up input
        Map<String,Object> seasonalEvent1 = new HashMap<>();
        seasonalEvent1.put("startDate", "20240124");
        seasonalEvent1.put("eventLanguage", "en");

        Map<String,Object> payload = new HashMap<>();
        payload.put("data", seasonalEvent1);

        // set up mocks        
        SeasonalEventView seasonalEventView1Mock = new SeasonalEventView(1L, "type", "20240124", "en", 1L);

        Map<Long,EventSeasonView> eventSeasonsMock = new HashMap<Long,EventSeasonView>();
        eventSeasonsMock.put(1L, new EventSeasonView(1L, "type", 3, "theme", 2023, "", "three days"));

        when(seasonalEventService.updateSeasonalEvent(any(), any())).thenReturn(seasonalEventView1Mock);
        when(eventSeasonService.getBatchEventSeasons(any())).thenReturn(eventSeasonsMock);

        graphqlTester.documentName("seasonalEventMutationPut")
            .variable("input", payload)
            .variable("seasonalEventId", "1")
            .execute()
            .path("updateSeasonalEvent", seasonalEvent -> { seasonalEvent
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

    @Test
    public void DELETE_seasonalEvent_returns_success() {

        // set up input
        Map<String,Object> id = new HashMap<>();
        id.put("id", "2");

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", id);


        graphqlTester.documentName("seasonalEventMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteSeasonalEvent", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the seasonal event with id = 2");
            });
    }

    
}
