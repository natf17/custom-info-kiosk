package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.locale.KioskLocale;

public class LocalizedFields {
    private KioskLocale locale;
    private CollectionNameField collectionNameField;
    private List<TextField> textFields;
    private List<NumericField> numericFields;
    private List<BooleanField> booleanFields;

    public LocalizedFields(KioskLocale locale, CollectionNameField collectionNameField, List<TextField> textFields, List<NumericField> numericFields, List<BooleanField> booleanFields) {
        this.locale = locale;
        this.collectionNameField = collectionNameField;
        this.textFields = textFields;
        this.numericFields = numericFields;
        this.booleanFields = booleanFields;
    }

    public KioskLocale getLocale() {
        return this.locale;
    }

    public CollectionNameField getCollectionNameField() {
        return this.collectionNameField;
    }

    public List<TextField> getTextFields() {
        return this.textFields;
    }

    public List<NumericField> getNumericFields() {
        return this.numericFields;
    }

    public List<BooleanField> getBooleanFields() {
        return this.booleanFields;
    }

}
