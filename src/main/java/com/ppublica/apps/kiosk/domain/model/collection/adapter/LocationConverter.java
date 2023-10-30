package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.Location;

public class LocationConverter implements KioskAndCmsTypeConverter<Location>{
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    private static final String LEVELNAME_FIELD_TYPE = "Level_name";
    private static final String LEVELNUM_FIELD_TYPE = "Level_num";
    private static final String FULLNAME_FIELD_TYPE = "Fullname";
    private static final String MAP_FIELD_NAME_TYPE = "Map";

    @Override
    public Location convert(SimpleCollectionType cmsRep) {
        List<ImageField> imageFields = cmsRep.getImageFields();
        List<NumericField> numericFields = cmsRep.getNumericFields();
        List<TextField> textFields = cmsRep.getTextFields();

        KioskCollectionField<Image> mapField = null;
        for(ImageField imageField : imageFields) {
            if(imageField.getFieldType().equals(MAP_FIELD_NAME_TYPE)) {
                mapField = toKioskCollectionConverter.toImageField(imageField);
            }
        }

        KioskCollectionField<Long> levelNum = null;
        for(NumericField numericField : numericFields) {
            if(numericField.getFieldType().equals(LEVELNUM_FIELD_TYPE)) {
                levelNum = toKioskCollectionConverter.toLongField(numericField);
            }
        }

        KioskCollectionField<String> levelName = null;
        KioskCollectionField<String> fullName = null;

        for(TextField textField : textFields) {
            if(textField.getFieldType().equals(LEVELNAME_FIELD_TYPE)) {
                levelName = toKioskCollectionConverter.toStringField(textField);
            }

            if(textField.getFieldType().equals(FULLNAME_FIELD_TYPE)) {
                fullName = toKioskCollectionConverter.toStringField(textField);
            }
        }

        return new DefaultLocation(levelName, levelNum, fullName, mapField);
        
    }

    @Override
    public void transferKioskRep(SimpleCollectionTypeImpl.Builder builder, Location location) {
        builder.addTextField(toCmsCollectionConverter.toTextField(location.getLevelNameField(), LEVELNAME_FIELD_TYPE))
                .addNumericField(toCmsCollectionConverter.toNumericField(location.getLevelNumField(), LEVELNUM_FIELD_TYPE))
                .addTextField(toCmsCollectionConverter.toTextField(location.getFullNameField(), FULLNAME_FIELD_TYPE))
                .addImageField(toCmsCollectionConverter.toImageField(location.getMapField(), MAP_FIELD_NAME_TYPE));
    }

    class DefaultLocation implements Location {

        private KioskCollectionField<Image> mapField;
        private KioskCollectionField<Long> levelNum;
        private KioskCollectionField<String> levelName;
        private KioskCollectionField<String> fullName;

        protected DefaultLocation(KioskCollectionField<String> levelName, KioskCollectionField<Long> levelNum, KioskCollectionField<String> fullName,
                                KioskCollectionField<Image> mapField) {
            this.levelName = levelName;
            this.levelNum = levelNum;
            this.fullName = fullName;
            this.mapField = mapField;
        }

        @Override
        public KioskCollectionField<String> getLevelNameField() {
            return this.levelName;
        }

        @Override
        public KioskCollectionField<Long> getLevelNumField() {
            return this.levelNum;
        }

        @Override
        public KioskCollectionField<String> getFullNameField() {
            return this.fullName;
        }

        @Override
        public KioskCollectionField<Image> getMapField() {
            return this.mapField;
        }
        
    }
}
