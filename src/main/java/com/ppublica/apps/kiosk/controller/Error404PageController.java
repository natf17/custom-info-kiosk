package com.ppublica.apps.kiosk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.Error404PageService;
import com.ppublica.apps.kiosk.service.payloads.PageDeletePayload;
import com.ppublica.apps.kiosk.service.payloads.error.Error404PagePayload;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.error.Error404PageView;

@Controller
public class Error404PageController {

    @Autowired
    private Error404PageService service;
    
    @QueryMapping
    public Error404PageView error404Page(@Argument String locale) {
        // todo: decide error for page not found

        Error404PageView error404PageView = service.getError404Page(locale).get();

        return error404PageView;
    }
    
    @MutationMapping
    public Error404PageView createError404Page(@Argument String locale, @Argument Error404PagePayload input) {
        Error404PageView error404PageView = service.createError404Page(locale, input.data());

        return error404PageView;
    }

    @MutationMapping
    public Error404PageView updateError404Page(@Argument String locale, @Argument Error404PagePayload input) {
        Error404PageView error404PageView = service.updateError404Page(locale, input.data());

        return error404PageView;
    }

    
    @MutationMapping
    public MessageResponse deleteError404Page(@Argument PageDeletePayload input) {
        String locale = input.where().locale();
        service.deleteError404Page(locale);

        return new MessageResponse("Deleted the error404page in " + locale);
    }
}
