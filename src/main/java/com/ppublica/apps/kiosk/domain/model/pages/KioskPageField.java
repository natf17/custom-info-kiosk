package com.ppublica.apps.kiosk.domain.model.pages;

/*
 * 
 * 
 */
public class KioskPageField<T> {
    private String fieldType;
    private String fieldName;
    private T fieldValue;
    private boolean isLocalized;

    public KioskPageField(String fieldType, String fieldName, boolean isLocalized) {
        this(fieldType, fieldName, null, isLocalized);
    }

    public KioskPageField(String fieldType, String fieldName, T fieldValue, boolean isLocalized) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.isLocalized = isLocalized;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public String getFieldName() {
        return this.fieldName;
    }
    
    public T getFieldValue() {
        return this.fieldValue;
    }

    public boolean isLocalized() {
        return this.isLocalized;
    }

    
}
