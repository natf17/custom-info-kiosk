package com.ppublica.apps.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.service.AboutPageService;
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

    


}
