package com.ppublica.apps.kiosk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.HomePageService;
import com.ppublica.apps.kiosk.service.payloads.PageDeletePayload;
import com.ppublica.apps.kiosk.service.payloads.home.HomePagePayload;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.home.HomePageView;

@Controller
public class HomePageController {
    
    @Autowired
    private HomePageService service;

    @QueryMapping
    public HomePageView homePage(@Argument String locale) {
        HomePageView homePageView = service.getHomePage(locale).get();

        return homePageView;
    }

    @MutationMapping
    public HomePageView createHomePage(@Argument String locale, @Argument HomePagePayload input) {
        HomePageView homePageView = service.createHomePage(locale, input.data());

        return homePageView;

    }

    @MutationMapping
    public HomePageView updateHomePage(@Argument String locale, @Argument HomePagePayload input) {
        HomePageView homePageView = service.updateHomePage(locale, input.data());

        return homePageView;
    }
    
    @MutationMapping
    public MessageResponse deleteHomePage(@Argument PageDeletePayload input) {
        String locale = input.where().locale();
        service.deleteHomePage(locale);

        return new MessageResponse("Deleted the homePage in " + locale);
    }
}
