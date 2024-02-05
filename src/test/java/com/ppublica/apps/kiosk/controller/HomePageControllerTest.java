package com.ppublica.apps.kiosk.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.ppublica.apps.kiosk.service.HomePageService;
import com.ppublica.apps.kiosk.service.views.home.HomePageView;

@GraphQlTest(controllers=HomePageController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class HomePageControllerTest {
    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private HomePageService service;

    Map<String,Object> payload;

    @BeforeEach
    public void setup() {
        // set up input
        Map<String,Object> input = new HashMap<>();
        input.put("pageTitle", "pageTitle");
        input.put("tapToContinuePrompt", "tapToContinuePrompt");
        input.put("welcomeText", "welcomeText");
        input.put("showSelectFromAvailableLocales", true);

        payload = new HashMap<>();

        payload.put("data", input);
    }

    @Test
    public void GET_homePage_returns_page() {

        // set up mock
        HomePageView homePageView = new HomePageView("id", "pageTitle", "tapToContinuePrompt", "welcomeText", true);
       
        when(service.getHomePage("en")).thenReturn(Optional.of(homePageView));


        graphqlTester.documentName("homePage")
            .variable("locale", "en")
            .execute()
            .path("homePage", homePage -> { homePage
                .path("id").entity(String.class).isEqualTo("id")
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("tapToContinuePrompt").entity(String.class).isEqualTo("tapToContinuePrompt")
                .path("welcomeText").entity(String.class).isEqualTo("welcomeText")
                .path("showSelectFromAvailableLocales").entity(Boolean.class).isEqualTo(true);

        });
    }

    @Test
    public void POST_homePage_returns_page() {

        // set up mocks
        HomePageView homePageView = new HomePageView("id", "pageTitle", "tapToContinuePrompt", "welcomeText", true);

        when(service.createHomePage(any(), any())).thenReturn(homePageView);

        graphqlTester.documentName("homePageMutationPost")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("createHomePage", homePage -> { homePage
                .path("id").entity(String.class).isEqualTo("id")
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("tapToContinuePrompt").entity(String.class).isEqualTo("tapToContinuePrompt")
                .path("welcomeText").entity(String.class).isEqualTo("welcomeText")
                .path("showSelectFromAvailableLocales").entity(Boolean.class).isEqualTo(true);
            });

    }

    @Test
    public void PUT_homePage_returns_page() {
    
        HomePageView homePageView = new HomePageView("id", "pageTitle", "tapToContinuePrompt", "welcomeText", true);

        when(service.updateHomePage(any(), any())).thenReturn(homePageView);

        graphqlTester.documentName("homePageMutationPut")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("updateHomePage", homePage -> { homePage
                .path("id").entity(String.class).isEqualTo("id")
                .path("pageTitle").entity(String.class).isEqualTo("pageTitle")
                .path("tapToContinuePrompt").entity(String.class).isEqualTo("tapToContinuePrompt")
                .path("welcomeText").entity(String.class).isEqualTo("welcomeText")
                .path("showSelectFromAvailableLocales").entity(Boolean.class).isEqualTo(true);
            });

    }

    @Test
    public void DELETE_homePage_returns_message() {

        // set up input
        Map<String,Object> locale = new HashMap<>();
        locale.put("locale", "en");

        Map<String,Object> payload = new HashMap<>();
        payload.put("where", locale);

        graphqlTester.documentName("homePageMutationDelete")
            .variable("input", payload)
            .execute()
            .path("deleteHomePage", response -> { response
                .path("message").entity(String.class).isEqualTo("Deleted the homePage in en");
            });
        
    }
}
