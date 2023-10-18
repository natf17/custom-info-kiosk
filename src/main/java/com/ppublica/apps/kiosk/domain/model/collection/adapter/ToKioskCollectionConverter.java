package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;

public class ToKioskCollectionConverter {

    public KioskCollectionField<String> toStringField(CollectionNameField collNameField) {
        return new KioskCollectionField<String>(collNameField.getFieldName(), collNameField.getFieldValue(), true);
    }

    public KioskCollectionField<Image> toImageField(ImageField imageField) {
        return new KioskCollectionField<Image>(imageField.getFieldName(), imageField.getFieldValue(), true);
    }

    public KioskCollectionField<String> toStringField(TextField textField) {
        return new KioskCollectionField<String>(textField.getFieldName(), textField.getFieldValue(), true);
    }

    public KioskCollectionField<Long> toLongField(NumericField numericField) {
        return new KioskCollectionField<Long>(numericField.getFieldName(), numericField.getFieldValue(), true);
    }
    
}
