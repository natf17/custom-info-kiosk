package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public abstract class AmenityTypeImpl extends KioskCollectionTypeImpl implements AmenityType {

    private Amenity amenity;

    protected AmenityTypeImpl(KioskCollectionType kioskCollectionType, Amenity amenity) {
        super(kioskCollectionType);
        this.amenity = amenity;
    }

    @Override
    public KioskCollectionField<Image> getFeatImg() {
        return this.amenity.getFeatImg();
    }

    @Override
    public KioskCollectionField<Long> getSvgElemId() {
        return this.amenity.getSvgElemId();
    }

    @Override
    public KioskCollectionField<Boolean> isWheelChairAccessible() {
        return this.amenity.isWheelChairAccessible();
    }

    @Override
    public KioskCollectionField<String> getName() {
        return this.amenity.getName();
    }

    @Override
    public KioskCollectionField<String> getNote() {
        return this.amenity.getNote();
    }

    @Override
    public KioskCollectionField<LinkedCollectionReference> getLocation() {
        return this.amenity.getLocation();
    }

    /*
     * This builder delegates to the DefaultAmenity.Builder for amenity fields, or the superclass for KioskCollectionType fields.
     */
    public static abstract class Builder<B extends Builder<B,M>, M extends AmenityTypeImpl> extends KioskCollectionTypeImpl.Builder<Builder<B,M>, M> {
        protected KioskCollectionField<Image> featImg;
        protected KioskCollectionField<Long> svgElemId;
        protected KioskCollectionField<Boolean> isWheelChairAccessible;
        protected KioskCollectionField<String> name;
        protected KioskCollectionField<String> note;
        protected KioskCollectionField<LinkedCollectionReference> location;

        private DefaultAmenity.Builder amenityBuilder = new DefaultAmenity.Builder();


        public Builder(CollectionTypeName kioskCollectionTypeName) {
            super(kioskCollectionTypeName);
        }
        
        public B featImg(KioskCollectionField<Image> editedFeatImg) {
            this.featImg = editedFeatImg;
            return self();
        }

        public B svgElemId(KioskCollectionField<Long> editedSvgElemId) {
            this.svgElemId = editedSvgElemId;
            return self();
        }

        public B isWheelChairAccessible(KioskCollectionField<Boolean> editedIsWheelChairAccessible) {
            this.isWheelChairAccessible = editedIsWheelChairAccessible;
            return self();
        }

        public B name(KioskCollectionField<String> editedName) {
            this.name = editedName;
            return self();
        }

        public B note(KioskCollectionField<String> editedNote) {
            this.note = editedNote;
            return self();
        }

        public B location(KioskCollectionField<LinkedCollectionReference> location) {
            this.location = location;
            return self();
        }
        

        // any validation in addition to that provided by the underlying amenity builder...
        @Override
        protected void validateAndPrepareChild() {
            validateAndPrepareAmenityChild();
        }

        protected abstract void validateAndPrepareAmenityChild();

        protected abstract B self();

        @Override
        protected M buildChild(KioskCollectionType kioskCollectionType) {
            Amenity amenity = amenityBuilder
                                .featImg(featImg)
                                .svgElemId(svgElemId)
                                .isWheelChairAccessible(isWheelChairAccessible)
                                .name(name)
                                .note(note)
                                .location(location)
                                .build();

            return buildAmenityChild(kioskCollectionType, amenity);
        }

        protected abstract M buildAmenityChild(KioskCollectionType kioskCollectionType, Amenity amenity);


    }
    
}
