package com.ppublica.apps.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.service.AboutPageService;
import com.ppublica.apps.kiosk.service.views.about.AboutPageView;


@Controller
public class AboutPageController {

    @Autowired
    private AboutPageService service;
    
    @QueryMapping
    public AboutPageView aboutPage(@Argument String locale) {
        AboutPageView aboutPageView = service.getAboutPage(locale);

        return aboutPageView;
    }

    


}
