package com.ppublica.apps.kiosk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.pages.HomePage;
import com.ppublica.apps.kiosk.repository.PageRepository;
import com.ppublica.apps.kiosk.service.payloads.home.HomePageInput;
import com.ppublica.apps.kiosk.service.views.home.HomePageView;

public class HomePageService {
    @Autowired
    private PageRepository repo;

    public Optional<HomePageView> getHomePage(String locale) {

        Optional<Page> homePageOpt = repo.findByPageTypeAndKioskLocale(HomePage.PAGE_TYPE, locale);
        
        if (homePageOpt.isEmpty()) {
            return Optional.empty();
        }

        HomePage homePage = new HomePage.Builder(homePageOpt.get())
                                .build();

        return Optional.of(transformToView(homePage));

    }

    public HomePageView createHomePage(String locale, HomePageInput input) {
        throw new UnsupportedOperationException();
    }

    public HomePageView updateHomePage(String locale, HomePageInput input) {
        throw new UnsupportedOperationException();
    }

    public void deleteHomePage(String locale) {
        throw new UnsupportedOperationException();
    }

    private HomePageView transformToView(HomePage homePage) {

        String id = homePage.getKioskPageMetadata().getPageInternals().getKioskLocaleId().toString();
        String pageTitle = homePage.getPageTitleField().getFieldValue();
        String tapToContinuePrompt = homePage.getTapToContinueText().getFieldValue();
        String welcomeText = homePage.getWelcomeText().getFieldValue();
        boolean showSelectFromAvailableLocales = homePage.shouldShowSelectFromAvailableLocales().getFieldValue();

        return new HomePageView(id, pageTitle, tapToContinuePrompt, welcomeText, showSelectFromAvailableLocales);

    }

}
