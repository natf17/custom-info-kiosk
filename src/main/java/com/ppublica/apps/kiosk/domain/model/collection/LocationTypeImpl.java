package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public class LocationTypeImpl extends KioskCollectionTypeImpl implements LocationType {
    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.LOCATION;

    private Location location;

    public LocationTypeImpl(KioskCollectionType kioskCollectionType, Location location) {
        super(kioskCollectionType);
        this.location =  location;
    }

    @Override
    public KioskCollectionField<String> getLevelNameField() {
        return this.location.getLevelNameField();
    }

    @Override
    public KioskCollectionField<Long> getLevelNumField() {
        return this.location.getLevelNumField();
    }

    @Override
    public KioskCollectionField<String> getFullNameField() {
        return this.location.getFullNameField();
    }

    @Override
    public KioskCollectionField<Image> getMapField() {
        return this.location.getMapField();
    }

    /*
     * This builder delegates to the DefaultLocation.Builder for location fields, or the superclass for KioskCollectionType fields.
     */
    public static class Builder extends KioskCollectionTypeImpl.Builder<Builder, LocationTypeImpl> {
        private KioskCollectionField<String> levelName;
        private KioskCollectionField<Long> levelNum;
        private KioskCollectionField<String> fullName;
        private KioskCollectionField<Image> map;

        private DefaultLocation.Builder locationBuilder = new DefaultLocation.Builder();

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

        
        // any validation in addition to that provided by the underlying amenity builder...
        @Override
        protected void validateAndPrepareChild() { }
        


        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected LocationTypeImpl buildChild(KioskCollectionType kioskCollectionType) {
            Location location = locationBuilder
                                    .levelName(levelName)
                                    .levelNum(levelNum)
                                    .fullName(fullName)
                                    .map(map)
                                    .build();

            return new LocationTypeImpl(kioskCollectionType, location);
        }
        
    }

}
