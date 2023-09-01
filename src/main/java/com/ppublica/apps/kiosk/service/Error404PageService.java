package com.ppublica.apps.kiosk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.pages.Error404Page;
import com.ppublica.apps.kiosk.repository.Error404PageRepository;
import com.ppublica.apps.kiosk.service.views.error.Error404PageView;

public class Error404PageService {

    @Autowired
    private Error404PageRepository repo;


    public Optional<Error404PageView> getError404Page(String locale) {
        //repo.getAboutPageIdsForLocale();
        return null;
    }
    
    private Error404PageView transformToView(Error404Page page) {
        return null;
    }
}
