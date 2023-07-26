package com.ppublica.apps.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.domain.model.pages.Image;
import com.ppublica.apps.kiosk.service.AboutPageService;


@Controller
public class AboutPageController {

    @Autowired
    AboutPageService service;
    
    @QueryMapping
    public AboutPage aboutPage(@Argument String locale) {
        //AboutPage aboutPage = service.getAboutPage(locale);
        
        AboutPage page = new AboutPage.Builder()
                            .pageTitle("sampleTitle")
                            .featureImage(Image.sampleImage())
                            .featureImageAltText("Sample image alt text")
                            .richDescription("Sample rich description")
                            .build();
        return page;
    }

    class AboutPageGraphQlView {
        String pageTitle;
        String richDescription;

    }




}
