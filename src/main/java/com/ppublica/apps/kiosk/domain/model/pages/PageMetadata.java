package com.ppublica.apps.kiosk.domain.model.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PageMetadata {
    private PageStatus pageStatus;
    private LocalDate createdOn;
    private LocalDateTime lastModified;

    // to be called by enclosing page and factory method
    PageMetadata(PageStatus pageStatus, LocalDate dateCreated, LocalDateTime lastModified) {
        this.pageStatus = pageStatus;
        this.createdOn = dateCreated;
        this.lastModified = lastModified;
    }

    static PageMetadata newPage() {
        return new PageMetadata(PageStatus.DRAFT, LocalDate.now(), LocalDateTime.now());
    }

    static PageMetadata existingPage(PageStatus pageStatus, LocalDate dateCreated, LocalDateTime lastModified) {
        return new PageMetadata(pageStatus, dateCreated, lastModified);
    }

}
