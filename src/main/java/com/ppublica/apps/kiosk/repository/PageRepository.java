package com.ppublica.apps.kiosk.repository;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;

public interface PageRepository {

    public Page save(Page page);

    public Page findByPageTypeAndKioskLocale(String pageType, String localeAbbrev);

    public void deletePageWithLocale(String pageType, String localeAbbrev);

    public boolean pageExists(String pageType, String localeAbbrev);

    public Page update(String pageType, String localeAbbrev, Page page);

}