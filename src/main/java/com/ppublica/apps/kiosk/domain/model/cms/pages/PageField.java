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
    private final String fieldType;
    private final String fieldName;
    private final T fieldValue;

    public PageField(String fieldType, String fieldName) {
        this(fieldType, fieldName, null);
    }

    public PageField(String fieldType, String fieldName, T fieldValue) {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
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

    /*
     * Return true if the field value is Locale-dependent.
     */
    public abstract boolean isLocalized();
    
}
