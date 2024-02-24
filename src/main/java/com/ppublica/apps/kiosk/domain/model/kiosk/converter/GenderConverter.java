package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.kiosk.GenderInfo;

public class GenderConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    private static final String GENDER_FIELD_TYPE = "Gender";

    public GenderInfo convert(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {
        // extract gender
        List<TextField> textFields = sharedCmsPiece.textFields();


        for (TextField textField : textFields) {
            if(textField.getFieldType().equals(GENDER_FIELD_TYPE)) {
                return new GenderInfo(toKioskCollectionConverter.toStringField(textField));
            }
        }

        throw new RuntimeException("No Gender field found");
    }

    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder, GenderInfo genderInfo) {
        sharedCmsBuilder.addTextField(toCmsCollectionConverter.toTextField(genderInfo.gender(), GENDER_FIELD_TYPE));
    }


}
