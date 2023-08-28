package com.ppublica.apps.kiosk.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.domain.model.pages.Image;
import com.ppublica.apps.kiosk.domain.model.pages.ImageField;
import com.ppublica.apps.kiosk.repository.AboutPageRepository;
import com.ppublica.apps.kiosk.service.views.about.AboutPageView;
import com.ppublica.apps.kiosk.service.views.about.ImageView;

public class AboutPageService {

    @Autowired
    private AboutPageRepository repo;
    

    public AboutPageView getAboutPage(String locale) {
        
        
        AboutPage aboutPage = null; //repo.findByPageInternalsLocaleAbbrev(locale);

        return transformToView(aboutPage);

    }



    private AboutPageView transformToView(AboutPage page) {

        // build ImageView
        ImageField imageField = page.getFeatureImageField();
        String imageAltText = imageField.getAltTextField().getFieldValue(); 
        
        Image image = imageField.getFieldValue();
        String imageLocation = image.location();
        Integer imageWidth = image.width();
        Integer imageHeight = image.height();

        ImageView imageView = new ImageView(imageLocation, imageWidth, imageHeight, imageAltText);


        // build and return AboutPage
        String title = page.getPageTitleField().getFieldValue();
        String richDescription = page.getRichDescriptionField().getFieldValue();

        return new AboutPageView(title, richDescription, imageView);

    }


}
