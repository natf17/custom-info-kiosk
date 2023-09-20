package com.ppublica.apps.kiosk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.repository.PageRepository;
import com.ppublica.apps.kiosk.service.views.about.AboutPageView;
import com.ppublica.apps.kiosk.service.views.about.ImageView;

public class AboutPageService {

    @Autowired
    private PageRepository repo;

    public Optional<AboutPageView> getAboutPage(String locale) {

        Optional<Page> aboutPageOpt = repo.findByPageTypeAndKioskLocale(AboutPage.PAGE_TYPE, locale);
        
        if (aboutPageOpt.isEmpty()) {
            return Optional.empty();
        }

        AboutPage aboutPage = AboutPage.fromPage(aboutPageOpt.get());

        return Optional.of(transformToView(aboutPage));

    }



    private AboutPageView transformToView(AboutPage aboutPage) {

        // build ImageView
        String imageAltText = aboutPage.getImageAltText(); 
        
        Image image = aboutPage.getImage();
        String imageLocation = image.location();
        Integer imageWidth = image.width();
        Integer imageHeight = image.height();

        ImageView imageView = new ImageView(imageLocation, imageWidth, imageHeight, imageAltText);


        // build and return AboutPage
        String title = aboutPage.getPageTitle();
        String richDescription = aboutPage.getRichDescription();

        return new AboutPageView(title, richDescription, imageView);

    }


}
