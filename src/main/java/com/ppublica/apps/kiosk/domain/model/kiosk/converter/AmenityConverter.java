package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.kiosk.Amenity;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultAmenityPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;

public class AmenityConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    // localized fields
    public static final String NAME_FIELD_TYPE = "Name";
    public static final String SVGELEM_FIELD_TYPE = "svgElemId";
    public static final String NOTE_FIELD_TYPE = "Note";
    public static final String FEATIMG_FIELD_TYPE = "FeatImg";

    // non-localized fields
    public static final String ISWHEELCHAIRACC_FIELD_TYPE = "isWheelchairAccessible";
    public static final String LOCATION_FIELD_TYPE = "Location";

    public Amenity convert(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {
        List<ImageField> locImageFields = localizedCmsPiece.locImageFields();
        List<TextField> locTextFields = localizedCmsPiece.locTextFields();


        List<LinkedCollectionField> sharedLinkedCollFields = sharedCmsPiece.linkedCollectionFields();
        List<BooleanField> sharedBooleanFields = sharedCmsPiece.booleanFields();


        KioskCollectionField<KioskImage> featImg = null;
        for(ImageField imageField : locImageFields) {
            if(imageField.getFieldType().equals(FEATIMG_FIELD_TYPE)) {
                featImg = toKioskCollectionConverter.toImageField(imageField);
            }
        }


        KioskCollectionField<Boolean> isWheelChairAccessible = null;
        for(BooleanField booleanField : sharedBooleanFields) {
            if(booleanField.getFieldType().equals(ISWHEELCHAIRACC_FIELD_TYPE)) {
                isWheelChairAccessible = toKioskCollectionConverter.toBooleanField(booleanField);
            }
        }

        KioskCollectionField<String> name = null;
        KioskCollectionField<String> note = null;
        KioskCollectionField<String> svgElemId = null;


        for(TextField textField : locTextFields) {
            if(textField.getFieldType().equals(NAME_FIELD_TYPE)) {
                name = toKioskCollectionConverter.toStringField(textField);
            }

            if(textField.getFieldType().equals(NOTE_FIELD_TYPE)) {
                note = toKioskCollectionConverter.toStringField(textField);
            }

            if(textField.getFieldType().equals(SVGELEM_FIELD_TYPE)) {
                svgElemId = toKioskCollectionConverter.toStringField(textField);
            }
        }

        KioskCollectionField<LinkedCollectionReference> location = null;
        for(LinkedCollectionField linkedCollField : sharedLinkedCollFields) {
            if(linkedCollField.getFieldType().equals(LOCATION_FIELD_TYPE)) {
                location = toKioskCollectionConverter.toLinkedCollectionReferenceField(linkedCollField);
            }
        }

        return new DefaultAmenityPiece.Builder()
                                    .featImg(featImg)
                                    .svgElemId(svgElemId)
                                    .isWheelChairAccessible(isWheelChairAccessible)
                                    .name(name)
                                    .note(note)
                                    .location(location)
                                    .build();
        
    }

    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder, Amenity amenity) {
        localizedCmsBuilder.addImageField(toCmsCollectionConverter.toImageField(amenity.featImg(), FEATIMG_FIELD_TYPE))
            .addTextField(toCmsCollectionConverter.toTextField(amenity.svgElemId(), SVGELEM_FIELD_TYPE))
            .addTextField(toCmsCollectionConverter.toTextField(amenity.name(), NAME_FIELD_TYPE))
            .addTextField(toCmsCollectionConverter.toTextField(amenity.note(), NOTE_FIELD_TYPE));

        sharedCmsBuilder.addBooleanField(toCmsCollectionConverter.toBooleanField(amenity.isWheelChairAccessible(), ISWHEELCHAIRACC_FIELD_TYPE))
            .addLinkedCollectionField(toCmsCollectionConverter.toLinkedCollectionField(amenity.location(), LOCATION_FIELD_TYPE));
        
    }

}
