package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public class LocationImpl extends KioskCollectionTypeImpl implements Location {

    private KioskCollectionField<String> levelName;
    private KioskCollectionField<Long> levelNum;
    private KioskCollectionField<String> fullName;
    private KioskCollectionField<Image> map;
    private static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.LOCATION;
    private static final String LEVELNAME_FIELD_NAME_DEFAULT = "Level_name";
    private static final String LEVELNUM_FIELD_NAME_DEFAULT = "Level_num";
    private static final String FULLNAME_FIELD_NAME_DEFAULT = "Fullname";
    private static final String MAP_FIELD_NAME_DEFAULT = "Map";


    private LocationImpl(CollectionTypeName type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata, 
                    KioskCollectionField<String> levelName, KioskCollectionField<Long> levelNum, KioskCollectionField<String> fullName, KioskCollectionField<Image> map) {
        super(type, collectionNameField, kioskCollectionMetadata);
        this.levelName = levelName;
        this.levelNum = levelNum;
        this.fullName = fullName;
        this.map = map;
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
        return this.map;
    }

    public static class Builder extends KioskCollectionTypeImpl.Builder<Builder, LocationImpl> {
        private KioskCollectionField<String> levelName;
        private KioskCollectionField<Long> levelNum;
        private KioskCollectionField<String> fullName;
        private KioskCollectionField<Image> map;

        public Builder() {
            super(KIOSK_COLLECTION_TYPE_NAME);
        }
        
        public Builder levelName(KioskCollectionField<String> editedLevelName) {
            this.levelName = editedLevelName;
            return self();
        }

        public Builder levelNum(KioskCollectionField<Long> editedLevelNum) {
            this.levelNum = editedLevelNum;
            return self();
        }

        public Builder fullName(KioskCollectionField<String> editedFullName) {
            this.fullName = editedFullName;
            return self();
        }

        public Builder map(KioskCollectionField<Image> editedMap) {
            this.map = editedMap;
            return self();
        }

        
        //private ImageContainerValidator imageContainerValidator;

        

        

        @Override
        protected void validateAndPrepareChild() {
        

            if(levelName == null) {   
                levelName = new KioskCollectionField<String>(LEVELNAME_FIELD_NAME_DEFAULT, null, true);
                //collectionNameField = editedCollectionNameField.toKioskCollectionField(COLLECTION_NAME_FIELD_TYPE, false);
            }
            
            if(levelNum == null) {   
                levelNum = new KioskCollectionField<Long>(LEVELNUM_FIELD_NAME_DEFAULT, null, true);
                //collectionNameField = editedCollectionNameField.toKioskCollectionField(COLLECTION_NAME_FIELD_TYPE, false);
            }

            if(fullName == null) {   
                fullName = new KioskCollectionField<String>(FULLNAME_FIELD_NAME_DEFAULT, null, true);
                //collectionNameField = editedCollectionNameField.toKioskCollectionField(COLLECTION_NAME_FIELD_TYPE, false);
            }

            if(map == null) {   
                map = new KioskCollectionField<Image>(MAP_FIELD_NAME_DEFAULT, null, true);
                //collectionNameField = editedCollectionNameField.toKioskCollectionField(COLLECTION_NAME_FIELD_TYPE, false);
            }

            /*
            // validate objects
            imageContainerValidator = new ImageContainerValidator(FEATURE_IMAGE_FIELD_TYPE, IMAGE_ALTERNATIVE_TEXT_FIELD_TYPE);
            
            if(!imageContainerValidator.isValid(imageContainer)) {
                throw new RuntimeException("Invalid field type fin the ImageContainer");
            }

            if (!KioskPageFieldTypeValidator.isValid(richDescriptionField, RICH_DESCRIPTION_TEXT_FIELD_TYPE)) {
                throw new RuntimeException("RichTextLongDescriptionField fieldType does not match");
            }

             */

        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected LocationImpl buildChild() {

            return new LocationImpl(super.type, super.collectionNameField, super.kioskCollectionMetadata, levelName, levelNum, fullName, map);
        }
    }


    
}
