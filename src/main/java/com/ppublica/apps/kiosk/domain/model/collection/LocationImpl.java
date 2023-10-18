package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public class LocationImpl extends KioskCollectionTypeImpl implements Location {

    private KioskCollectionField<String> levelName;
    private KioskCollectionField<Long> levelNum;
    private KioskCollectionField<String> fullName;
    private KioskCollectionField<Image> map;
    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.LOCATION;
    private static final String LEVELNAME_FIELD_NAME_DEFAULT = "Level_name";
    private static final String LEVELNUM_FIELD_NAME_DEFAULT = "Level_num";
    private static final String FULLNAME_FIELD_NAME_DEFAULT = "Fullname";
    private static final String MAP_FIELD_NAME_DEFAULT = "Map";


    private LocationImpl(CollectionTypeName type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata, Long id,
                    KioskCollectionField<String> levelName, KioskCollectionField<Long> levelNum, KioskCollectionField<String> fullName, KioskCollectionField<Image> map) {
        super(type, collectionNameField, kioskCollectionMetadata, id);
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
            }
            
            if(levelNum == null) {   
                levelNum = new KioskCollectionField<Long>(LEVELNUM_FIELD_NAME_DEFAULT, null, true);
            }

            if(fullName == null) {   
                fullName = new KioskCollectionField<String>(FULLNAME_FIELD_NAME_DEFAULT, null, true);
            }

            if(map == null) {   
                map = new KioskCollectionField<Image>(MAP_FIELD_NAME_DEFAULT, null, true);
            }

        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected LocationImpl buildChild() {

            return new LocationImpl(super.type, super.collectionNameField, super.kioskCollectionMetadata, super.id, levelName, levelNum, fullName, map);
        }
    }


    
}
