package com.ppublica.apps.kiosk.domain.model.cms.pages;

/*
 * Represents a single user-provided unit that exists inside a FieldContainer, 
 * (e.g. an image, a text field) with a single editable field value.
 *
 * For page entities that do not represent a field value (e.g. a container
 * or list of other PageFields), see PageFieldContainer. 
 * 
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

    public String getFieldName() {
        return this.fieldName;
    }
    
    public T getFieldValue() {
        return this.fieldValue;
    }

    /*
     * Return true if the field value is Locale-dependent. It does not depend on
     * whether any sub fields are Locale-dependent.
     */
    public abstract boolean isLocalized();
    
}
