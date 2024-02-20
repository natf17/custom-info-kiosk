package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class CollectionSharedInternals {
    private PageStatus status;
    private LocalDate createdOn;
    private LocalDateTime lastModified;


    public CollectionSharedInternals(PageStatus status, LocalDate createdOn, LocalDateTime lastModified) {
        this.status = status;
        this.createdOn = createdOn;
        this.lastModified = lastModified;
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
