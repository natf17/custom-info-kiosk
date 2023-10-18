package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;

public class ToCmsCollectionConverter {
    public CollectionNameField toCollectionNameField(KioskCollectionField<String> collectionNameField) {
        return new CollectionNameField(collectionNameField.getFieldName(), collectionNameField.getFieldValue());
    }

    public TextField toTextField(KioskCollectionField<String> field, String fieldType) {
        return new TextField(fieldType, field.getFieldName(), field.getFieldValue());
    }

    public NumericField toNumericField(KioskCollectionField<Long> field, String fieldType) {
        return new NumericField(fieldType, field.getFieldName(), field.getFieldValue());
    }

    public ImageField toImageField(KioskCollectionField<Image> field, String fieldType) {
        return new ImageField(fieldType, field.getFieldName(), field.getFieldValue());
    }

}
