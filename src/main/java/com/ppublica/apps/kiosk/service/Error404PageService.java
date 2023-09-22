package com.ppublica.apps.kiosk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.pages.Error404Page;
import com.ppublica.apps.kiosk.repository.PageRepository;
import com.ppublica.apps.kiosk.service.views.error.Error404PageView;
import com.ppublica.apps.kiosk.service.views.error.RedirectLinkView;

public class Error404PageService {

    @Autowired
    private PageRepository repo;


    public Optional<Error404PageView> getError404Page(String locale) {
        Optional<Page> errorPageOpt = repo.findByPageTypeAndKioskLocale(Error404Page.PAGE_TYPE, locale);
        
        if (errorPageOpt.isEmpty()) {
            return Optional.empty();
        }

        Error404Page error404Page = Error404Page.fromPage(errorPageOpt.get());

        return Optional.of(transformToView(error404Page));

    }
    
    private Error404PageView transformToView(Error404Page page) {
        String redirectUrl = page.getRedirectUrl();
        String redirectDisplayText = page.getRedirectDisplayText();
        String redirectDescription = page.getRedirectDescription();
        RedirectLinkView redirectLinkView = new RedirectLinkView(redirectUrl, redirectDisplayText, redirectDescription); 

        String pageTitle = page.getPageTitle();
        String errorDescription = page.getErrorDescription();
        Boolean showRedirectLink = page.shouldShowRedirectLink();

        return new Error404PageView(pageTitle, errorDescription, showRedirectLink, redirectLinkView);
    }
}
