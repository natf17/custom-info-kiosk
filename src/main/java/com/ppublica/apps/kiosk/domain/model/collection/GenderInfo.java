package com.ppublica.apps.kiosk.domain.model.collection;


public class GenderInfo implements GenderAware {
    
    private KioskCollectionField<String> genderField;

    private static final String GENDER_FIELD_NAME_DEFAULT = "Gender";

    protected GenderInfo(KioskCollectionField<String> genderField) {
        this.genderField = genderField;
    }

    public GenderInfo() {
        this(new KioskCollectionField<String>(GENDER_FIELD_NAME_DEFAULT, null, true));
    }

    public KioskCollectionField<String> getGender() {
        return genderField;
    }
    
}
