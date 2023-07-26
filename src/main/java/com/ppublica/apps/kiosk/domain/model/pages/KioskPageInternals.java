package com.ppublica.apps.kiosk.domain.model.pages;

/*
 * The base class for all kiosk pages.
 * 
 * All pages in the kiosk are Locale-aware and contain
 * "fields". These fields are of two types: Locale-aware and 
 * Local-agnostic.
 */
public class KioskPageInternals {
    private static final KioskLocale DEFAULT_KIOSK_LOCALE = KioskLocale.EN;
    private KioskLocale locale = DEFAULT_KIOSK_LOCALE;
    private PageMetadata metadata;
    
    public KioskPageInternals(PageMetadata metadata) {
        this.metadata = metadata;
    }
    public KioskPageInternals(KioskLocale locale, PageMetadata metadata) {
        this.locale = locale;
        this.metadata = metadata;
    }


    public KioskLocale getLocale() {
        return this.locale;
    }
    
    public PageMetadata getMetadata() {
        return this.metadata;
    }



}
