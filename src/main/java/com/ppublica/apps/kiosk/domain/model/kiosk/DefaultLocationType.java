package com.ppublica.apps.kiosk.domain.model.kiosk;

public class DefaultLocationType extends KioskCollectionTypeBase implements LocationType {
    public static final CollectionType KIOSK_COLLECTION_TYPE_NAME = CollectionType.LOCATION;

    private Location locationInfo;

    protected DefaultLocationType(KioskCollectionType kioskCollectionType, Location locationInfo) {
        super(kioskCollectionType);
        this.locationInfo = locationInfo;
    }

    @Override
    public KioskCollectionField<String> fullName() {
        return this.locationInfo.fullName();
    }

    @Override
    public KioskCollectionField<Integer> level_num() {
        return this.locationInfo.level_num();
    }

    @Override
    public KioskCollectionField<String> level_name() {
        return this.locationInfo.level_name();
    }

    @Override
    public KioskCollectionField<KioskImage> map() {
        return this.locationInfo.map();
    }

    public static class Builder extends KioskCollectionTypeBase.Builder<Builder, DefaultLocationType> {

        protected KioskCollectionField<String> fullName;
        protected KioskCollectionField<Integer> level_num;
        protected KioskCollectionField<String> level_name;
        protected KioskCollectionField<KioskImage> map;
            
        public Builder fullName(KioskCollectionField<String> fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder level_num(KioskCollectionField<Integer> level_num) {
            this.level_num = level_num;
            return this;
        }

        public Builder level_name(KioskCollectionField<String> level_name) {
            this.level_name = level_name;
            return this;
        }

        public Builder map(KioskCollectionField<KioskImage> map) {
            this.map = map;
            return this;
        }

        public Builder() {
            super(KIOSK_COLLECTION_TYPE_NAME);
        }

        public Builder(CollectionType kioskCollectionTypeName) {
            super(kioskCollectionTypeName);
        }

        @Override
        protected void validateAndPrepareChild() { }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected DefaultLocationType buildChild(KioskCollectionType kioskCollectionType) {
            Location locationInfo = new DefaultLocationPiece.Builder()
                                                        .fullName(fullName)
                                                        .level_name(level_name)
                                                        .level_num(level_num)
                                                        .map(map)
                                                        .build();
            return new DefaultLocationType(kioskCollectionType, locationInfo);
        }

    }

}
