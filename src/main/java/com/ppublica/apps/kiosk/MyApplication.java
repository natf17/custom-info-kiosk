package com.ppublica.apps.kiosk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@SpringBootApplication
public class MyApplication {
    
    @GetMapping("/")
    String home() {
        return "Hello world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}