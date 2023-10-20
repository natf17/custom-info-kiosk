package com.ppublica.apps.kiosk.domain.model.collection;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public class Bathroom extends AmenityImpl {

    private KioskCollectionField<String> gender;

    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.BATHROOM;
    private static final String GENDER_FIELD_NAME_DEFAULT = "Gender";

    protected Bathroom(CollectionTypeName type, KioskCollectionField<String> collectionNameField, KioskCollectionMetadata kioskCollectionMetadata, Long id,
                    KioskCollectionField<Image> featImg, KioskCollectionField<Long> svgElemId, KioskCollectionField<Boolean> isWheelChairAccessible,
                    KioskCollectionField<String> name, KioskCollectionField<String> note, KioskCollectionField<Location> location, KioskCollectionField<String> gender) {
        super(type, collectionNameField, kioskCollectionMetadata, id, featImg, svgElemId, isWheelChairAccessible, name, note, location);
        this.gender = gender;
    }

    public KioskCollectionField<String> getGender() {
        return gender;
    }

    public static class Builder extends AmenityImpl.Builder<Builder, Bathroom> {
        private KioskCollectionField<String> gender;

        public Builder() {
            super(KIOSK_COLLECTION_TYPE_NAME);
        }
        
        public Builder gender(KioskCollectionField<String> editedGender) {
            this.gender = editedGender;
            return self();
        }

        @Override
        protected void validateAndPrepareChild() {
        
            super.validateAndPrepareChild();

            if(gender == null) {   
                gender = new KioskCollectionField<String>(GENDER_FIELD_NAME_DEFAULT, null, true);
            }

        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected Bathroom buildChild() {

            return new Bathroom(super.type, super.collectionNameField, super.kioskCollectionMetadata, super.id, super.featImg, 
                                super.svgElemId, super.isWheelChairAccessible, super.name, super.note, super.location, gender);
        }
    }
    
}
