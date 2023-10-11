package com.ppublica.apps.kiosk.domain.model.collection;

public class KioskCollectionField<T> {

    private String fieldName;
    private T fieldValue;
    private boolean isLocalized;

    public KioskCollectionField(String fieldName, boolean isLocalized) {
        this(fieldName, null, isLocalized);
    }

    public KioskCollectionField(String fieldName, T fieldValue, boolean isLocalized) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.isLocalized = isLocalized;
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

    /*
    public KioskCollectionField<T> toKioskCollectionField(String fieldType, boolean isLocalizable) {
        return new KioskCollectionField<T>(fieldType, fieldName, fieldValue, isLocalizable);
    } */


    /*
    private String fieldType;
    private String fieldName;
    private T fieldValue;
    private boolean isLocalized;

    public KioskCollectionField(String fieldType, String fieldName, boolean isLocalized) {
        this(fieldType, fieldName, null, isLocalized);
    }

    public KioskCollectionField(String fieldType, String fieldName, T fieldValue, boolean isLocalized) {
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
    } */

}
