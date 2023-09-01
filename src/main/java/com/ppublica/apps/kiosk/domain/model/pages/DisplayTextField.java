package com.ppublica.apps.kiosk.domain.model.pages;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class DisplayTextField extends PageField<String> {

    @Id
    private Long id;

    @PersistenceCreator
    public DisplayTextField(String fieldName, String fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public boolean isLocalized() {
        return true;
    }


}