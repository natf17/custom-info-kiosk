package com.ppublica.apps.kiosk.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ppublica.apps.kiosk.domain.model.pages.AboutPage;
import com.ppublica.apps.kiosk.domain.model.util.KioskLocale;

public interface AboutPageRepository extends CrudRepository<AboutPage, Long>{

    /*
    @Query("SELECT about_page.id " + 
            "FROM about_page " + 
            "INNER JOIN kiosk_page_internals ON about_page.id = kiosk_page_internals.about_page " + 
            "INNER JOIN kiosk_locale ON kiosk_page_internals.id = kiosk_locale.kiosk_page_internals " +
            "WHERE kiosk_locale.abbrev = :locale")
    public List<Long> getAboutPageIdsforLocale(@Param("locale") String abbrev);
    */

      @Query("SELECT about_page.id " + 
            "FROM about_page " + 
            "INNER JOIN kiosk_page_internals ON about_page.id = kiosk_page_internals.about_page " + 
            "INNER JOIN kiosk_locale ON kiosk_page_internals.locale = kiosk_locale.id " +
            "WHERE kiosk_locale.abbrev = :locale")
    public List<Long> getAboutPageIdsforLocale(@Param("locale") String abbrev);




    
}