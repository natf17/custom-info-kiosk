package com.ppublica.apps.kiosk.repository;

import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.domain.model.pages.KioskLocale;

public interface AboutPageRepository {

    AboutPage getAboutPage(KioskLocale locale);
    
}
