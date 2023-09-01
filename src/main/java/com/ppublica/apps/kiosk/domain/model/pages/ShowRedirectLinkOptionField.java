package com.ppublica.apps.kiosk.domain.model.pages;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class ShowRedirectLinkOptionField extends PageField<Boolean> {
    @Id
    private Long id;
    
    public ShowRedirectLinkOptionField(String fieldName) {
        super(fieldName);
    }

    // for use by repository classes ONLY
    @PersistenceCreator
    public ShowRedirectLinkOptionField(String fieldName, Boolean fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return false;
    }
}
