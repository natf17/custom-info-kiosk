package com.ppublica.apps.kiosk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

/* @ExtendWith(SpringExtension.class)
 * @ContextConfiguration(classes=TestConfig.class)
 * @WebAppConfiguration
*/ 
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyApplicationIT {

    @Autowired
    WebTestClient webTestClient;

    // configured automatically by @SpringBootTest
    @Autowired
    HttpGraphQlTester graphqlTester;

    @BeforeAll
    static void setup() {
        // populate about page with feature image width value 100
        

    }

    @Test
    void exampleTest() {
        webTestClient.get().uri("/")
                      .exchange()
                      .expectStatus().isOk()
                      .expectBody(String.class).isEqualTo("Hello world!");
    }

    @Test
    void exampleGraphQlTest() {
        HttpGraphQlTester tester = graphqlTester.mutate()
            .build();
        tester.documentName("aboutPage")
            .variable("locale", "en")
            .execute()
            .path("aboutPage", aboutPage -> aboutPage
                                                .path("featImg.width")
                                                .entity(Integer.class)
                                                .isEqualTo(Integer.valueOf(100))
            );

    }

}