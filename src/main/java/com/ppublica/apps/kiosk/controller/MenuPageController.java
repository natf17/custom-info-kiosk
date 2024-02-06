package com.ppublica.apps.kiosk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.MenuPageService;
import com.ppublica.apps.kiosk.service.payloads.PageDeletePayload;
import com.ppublica.apps.kiosk.service.payloads.menu.MenuPagePayload;
import com.ppublica.apps.kiosk.service.views.MessageResponse;
import com.ppublica.apps.kiosk.service.views.menu.MenuPageView;

@Controller
public class MenuPageController {
    @Autowired
    private MenuPageService service;

    @QueryMapping
    public MenuPageView menuPage(@Argument String locale) {
        MenuPageView mapPageView = service.getMenuPage(locale).get();

        return mapPageView;
    }

    @MutationMapping
    public MenuPageView createMenuPage(@Argument String locale, @Argument MenuPagePayload input) {
        MenuPageView mapPageView = service.createMenuPage(locale, input.data());

        return mapPageView;

    }

    @MutationMapping
    public MenuPageView updateMenuPage(@Argument String locale, @Argument MenuPagePayload input) {
        MenuPageView mapPageView = service.updateMenuPage(locale, input.data());

        return mapPageView;
    }
    
    @MutationMapping
    public MessageResponse deleteMenuPage(@Argument PageDeletePayload input) {
        String locale = input.where().locale();
        service.deleteMenuPage(locale);

        return new MessageResponse("Deleted the menuPage in " + locale);
    }

}
