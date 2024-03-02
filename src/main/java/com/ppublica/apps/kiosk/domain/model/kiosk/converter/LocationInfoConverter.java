package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultLocationPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.Location;

public class LocationInfoConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    // localized fields
    public static final String LEVELNAME_FIELD_TYPE = "Level_name";
    public static final String FULLNAME_FIELD_TYPE = "Fullname";
    public static final String MAP_FIELD_NAME_TYPE = "Map";

    // non-localized fields
    public static final String LEVELNUM_FIELD_TYPE = "Level_num";


    public Location convert(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {
        List<ImageField> locImageFields = localizedCmsPiece.locImageFields();
        List<TextField> locTextFields = localizedCmsPiece.locTextFields();


        List<NumericField> sharedNumericFields = sharedCmsPiece.numericFields();

        KioskCollectionField<Integer> level_num = null;
        for(NumericField numericField : sharedNumericFields) {
            if(numericField.getFieldType().equals(LEVELNUM_FIELD_TYPE)) {
                level_num = toKioskCollectionConverter.toIntField(numericField, false);
            }
        }

        KioskCollectionField<KioskImage> map = null;
        for(ImageField imageField : locImageFields) {
            if(imageField.getFieldType().equals(MAP_FIELD_NAME_TYPE)) {
                map = toKioskCollectionConverter.toImageField(imageField);
            }
        }

        KioskCollectionField<String> fullName = null;
        KioskCollectionField<String> level_name = null;

        for(TextField textField : locTextFields) {
            if(textField.getFieldType().equals(FULLNAME_FIELD_TYPE)) {
                fullName = toKioskCollectionConverter.toStringField(textField);
            }

            if(textField.getFieldType().equals(LEVELNAME_FIELD_TYPE)) {
                level_name = toKioskCollectionConverter.toStringField(textField);
            }

        }


        return new DefaultLocationPiece.Builder()
                                    .level_name(level_name)
                                    .level_num(level_num)
                                    .fullName(fullName)
                                    .map(map)
                                    .build();
        
    }

    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder, Location locationInfo) {
        localizedCmsBuilder
            .addTextField(toCmsCollectionConverter.toTextField(locationInfo.fullName(), FULLNAME_FIELD_TYPE))
            .addTextField(toCmsCollectionConverter.toTextField(locationInfo.level_name(), LEVELNAME_FIELD_TYPE))
            .addImageField(toCmsCollectionConverter.toImageField(locationInfo.map(), MAP_FIELD_NAME_TYPE));

            // ugly - should be improved
            KioskCollectionField<Integer> levelNum = locationInfo.level_num();
            Long value = levelNum.fieldValue() != null ? Long.valueOf(levelNum.fieldValue()) : null;
            KioskCollectionField<Long> levelNum_Long = new KioskCollectionField<Long>(value, levelNum.isLocalized());


        sharedCmsBuilder.addNumericField(toCmsCollectionConverter.toNumericField(levelNum_Long, LEVELNUM_FIELD_TYPE));
        
    }
}
