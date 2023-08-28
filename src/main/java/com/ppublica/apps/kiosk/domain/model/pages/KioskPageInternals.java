package com.ppublica.apps.kiosk.domain.model.pages;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.ppublica.apps.kiosk.domain.model.util.KioskLocale;

/*
 * The base class for all kiosk pages.
 * 
 * All pages in the kiosk are Locale-aware and contain
 * "fields". These fields are of two types: Locale-aware and 
 * Local-agnostic.
 */
public class KioskPageInternals {
    @Id
    private Long id;

    //private static final KioskLocale DEFAULT_KIOSK_LOCALE = KioskLocale.from("EN");
    //private KioskLocale locale = DEFAULT_KIOSK_LOCALE;
    private AggregateReference<KioskLocale, Long> locale;
    private PageMetadata metadata;
    
    public KioskPageInternals(PageMetadata metadata) {
        this.metadata = metadata;
    }

    // for use by repository classes ONLY
    @PersistenceCreator
    public KioskPageInternals(AggregateReference<KioskLocale, Long> locale, PageMetadata metadata) {
        this.locale = locale;
        this.metadata = metadata;
    }


    public AggregateReference<KioskLocale, Long> getLocale() {
        return this.locale;
    }
    
    public PageMetadata getMetadata() {
        return this.metadata;
    }



}
