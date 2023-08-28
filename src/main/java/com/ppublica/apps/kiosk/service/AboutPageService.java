package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.domain.model.pages.Image;
import com.ppublica.apps.kiosk.domain.model.pages.ImageField;
import com.ppublica.apps.kiosk.repository.AboutPageRepository;
import com.ppublica.apps.kiosk.repository.KioskLocaleRepository;
import com.ppublica.apps.kiosk.service.views.about.AboutPageView;
import com.ppublica.apps.kiosk.service.views.about.ImageView;

public class AboutPageService {

    @Autowired
    private AboutPageRepository repo;

    @Autowired
    private KioskLocaleRepository localeRepo;
    
    /*
     * This service method expects only one about page for a given locale
     * to exist.
     */
    public Optional<AboutPageView> getAboutPage(String locale) {
        
        List<Long> aboutPageIds = repo.getAboutPageIdsforLocale(locale);

        if (aboutPageIds.isEmpty()) {
            return Optional.empty();
        }

        Optional<AboutPage> aboutPage = repo.findById(aboutPageIds.get(0));

        if (aboutPage.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(transformToView(aboutPage.get()));

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
