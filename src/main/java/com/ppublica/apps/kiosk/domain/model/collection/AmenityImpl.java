package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public class AmenityImpl extends KioskCollectionTypeImpl implements Amenity {

    private KioskCollectionField<Image> featImg;
    private KioskCollectionField<Long> svgElemId;
    private KioskCollectionField<Boolean> isWheelChairAccessible;
    private KioskCollectionField<String> name;
    private KioskCollectionField<String> note;
    private KioskCollectionField<Location> location;

    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.AMENITY;
    private static final String FEATIMG_FIELD_NAME_DEFAULT = "FeatImg";
    private static final String SVGELEM_FIELD_NAME_DEFAULT = "svgElemId";
    private static final String ISWHEELCHAIRACC_FIELD_NAME_DEFAULT = "isWheelchairAccessible";
    private static final String NAME_FIELD_NAME_DEFAULT = "Name";
    private static final String NOTE_FIELD_NAME_DEFAULT = "Note";
    private static final String LOCATION_FIELD_NAME_DEFAULT = "Location";

    protected AmenityImpl(CollectionTypeName type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata, Long id,
                    KioskCollectionField<Image> featImg, KioskCollectionField<Long> svgElemId, KioskCollectionField<Boolean> isWheelChairAccessible,
                    KioskCollectionField<String> name, KioskCollectionField<String> note, KioskCollectionField<Location> location) {
        super(type, collectionNameField, kioskCollectionMetadata, id);
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
    public KioskCollectionField<Location> getLocation() {
        return this.location;
    }

    public static abstract class Builder<B extends Builder<B,M>, M extends AmenityImpl> extends KioskCollectionTypeImpl.Builder<Builder<B,M>, M> {
        protected KioskCollectionField<Image> featImg;
        protected KioskCollectionField<Long> svgElemId;
        protected KioskCollectionField<Boolean> isWheelChairAccessible;
        protected KioskCollectionField<String> name;
        protected KioskCollectionField<String> note;
        protected KioskCollectionField<Location> location;

        public Builder() {
            super(KIOSK_COLLECTION_TYPE_NAME);
        }

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

        public B location(KioskCollectionField<Location> location) {
            this.location = location;
            return self();
        }
        

        @Override
        protected void validateAndPrepareChild() {
        
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
                location = new KioskCollectionField<Location>(LOCATION_FIELD_NAME_DEFAULT, null, true);
            }

        }

        protected abstract B self();

        protected abstract M buildChild();

    }
    
}
