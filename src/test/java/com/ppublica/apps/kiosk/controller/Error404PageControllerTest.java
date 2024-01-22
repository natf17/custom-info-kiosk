package com.ppublica.apps.kiosk.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.ppublica.apps.kiosk.controller.Error404PageController;
import com.ppublica.apps.kiosk.service.Error404PageService;
import com.ppublica.apps.kiosk.service.views.error.Error404PageView;
import com.ppublica.apps.kiosk.service.views.error.RedirectLinkView;

@GraphQlTest(Error404PageController.class)
public class Error404PageControllerTest {

    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private Error404PageService pageService;

    /*
     * Test a query like the following:
     * 
     * query Error404Page($locale: String!) {
     *    error404Page(locale: ${locale}) {}
     *       pageTitle
     *       errorDescription
     *       showRedirectLink
     *       redirectLink {
     *          url
     *          displayText
     *          description
     *       }
     *    }
     * }
     * 
     */
    @Test
    public void GET_errorPage_returns_page() {

        // set up mocks
        RedirectLinkView redirectLinkView = new RedirectLinkView("redirect_url", "redirectLink_displayText", "redirectLink_description");
        Error404PageView errorView = new Error404PageView("sampleTitle", "Sample error description", true, redirectLinkView);

        when(pageService.getError404Page("en")).thenReturn(Optional.of(errorView));

        graphqlTester.documentName("error404Page")
            .variable("locale", "en")
            .execute()
            .path("aboutPage", aboutPage -> { aboutPage
                .path("pageTitle").entity(String.class).isEqualTo("sampleTitle")
                .path("errorDescription").entity(String.class).isEqualTo("Sample error description")
                .path("showRedirectLink").entity(Boolean.class).isEqualTo(true)
                .path("redirectLink", featImg -> { featImg
                    .path("url").entity(String.class).isEqualTo("redirect_url")
                    .path("displayText").entity(String.class).isEqualTo("redirectLink_displayText")
                    .path("description").entity(String.class).isEqualTo("redirectLink_description");

                });

        });

    }

    
}
