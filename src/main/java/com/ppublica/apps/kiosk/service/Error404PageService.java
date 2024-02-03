package com.ppublica.apps.kiosk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.pages.Error404Page;
import com.ppublica.apps.kiosk.domain.model.pages.RedirectUrlContainer;
import com.ppublica.apps.kiosk.repository.PageRepository;
import com.ppublica.apps.kiosk.service.payloads.error.Error404PageInput;
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

        Error404Page error404Page = new Error404Page.Builder(errorPageOpt.get())
                                    .build();

        return Optional.of(transformToView(error404Page));

    }

    public Error404PageView createError404Page(String locale, Error404PageInput data) {
        throw new UnsupportedOperationException();
    }

    public Error404PageView updateError404Page(Long error404PageId, Error404PageInput data) {
        throw new UnsupportedOperationException();
    }

    public void deleteError404Page(String locale) {
        throw new UnsupportedOperationException();
    }
    
    private Error404PageView transformToView(Error404Page page) {
        RedirectUrlContainer container = page.getRedirectUrlContainer();
        String redirectUrl = container.getRedirectUrlField().getFieldValue();
        String redirectDisplayText = container.getRedirectDisplayTextField().getFieldValue();
        String redirectDescription = container.getRedirectDescriptionField().getFieldValue();
        RedirectLinkView redirectLinkView = new RedirectLinkView(redirectUrl, redirectDisplayText, redirectDescription); 

        String pageTitle = page.getPageTitleField().getFieldValue();
        String errorDescription = page.getErrorDescriptionField().getFieldValue();
        Boolean showRedirectLink = page.shouldShowRedirectLink().getFieldValue();

        return new Error404PageView(pageTitle, errorDescription, showRedirectLink, redirectLinkView);
    }
}
