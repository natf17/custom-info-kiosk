package com.ppublica.apps.kiosk.domain.model.pages;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class SimpleDescriptionField extends PageField<String> {

    @Id
    private Long id;

    @PersistenceCreator
    public SimpleDescriptionField(String fieldName, String fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }


}
