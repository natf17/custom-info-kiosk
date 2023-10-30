package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.LinkedCollectionReference;

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

    public KioskCollectionField<Boolean> toBooleanField(BooleanField booleanField) {
        return new KioskCollectionField<Boolean>(booleanField.getFieldName(), booleanField.getFieldValue(), true);
    }

    public KioskCollectionField<LinkedCollectionReference> toLinkedCollectionReferenceField(LinkedCollectionField linkedCollectionField) {
        LinkedCollectionReference linkedCollRef = linkedCollectionField.getFieldValue() == null ? null : new LinkedCollectionReference(linkedCollectionField.getFieldValue());
        return new KioskCollectionField<LinkedCollectionReference>(linkedCollectionField.getFieldName(), linkedCollRef, true);
    }

    public CollectionTypeName toCollectionTypeName(String type) {
        return CollectionTypeName.valueOf(type);
    }
    
}
