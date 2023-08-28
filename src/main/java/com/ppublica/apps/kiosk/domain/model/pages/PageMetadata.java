package com.ppublica.apps.kiosk.domain.model.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class PageMetadata {
    @Id
    private Long id;
    
    private PageStatus pageStatus;
    private LocalDate createdOn;
    private LocalDateTime lastModified;

    // to be called by enclosing page and factory method
    @PersistenceCreator
    PageMetadata(PageStatus pageStatus, LocalDate createdOn, LocalDateTime lastModified) {
        this.pageStatus = pageStatus;
        this.createdOn = createdOn;
        this.lastModified = lastModified;
    }

    static PageMetadata newPage() {
        return new PageMetadata(PageStatus.DRAFT, LocalDate.now(), LocalDateTime.now());
    }

    static PageMetadata existingPage(PageStatus pageStatus, LocalDate dateCreated, LocalDateTime lastModified) {
        return new PageMetadata(pageStatus, dateCreated, lastModified);
    }

}
