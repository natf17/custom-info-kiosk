package com.ppublica.apps.kiosk.domain.model.kiosk;

/*
 * Represents a full amenity type that must be extended.
 */
public abstract class AmenityTypeBase extends KioskCollectionTypeBase implements AmenityType {

    private Amenity amenity;

    protected AmenityTypeBase(KioskCollectionType kioskCollectionType, Amenity amenity) {
        super(kioskCollectionType);
        this.amenity = amenity;
    }

    @Override
    public KioskCollectionField<KioskImage> featImg() {
        return this.amenity.featImg();
    }

    @Override
    public KioskCollectionField<Long> svgElemId() {
        return this.amenity.svgElemId();
    }

    @Override
    public KioskCollectionField<Boolean> isWheelChairAccessible() {
        return this.amenity.isWheelChairAccessible();
    }

    @Override
    public KioskCollectionField<String> name() {
        return this.amenity.name();
    }

    @Override
    public KioskCollectionField<String> note() {
        return this.amenity.note();
    }

    @Override
    public KioskCollectionField<LinkedCollectionReference> location() {
        return this.amenity.location();
    }

    /*
     * This builder delegates to the DefaultAmenity.Builder for amenity fields, or the superclass for KioskCollectionType fields.
     */
    public static abstract class Builder<B extends Builder<B,M>, M extends AmenityTypeBase> extends KioskCollectionTypeBase.Builder<Builder<B,M>, M> {
        protected KioskCollectionField<KioskImage> featImg;
        protected KioskCollectionField<Long> svgElemId;
        protected KioskCollectionField<Boolean> isWheelChairAccessible;
        protected KioskCollectionField<String> name;
        protected KioskCollectionField<String> note;
        protected KioskCollectionField<LinkedCollectionReference> location;

        private DefaultAmenityPiece.Builder amenityBuilder = new DefaultAmenityPiece.Builder();


        public Builder(CollectionType kioskCollectionTypeName) {
            super(kioskCollectionTypeName);
        }
        
        public B featImg(KioskCollectionField<KioskImage> editedFeatImg) {
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
