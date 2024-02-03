package com.ppublica.apps.kiosk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.domain.model.pages.ImageContainer;
import com.ppublica.apps.kiosk.repository.PageRepository;
import com.ppublica.apps.kiosk.service.payloads.about.AboutPageInput;
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

        AboutPage aboutPage = new AboutPage.Builder(aboutPageOpt.get())
                                .build();

        return Optional.of(transformToView(aboutPage));

    }

    public AboutPageView createAboutPage(String locale, AboutPageInput input) {
        throw new UnsupportedOperationException();
    }

    public AboutPageView updateAboutPage(String locale, AboutPageInput input) {
        throw new UnsupportedOperationException();
    }

    public void deleteAboutPage(String locale) {
        throw new UnsupportedOperationException();
    }



    private AboutPageView transformToView(AboutPage aboutPage) {

        // build ImageView
        ImageContainer imageContainer = aboutPage.getImageContainer(); 
        
        Image image = imageContainer.getImageField().getFieldValue();
        String imageLocation = image.location();
        Integer imageWidth = image.width();
        Integer imageHeight = image.height();

        String imageAltText = imageContainer.getImageAltTextField().getFieldValue();

        ImageView imageView = new ImageView(imageLocation, imageWidth, imageHeight, imageAltText);


        // build and return AboutPage
        String title = aboutPage.getPageTitleField().getFieldValue();
        String richDescription = aboutPage.getRichDescrField().getFieldValue();

        return new AboutPageView(title, richDescription, imageView);

    }


}
