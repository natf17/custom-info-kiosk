package com.ppublica.apps.kiosk.controller;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.ppublica.apps.kiosk.service.Error404PageService;
import com.ppublica.apps.kiosk.service.views.about.AboutPageView;
import com.ppublica.apps.kiosk.service.views.about.ImageView;
import com.ppublica.apps.kiosk.service.views.error.Error404PageView;
import com.ppublica.apps.kiosk.service.views.error.RedirectLinkView;

@GraphQlTest(controllers=Error404PageController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
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
            .path("error404Page", error404Page -> { error404Page
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

    @Test
    public void POST_error404Page_returns_page() {

        // set up input
        Map<String,Object> redirectLinkInput = new HashMap<>();
        redirectLinkInput.put("url", "redirect_url");
        redirectLinkInput.put("displayText", "redirectLink_displayText");
        redirectLinkInput.put("description", "redirectLink_description");

        Map<String,Object> input = new HashMap<>();
        input.put("pageTitle", "sampleTitle");
        input.put("errorDescription", "Sample error description");
        input.put("showRedirectLink", true);
        input.put("redirectLink", redirectLinkInput);


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        // set up mocks
        RedirectLinkView redirectLinkView = new RedirectLinkView("redirect_url", "redirectLink_displayText", "redirectLink_description");
        Error404PageView errorView = new Error404PageView("sampleTitle", "Sample error description", true, redirectLinkView);

        when(pageService.createError404Page(any(), any())).thenReturn(errorView);

        graphqlTester.documentName("error404PageMutationPost")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("createError404Page", error404Page -> { error404Page
                .path("pageTitle").entity(String.class).isEqualTo("sampleTitle")
                .path("errorDescription").entity(String.class).isEqualTo("Sample error description")
                .path("showRedirectLink").entity(Boolean.class).isEqualTo(true)
                .path("redirectLink").entity(RedirectLinkView.class).isEqualTo(redirectLinkView);
                });

        }

        @Test
        public void PUT_error404Page_returns_page() {

        // set up input
        Map<String,Object> redirectLinkInput = new HashMap<>();
        redirectLinkInput.put("url", "upd_redirect_url");
        redirectLinkInput.put("displayText", "upd_redirectLink_displayText");
        redirectLinkInput.put("description", "upd_redirectLink_description");

        Map<String,Object> input = new HashMap<>();
        input.put("pageTitle", "upd_sampleTitle");
        input.put("errorDescription", "upd_Sample error description");
        input.put("showRedirectLink", true);
        input.put("redirectLink", redirectLinkInput);


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        // set up mocks
        RedirectLinkView redirectLinkView = new RedirectLinkView("upd_redirect_url", "upd_redirectLink_displayText", "upd_redirectLink_description");
        Error404PageView errorView = new Error404PageView("upd_sampleTitle", "upd_Sample error description", true, redirectLinkView);

        when(pageService.updateError404Page(any(), any())).thenReturn(errorView);

        graphqlTester.documentName("error404PageMutationPut")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("updateError404Page", error404Page -> { error404Page
                .path("pageTitle").entity(String.class).isEqualTo("upd_sampleTitle")
                .path("errorDescription").entity(String.class).isEqualTo("upd_Sample error description")
                .path("showRedirectLink").entity(Boolean.class).isEqualTo(true)
                .path("redirectLink").entity(RedirectLinkView.class).isEqualTo(redirectLinkView);
                });

        }

        @Test
        public void DELETE_error404Page_returns_page() {

            // set up input
            Map<String,Object> locale = new HashMap<>();
            locale.put("locale", "en");

            Map<String,Object> payload = new HashMap<>();
            payload.put("where", locale);

            graphqlTester.documentName("error404PageMutationDelete")
                .variable("input", payload)
                .execute()
                .path("deleteError404Page", response -> { response
                    .path("message").entity(String.class).isEqualTo("Deleted the error404page in en");
                    });

            }

    
}
