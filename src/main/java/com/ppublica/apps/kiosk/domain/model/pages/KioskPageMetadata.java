package com.ppublica.apps.kiosk.domain.model.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class KioskPageMetadata {

    private Long localeId;
    private PageStatus pageStatus;
    private LocalDate createdOn;
    private LocalDateTime lastModified;

    public KioskPageMetadata(Long localeId, PageStatus pageStatus, LocalDate createdOn, LocalDateTime lastModified) {
        this.localeId = localeId;
        this.pageStatus = pageStatus;
        this.createdOn = createdOn;
        this.lastModified = lastModified;
    }

    public PageInternals getPageInternals() {
        return new PageInternals(localeId, pageStatus, createdOn, lastModified);
    }

    public static KioskPageMetadata fromPageInternals(PageInternals pageInternals) {
        return new KioskPageMetadata(pageInternals.getKioskLocaleId(), pageInternals.getPageStatus(), 
                        pageInternals.getCreatedOn(), pageInternals.getLastModified());
        
    }
    
}
