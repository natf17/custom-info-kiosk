package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;


public class ToKioskCollectionConverter {
    public KioskCollectionField<String> toStringField(CollectionNameField collNameField) {
        return new KioskCollectionField<String>(collNameField.getFieldValue(), true);
    }

    public KioskCollectionField<KioskImage> toImageField(ImageField imageField) {
        return new KioskCollectionField<KioskImage>(toKioskImage(imageField.getFieldValue()), true);
    }

    public KioskCollectionField<String> toStringField(TextField textField) {
        return new KioskCollectionField<String>(textField.getFieldValue(), true);
    }

    public KioskCollectionField<Long> toLongField(NumericField numericField) {
        return new KioskCollectionField<Long>(numericField.getFieldValue(), true);
    }

    public KioskCollectionField<Boolean> toBooleanField(BooleanField booleanField) {
        return new KioskCollectionField<Boolean>(booleanField.getFieldValue(), true);
    }

    // the linkedCollectionField in the cms might be null
    public KioskCollectionField<LinkedCollectionReference> toLinkedCollectionReferenceField(LinkedCollectionField linkedCollectionField) {
        if (linkedCollectionField == null) {
            return new KioskCollectionField<LinkedCollectionReference>(new LinkedCollectionReference(null), true);
        }

        LinkedCollectionReference linkedCollRef = linkedCollectionField.getFieldValue() == null ? null : new LinkedCollectionReference(linkedCollectionField.getFieldValue());
        return new KioskCollectionField<LinkedCollectionReference>(linkedCollRef, true);
    }

    public CollectionType toCollectionTypeName(String type) {
        return CollectionType.valueOf(type);
    }

    public KioskImage toKioskImage(Image image) {
        return new KioskImage(image.location(), image.width(), image.height());
    }
}
