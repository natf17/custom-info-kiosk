package com.ppublica.apps.kiosk.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.ppublica.apps.kiosk.service.EventSeasonService;
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
    
}
