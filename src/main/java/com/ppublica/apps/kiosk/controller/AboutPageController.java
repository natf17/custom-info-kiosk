package com.ppublica.apps.kiosk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.AboutPageService;
import com.ppublica.apps.kiosk.service.payloads.PageDeletePayload;
import com.ppublica.apps.kiosk.service.payloads.about.AboutPagePayload;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.about.AboutPageView;


@Controller
public class AboutPageController {

    @Autowired
    private AboutPageService service;
    
    @QueryMapping
    public AboutPageView aboutPage(@Argument String locale) {
        // todo: decide error for page not found

        AboutPageView aboutPageView = service.getAboutPage(locale).get();

        return aboutPageView;
    }

    @MutationMapping
    public AboutPageView createAboutPage(@Argument String locale, @Argument AboutPagePayload input) {
        AboutPageView aboutPageView = service.createAboutPage(locale, input.data());

        return aboutPageView;
    }

    @MutationMapping
    public AboutPageView updateAboutPage(@Argument String locale, @Argument AboutPagePayload input) {
        AboutPageView aboutPageView = service.updateAboutPage(locale, input.data());

        return aboutPageView;
    }

    
    @MutationMapping
    public MessageResponse deleteAboutPage(@Argument PageDeletePayload input) {
        String locale = input.where().locale();
        service.deleteAboutPage(locale);

        return new MessageResponse("Deleted the aboutPage in " + locale);
    }

    


}
