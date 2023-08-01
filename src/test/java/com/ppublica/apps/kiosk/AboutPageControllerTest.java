package com.ppublica.apps.kiosk;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

import com.ppublica.apps.kiosk.service.AboutPageService;
import com.ppublica.apps.kiosk.service.views.about.AboutPageView;
import com.ppublica.apps.kiosk.service.views.about.ImageView;


@AutoConfigureHttpGraphQlTester
@SpringBootTest
public class AboutPageControllerTest {

    // It is already initialized with a configured WebTestClient bound to the A.C
    @Autowired
    private HttpGraphQlTester httpGraphQlTester;

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

        /*
        // set up mock
        AboutPage page = new AboutPage.Builder()
                            .pageTitle("sampleTitle")
                            .featureImage(new Image("sample_url", 1, 1))
                            .featureImageAltText("Sample image alt text")
                            .richDescription("Sample rich description")
                            .build();
        */

        // set up mock
        ImageView imageView = new ImageView("sample_url", 1, 1, "Sample image alt text");
        AboutPageView aboutPageView = new AboutPageView("sampleTitle", "Sample rich description", imageView);
       
        when(pageService.getAboutPage("en")).thenReturn(aboutPageView);

        
        HttpGraphQlTester tester = httpGraphQlTester.mutate()
            .build();

        tester.documentName("aboutPage")
            .variable("locale", "en")
            .execute()
            .path("aboutPage", aboutPage -> { aboutPage
                .path("pageTitle").entity(String.class).isEqualTo("sampleTitle")
                .path("richDescription").entity(String.class).isEqualTo("Sample rich description")
                .path("featImg", featImg -> { featImg
                    .path("url").entity(String.class).isEqualTo("sample_url")
                    .path("width").entity(Integer.class).isEqualTo(Integer.valueOf(1))
                    .path("height").entity(Integer.class).isEqualTo(Integer.valueOf(1));

                });

        });
    }
    
}
