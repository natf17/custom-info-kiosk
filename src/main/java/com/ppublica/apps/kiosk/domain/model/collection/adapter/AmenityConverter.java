package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.Amenity;
import com.ppublica.apps.kiosk.domain.model.collection.DefaultAmenity;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.LinkedCollectionReference;

public class AmenityConverter implements KioskAndCmsTypeConverter<Amenity> {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    private static final String FEATIMG_FIELD_TYPE = "FeatImg";
    private static final String SVGELEM_FIELD_TYPE = "svgElemId";
    private static final String ISWHEELCHAIRACC_FIELD_TYPE = "isWheelchairAccessible";
    private static final String NAME_FIELD_TYPE = "Name";
    private static final String NOTE_FIELD_TYPE = "Note";
    private static final String LOCATION_FIELD_TYPE = "Location";

    @Override
    public Amenity convert(SimpleCollectionType cmsRep) {
        List<ImageField> imageFields = cmsRep.getImageFields();
        List<NumericField> numericFields = cmsRep.getNumericFields();
        List<BooleanField> booleanFields = cmsRep.getBooleanFields();
        List<TextField> textFields = cmsRep.getTextFields();
        List<LinkedCollectionField> linkedCollFields = cmsRep.getLinkedCollectionFields();

        KioskCollectionField<Image> featImg = null;
        for(ImageField imageField : imageFields) {
            if(imageField.getFieldType().equals(FEATIMG_FIELD_TYPE)) {
                featImg = toKioskCollectionConverter.toImageField(imageField);
            }
        }

        KioskCollectionField<Long> svgElemId = null;
        for(NumericField numericField : numericFields) {
            if(numericField.getFieldType().equals(SVGELEM_FIELD_TYPE)) {
                svgElemId = toKioskCollectionConverter.toLongField(numericField);
            }
        }

        KioskCollectionField<Boolean> isWheelChairAccessible = null;
        for(BooleanField booleanField : booleanFields) {
            if(booleanField.getFieldType().equals(ISWHEELCHAIRACC_FIELD_TYPE)) {
                isWheelChairAccessible = toKioskCollectionConverter.toBooleanField(booleanField);
            }
        }

        KioskCollectionField<String> name = null;
        KioskCollectionField<String> note = null;

        for(TextField textField : textFields) {
            if(textField.getFieldType().equals(NAME_FIELD_TYPE)) {
                name = toKioskCollectionConverter.toStringField(textField);
            }

            if(textField.getFieldType().equals(NOTE_FIELD_TYPE)) {
                note = toKioskCollectionConverter.toStringField(textField);
            }
        }

        KioskCollectionField<LinkedCollectionReference> location = null;
        for(LinkedCollectionField linkedCollField : linkedCollFields) {
            if(linkedCollField.getFieldType().equals(LOCATION_FIELD_TYPE)) {
                location = toKioskCollectionConverter.toLinkedCollectionReferenceField(linkedCollField);
            }
        }

        return new DefaultAmenity.Builder()
                                    .featImg(featImg)
                                    .svgElemId(svgElemId)
                                    .isWheelChairAccessible(isWheelChairAccessible)
                                    .name(name)
                                    .note(note)
                                    .location(location)
                                    .build();
        
    }

    @Override
    public void transferKioskRep(SimpleCollectionTypeImpl.Builder builder, Amenity amenity) {
        builder.addImageField(toCmsCollectionConverter.toImageField(amenity.getFeatImg(), FEATIMG_FIELD_TYPE))
            .addNumericField(toCmsCollectionConverter.toNumericField(amenity.getSvgElemId(), SVGELEM_FIELD_TYPE))
            .addBooleanField(toCmsCollectionConverter.toBooleanField(amenity.isWheelChairAccessible(), ISWHEELCHAIRACC_FIELD_TYPE))
            .addTextField(toCmsCollectionConverter.toTextField(amenity.getName(), NAME_FIELD_TYPE))
            .addTextField(toCmsCollectionConverter.toTextField(amenity.getNote(), NOTE_FIELD_TYPE))
            .addLinkedCollectionField(toCmsCollectionConverter.toLinkedCollectionField(amenity.getLocation(), LOCATION_FIELD_TYPE));
        
    }

    
}
