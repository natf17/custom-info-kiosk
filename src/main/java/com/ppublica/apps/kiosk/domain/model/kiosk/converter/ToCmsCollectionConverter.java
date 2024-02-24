package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;

public class ToCmsCollectionConverter {
    public CollectionNameField toCollectionNameField(KioskCollectionField<String> collectionNameField) {
        return new CollectionNameField(collectionNameField.fieldValue(), collectionNameField.fieldValue());
    }

    public TextField toTextField(KioskCollectionField<String> field, String fieldType) {
        return new TextField(fieldType, fieldType, field.fieldValue());
    }

    public NumericField toNumericField(KioskCollectionField<Long> field, String fieldType) {
        return new NumericField(fieldType, fieldType, field.fieldValue());
    }

    public ImageField toImageField(KioskCollectionField<KioskImage> field, String fieldType) {
        return new ImageField(fieldType, fieldType, toCmsImage(field.fieldValue()));
    }

    public BooleanField toBooleanField(KioskCollectionField<Boolean> field, String fieldType) {
        return new BooleanField(fieldType, fieldType, field.fieldValue());
    }

    public LinkedCollectionField toLinkedCollectionField(KioskCollectionField<LinkedCollectionReference> field, String fieldType) {
        return new LinkedCollectionField(fieldType, fieldType, field.fieldValue().linkedCollectionId());
    }

    public String toType(CollectionType typeName) {
        return typeName.toString();
    }

    private Image toCmsImage(KioskImage image) {
        return new Image(image.location(), image.width(), image.height());
    }
}
