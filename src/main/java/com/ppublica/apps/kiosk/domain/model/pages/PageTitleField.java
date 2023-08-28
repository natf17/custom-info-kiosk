package com.ppublica.apps.kiosk.domain.model.pages;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

/*
 * Represents a page title field on a page.
 */
public class PageTitleField extends PageField<String>{
    @Id
    private Long id;
    
    public PageTitleField(String fieldName) {
        super (fieldName);
    }

    @PersistenceCreator
    public PageTitleField(String fieldName, String fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
    
}
