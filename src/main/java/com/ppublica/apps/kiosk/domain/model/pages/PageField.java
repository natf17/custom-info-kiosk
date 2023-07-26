package com.ppublica.apps.kiosk.domain.model.pages;

/*
 * Represents a user-provided entity that exists inside a page, 
 * such as an image, a text field, a label, ....
 */
public abstract class PageField<T> {
    private String fieldName;
    private T fieldValue;

    public PageField(String fieldName) {
        this(fieldName, null);
    }

    public PageField(String fieldName, T fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    private String getFieldName() {
        return this.fieldName;
    }
    
    private T getFieldValue() {
        return this.fieldValue;
    }

    /*
     * Return true if the field value is Locale-dependent. It does not depend on
     * whether any sub fields are Locale-dependent.
     */
    public abstract boolean isLocalized();
    
}
