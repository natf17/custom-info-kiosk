package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.collection.GenderAware;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;

public class GenderConverter implements KioskAndCmsTypeConverter<GenderAware> {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    private static final String GENDER_FIELD_TYPE = "Gender";

    public GenderInfo convert(SimpleCollectionType cmsRep) {
        // extract gender
        List<TextField> textFields = cmsRep.getTextFields();


        for (TextField textField : textFields) {
            if(textField.getFieldType().equals(GENDER_FIELD_TYPE)) {
                return new GenderInfo(toKioskCollectionConverter.toStringField(textField));
            }
        }

        throw new RuntimeException("No Gender field found");
        
    }

    public void transferKioskRep(SimpleCollectionTypeImpl.Builder builder, GenderAware genderAwareCollection) {
        builder.addTextField(toCmsCollectionConverter.toTextField(genderAwareCollection.getGender(), GENDER_FIELD_TYPE));
    }

    class GenderInfo implements GenderAware {
        private final KioskCollectionField<String> gender;
        public GenderInfo(KioskCollectionField<String> gender) {
            this.gender = gender;
        }

        @Override
        public KioskCollectionField<String> getGender() {
            return this.gender;
        }

    }


}
