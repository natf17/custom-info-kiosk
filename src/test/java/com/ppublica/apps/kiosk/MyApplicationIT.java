package com.ppublica.apps.kiosk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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

    @Test
    void exampleTest() {
        webTestClient.get().uri("/")
                      .exchange()
                      .expectStatus().isOk()
                      .expectBody(String.class).isEqualTo("Hello world!");
    }

}