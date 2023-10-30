package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public class DefaultAmenity implements Amenity {

    private KioskCollectionField<Image> featImg;
    private KioskCollectionField<Long> svgElemId;
    private KioskCollectionField<Boolean> isWheelChairAccessible;
    private KioskCollectionField<String> name;
    private KioskCollectionField<String> note;
    private KioskCollectionField<LinkedCollectionReference> location;

    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.AMENITY;
    private static final String FEATIMG_FIELD_NAME_DEFAULT = "FeatImg";
    private static final String SVGELEM_FIELD_NAME_DEFAULT = "svgElemId";
    private static final String ISWHEELCHAIRACC_FIELD_NAME_DEFAULT = "isWheelchairAccessible";
    private static final String NAME_FIELD_NAME_DEFAULT = "Name";
    private static final String NOTE_FIELD_NAME_DEFAULT = "Note";
    private static final String LOCATION_FIELD_NAME_DEFAULT = "Location";

    protected DefaultAmenity(KioskCollectionField<Image> featImg, KioskCollectionField<Long> svgElemId, KioskCollectionField<Boolean> isWheelChairAccessible,
                            KioskCollectionField<String> name, KioskCollectionField<String> note, KioskCollectionField<LinkedCollectionReference> location) {
        this.featImg = featImg;
        this.svgElemId = svgElemId;
        this.isWheelChairAccessible = isWheelChairAccessible;
        this.name = name;
        this.note = note;
        this.location = location;
    }

    @Override
    public KioskCollectionField<Image> getFeatImg() {
        return this.featImg;
    }

    @Override
    public KioskCollectionField<Long> getSvgElemId() {
        return this.svgElemId;
    }

    @Override
    public KioskCollectionField<Boolean> isWheelChairAccessible() {
        return this.isWheelChairAccessible;
    }

    @Override
    public KioskCollectionField<String> getName() {
        return this.name;
    }

    @Override
    public KioskCollectionField<String> getNote() {
        return this.note;
    }

    @Override
    public KioskCollectionField<LinkedCollectionReference> getLocation() {
        return this.location;
    }

    public static class Builder {
        protected KioskCollectionField<Image> featImg;
        protected KioskCollectionField<Long> svgElemId;
        protected KioskCollectionField<Boolean> isWheelChairAccessible;
        protected KioskCollectionField<String> name;
        protected KioskCollectionField<String> note;
        protected KioskCollectionField<LinkedCollectionReference> location;
            
        public Builder featImg(KioskCollectionField<Image> editedFeatImg) {
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

            return new DefaultAmenity(featImg, svgElemId, isWheelChairAccessible, name, note, location);
        }
            
        protected void validateAndPrepare() {
    
            if(featImg == null) {   
                featImg = new KioskCollectionField<Image>(FEATIMG_FIELD_NAME_DEFAULT, null, true);
            }
                
            if(svgElemId == null) {   
                svgElemId = new KioskCollectionField<Long>(SVGELEM_FIELD_NAME_DEFAULT, null, true);
            }

            if(isWheelChairAccessible == null) {   
                isWheelChairAccessible = new KioskCollectionField<Boolean>(ISWHEELCHAIRACC_FIELD_NAME_DEFAULT, null, true);
            }

            if(name == null) {   
                name = new KioskCollectionField<String>(NAME_FIELD_NAME_DEFAULT, null, true);
            }

            if(note == null) {   
                note = new KioskCollectionField<String>(NOTE_FIELD_NAME_DEFAULT, null, true);
            }

            if(location == null) {
                location = new KioskCollectionField<LinkedCollectionReference>(LOCATION_FIELD_NAME_DEFAULT, null, true);
            }

        }
    }
        
}
