package com.ppublica.apps.kiosk.repository;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;

public interface PageRepository {

    public Page save(Page page);

    public Page findByPageTypeAndKioskLocale(String pageType, String localeAbbrev);

    
}
