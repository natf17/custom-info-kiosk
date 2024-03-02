package com.ppublica.apps.kiosk.domain.model.kiosk;

public record DefaultLocationPiece(KioskCollectionField<String> fullName, KioskCollectionField<Integer> level_num, KioskCollectionField<String> level_name, KioskCollectionField<KioskImage> map) implements Location {

    public static final CollectionType KIOSK_COLLECTION_TYPE_NAME = CollectionType.LOCATION;    

    public static class Builder {
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


        public Location build() {
            validateAndPrepare();

            return new DefaultLocationPiece(fullName, level_num, level_name, map);
        }
            
        protected void validateAndPrepare() {
    
            if(fullName == null) {   
                fullName = new KioskCollectionField<String>(null, true);
            }
                
            if(level_num == null) {   
                level_num = new KioskCollectionField<Integer>(null, false);
            }

            if(level_name == null) {   
                level_name = new KioskCollectionField<String>(null, true);
            }

            if(map == null) {   
                map = new KioskCollectionField<KioskImage>(null, true);
            }

        }
    }
    
}
