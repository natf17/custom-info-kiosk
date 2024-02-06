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

import com.ppublica.apps.kiosk.service.AboutPageService;
import com.ppublica.apps.kiosk.service.views.about.AboutPageView;
import com.ppublica.apps.kiosk.service.views.about.ImageView;

/*
 * Not using...
 *   @AutoConfigureHttpGraphqlTester
 *   @SpringBootTest
 * 
 * ... because it starts up a server and it reads entire configuration files,
 * which processes schema files. We want to test a thinner slice.
 *   
 */
@GraphQlTest(controllers=AboutPageController.class, properties={"spring.graphql.schema.locations=classpath:graphql-test/**"})
public class AboutPageControllerTest {

    // It is already initialized with a configured WebTestClient bound to the A.C
    @Autowired
    private GraphQlTester graphqlTester;

    @MockBean
    private AboutPageService pageService;

    /*
     * Test a query like the following:
     * 
     * query AboutPage($locale: String!) {
     *    aboutPage(locale: $locale) {
     *       pageTitle
     *       richDescription
     *       featImg {
     *          url
     *          width
     *          height
     *          alternativeText
     *       }
     *    }
     * }
     * 
     */
    @Test
    public void GET_aboutPage_returns_page() {

        // set up mock
        ImageView imageView = new ImageView("sample_url", 1, 1, "Sample image alt text");
        AboutPageView aboutPageView = new AboutPageView("sampleTitle", "Sample rich description", imageView);
       
        when(pageService.getAboutPage("en")).thenReturn(Optional.of(aboutPageView));


        graphqlTester.documentName("aboutPage")
            .variable("locale", "en")
            .execute()
            .path("aboutPage", aboutPage -> { aboutPage
                .path("pageTitle").entity(String.class).isEqualTo("sampleTitle")
                .path("richDescription").entity(String.class).isEqualTo("Sample rich description")
                .path("featImg", featImg -> { featImg
                    .path("url").entity(String.class).isEqualTo("sample_url")
                    .path("width").entity(Integer.class).isEqualTo(1)
                    .path("height").entity(Integer.class).isEqualTo(1);

                });

        });
    }

    @Test
    public void POST_aboutPage_returns_page() {

        // set up input
        Map<String,Object> featImgInput = new HashMap<>();
        featImgInput.put("url", "sample_url");
        featImgInput.put("width", 1);
        featImgInput.put("height", 1);
        featImgInput.put("alternativeText", "Sample image alt text");

        Map<String,Object> input = new HashMap<>();
        input.put("pageTitle", "sampleTitle");
        input.put("richDescription", "Sample rich description");
        input.put("featImg", featImgInput);


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        // set up mocks
        ImageView imageView = new ImageView("sample_url", 1, 1, "Sample image alt text");
        AboutPageView aboutPageView = new AboutPageView("sampleTitle", "Sample rich description", imageView);

        when(pageService.createAboutPage(any(), any())).thenReturn(aboutPageView);

        graphqlTester.documentName("aboutPageMutationPost")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("createAboutPage", aboutPage -> { aboutPage
                .path("pageTitle").entity(String.class).isEqualTo("sampleTitle")
                .path("richDescription").entity(String.class).isEqualTo("Sample rich description")
                .path("featImg", featImg -> { featImg
                    .path("url").entity(String.class).isEqualTo("sample_url")
                    .path("width").entity(Integer.class).isEqualTo(1)
                    .path("height").entity(Integer.class).isEqualTo(1);

                });
            });

        }

        @Test
        public void PUT_aboutPage_returns_page() {

        // set up input
        Map<String,Object> featImgInput = new HashMap<>();
        featImgInput.put("url", "sample_url");
        featImgInput.put("width", 1);
        featImgInput.put("height", 1);
        featImgInput.put("alternativeText", "Sample image alt text");

        Map<String,Object> input = new HashMap<>();
        input.put("pageTitle", "sampleTitle");
        input.put("richDescription", "Sample rich description");
        input.put("featImg", featImgInput);


        Map<String,Object> payload = new HashMap<>();
        payload.put("data", input);

        // set up mocks
        ImageView imageView = new ImageView("sample_url", 1, 1, "Sample image alt text");
        AboutPageView aboutPageView = new AboutPageView("sampleTitle", "Sample rich description", imageView);

        when(pageService.updateAboutPage(any(), any())).thenReturn(aboutPageView);

        graphqlTester.documentName("aboutPageMutationPut")
            .variable("input", payload)
            .variable("locale", "en")
            .execute()
            .path("updateAboutPage", aboutPage -> { aboutPage
                .path("pageTitle").entity(String.class).isEqualTo("sampleTitle")
                .path("richDescription").entity(String.class).isEqualTo("Sample rich description")
                .path("featImg", featImg -> { featImg
                    .path("url").entity(String.class).isEqualTo("sample_url")
                    .path("width").entity(Integer.class).isEqualTo(1)
                    .path("height").entity(Integer.class).isEqualTo(1);

                });
            });

        }

        @Test
        public void DELETE_aboutPage_returns_message() {

            // set up input
            Map<String,Object> locale = new HashMap<>();
            locale.put("locale", "en");

            Map<String,Object> payload = new HashMap<>();
            payload.put("where", locale);

            graphqlTester.documentName("aboutPageMutationDelete")
                .variable("input", payload)
                .execute()
                .path("deleteAboutPage", response -> { response
                    .path("message").entity(String.class).isEqualTo("Deleted the aboutPage in en");
                });

        }
    
}
