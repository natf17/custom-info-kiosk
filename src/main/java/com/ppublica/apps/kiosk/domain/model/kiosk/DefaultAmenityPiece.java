package com.ppublica.apps.kiosk.domain.model.kiosk;

public record DefaultAmenityPiece(KioskCollectionField<KioskImage> featImg, KioskCollectionField<Long> svgElemId, KioskCollectionField<Boolean> isWheelChairAccessible,
KioskCollectionField<String> name, KioskCollectionField<String> note, KioskCollectionField<LinkedCollectionReference> location) implements Amenity {


    public static final CollectionType KIOSK_COLLECTION_TYPE_NAME = CollectionType.AMENITY;    

    public static class Builder {
        protected KioskCollectionField<KioskImage> featImg;
        protected KioskCollectionField<Long> svgElemId;
        protected KioskCollectionField<Boolean> isWheelChairAccessible;
        protected KioskCollectionField<String> name;
        protected KioskCollectionField<String> note;
        protected KioskCollectionField<LinkedCollectionReference> location;
            
        public Builder featImg(KioskCollectionField<KioskImage> editedFeatImg) {
            this.featImg = editedFeatImg;
            return this;
        }

        public Builder svgElemId(KioskCollectionField<Long> editedSvgElemId) {
            this.svgElemId = editedSvgElemId;
            return this;
        }

        public Builder isWheelChairAccessible(KioskCollectionField<Boolean> editedIsWheelChairAccessible) {
            this.isWheelChairAccessible = editedIsWheelChairAccessible;
            return this;
        }

        public Builder name(KioskCollectionField<String> editedName) {
            this.name = editedName;
            return this;
        }

        public Builder note(KioskCollectionField<String> editedNote) {
            this.note = editedNote;
            return this;
        }

        public Builder location(KioskCollectionField<LinkedCollectionReference> location) {
            this.location = location;
            return this;
        }

        public Amenity build() {
            validateAndPrepare();

            return new DefaultAmenityPiece(featImg, svgElemId, isWheelChairAccessible, name, note, location);
        }
            
        protected void validateAndPrepare() {
    
            if(featImg == null) {   
                featImg = new KioskCollectionField<KioskImage>(null, true);
            }
                
            if(svgElemId == null) {   
                svgElemId = new KioskCollectionField<Long>(null, true);
            }

            if(isWheelChairAccessible == null) {   
                isWheelChairAccessible = new KioskCollectionField<Boolean>(null, true);
            }

            if(name == null) {   
                name = new KioskCollectionField<String>(null, true);
            }

            if(note == null) {   
                note = new KioskCollectionField<String>(null, true);
            }

            if(location == null) {
                location = new KioskCollectionField<LinkedCollectionReference>(null, true);
            }

        }
    }
        
}