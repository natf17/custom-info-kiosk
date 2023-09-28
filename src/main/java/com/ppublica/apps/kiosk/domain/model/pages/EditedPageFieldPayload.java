package com.ppublica.apps.kiosk.domain.model.pages;

public class EditedPageFieldPayload<T> {

    private final String fieldName;
    private final T fieldValue;

    public EditedPageFieldPayload(String fieldName, T fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getPageFieldName() {
        return this.fieldName;
    }
    public T getPageFieldValue() {
        return this.fieldValue;
    }

    public KioskPageField<T> toKioskPageField(String fieldType, boolean isLocalizable) {
        return new KioskPageField<T>(fieldType, fieldName, fieldValue, isLocalizable);
    }
    
}
