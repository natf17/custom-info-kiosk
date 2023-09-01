package com.ppublica.apps.kiosk.domain.model.pages;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class ErrorDescriptionField extends PageField<String> {

    @Id
    private Long id;
    
    public ErrorDescriptionField(String fieldName) {
        super(fieldName);
    }

    // for use by repository classes ONLY
    @PersistenceCreator
    public ErrorDescriptionField(String fieldName, String fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }
    
}
