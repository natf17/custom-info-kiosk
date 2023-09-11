package com.ppublica.apps.kiosk.domain.model.cms.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.PersistenceCreator;

public class PageInternals {
    private Long kioskLocaleId;
    private PageStatus pageStatus;
    private LocalDate createdOn;
    private LocalDateTime lastModified;


    @PersistenceCreator
    public PageInternals(Long kioskLocaleId, PageStatus pageStatus, 
                            LocalDate createdOn, LocalDateTime lastModified) {
        this.kioskLocaleId = kioskLocaleId;
        this.pageStatus = pageStatus;
        this.createdOn = createdOn;
        this.lastModified = lastModified;
    }

    public Long getKioskLocaleId() {
        return this.kioskLocaleId;
    }

    public PageStatus getPageStatus() {
        return this.pageStatus;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public LocalDateTime getLastModified() {
        return this.lastModified;
    }

}
