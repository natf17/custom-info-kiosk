package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class CollectionInternals {
    private Long kioskLocaleId;
    private String kioskLocale;
    private PageStatus status;
    private LocalDate createdOn;
    private LocalDateTime lastModified;


    public CollectionInternals(Long kioskLocaleId, String kioskLocale, PageStatus status, 
                            LocalDate createdOn, LocalDateTime lastModified) {
        this.kioskLocaleId = kioskLocaleId;
        this.kioskLocale = kioskLocale;
        this.status = status;
        this.createdOn = createdOn;
        this.lastModified = lastModified;
    }

    public Long getKioskLocaleId() {
        return this.kioskLocaleId;
    }

    public String getKioskLocale() {
        return this.kioskLocale;
    }

    public PageStatus getStatus() {
        return this.status;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public LocalDateTime getLastModified() {
        return this.lastModified;
    }
}
