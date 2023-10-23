package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl.Builder;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.Amenity;
import com.ppublica.apps.kiosk.domain.model.collection.AmenityImpl;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.LinkedCollectionReference;

public abstract class AmenityAdapter extends SimpleCollectionTypeAdapter implements Amenity {
    private static final String FEATIMG_FIELD_TYPE = "FeatImg";
    private static final String SVGELEM_FIELD_TYPE = "svgElemId";
    private static final String ISWHEELCHAIRACC_FIELD_TYPE = "isWheelchairAccessible";
    private static final String NAME_FIELD_TYPE = "Name";
    private static final String NOTE_FIELD_TYPE = "Note";
    private static final String LOCATION_FIELD_TYPE = "Location";

    private SimpleCollectionType cmsRep;
    private Amenity kioskRep;

    @Override
    public KioskCollectionField<Image> getFeatImg() {
        return getOrBuildAndSetKioskRep().getFeatImg();
    }

    @Override
    public KioskCollectionField<Long> getSvgElemId() {
        return getOrBuildAndSetKioskRep().getSvgElemId();
    }

    @Override
    public KioskCollectionField<Boolean> isWheelChairAccessible() {
        return getOrBuildAndSetKioskRep().isWheelChairAccessible();
    }

    @Override
    public KioskCollectionField<String> getName() {
        return getOrBuildAndSetKioskRep().getName();
    }

    @Override
    public KioskCollectionField<String> getNote() {
        return getOrBuildAndSetKioskRep().getNote();
    }

    @Override
    public KioskCollectionField<LinkedCollectionReference> getLocation() {
        return getOrBuildAndSetKioskRep().getLocation();
    }

    @Override
    protected SimpleCollectionType getCmsRepInstance() {
        return this.cmsRep;
    }

    @Override
    protected void processAndSetCmsRep(Builder cmsRepBuilder) {
        cmsRepBuilder.addImageField(super.toCmsCollectionConverter.toImageField(kioskRep.getFeatImg(), FEATIMG_FIELD_TYPE))
                    .addNumericField(super.toCmsCollectionConverter.toNumericField(kioskRep.getSvgElemId(), SVGELEM_FIELD_TYPE))
                    .addBooleanField(super.toCmsCollectionConverter.toBooleanField(kioskRep.isWheelChairAccessible(), ISWHEELCHAIRACC_FIELD_TYPE))
                    .addTextField(super.toCmsCollectionConverter.toTextField(kioskRep.getName(), NAME_FIELD_TYPE))
                    .addTextField(super.toCmsCollectionConverter.toTextField(kioskRep.getNote(), NOTE_FIELD_TYPE))
                    .addLinkedCollectionField(super.toCmsCollectionConverter.toLinkedCollectionField(kioskRep.getLocation(), LOCATION_FIELD_TYPE));
        
        this.cmsRep = cmsRepBuilder.build();
    }

    @Override
    protected KioskCollectionType getKioskRepInstance() {
       return this.kioskRep;
    }

    @Override
    protected Amenity getOrBuildAndSetKioskRep() {
        if(this.kioskRep != null) {
            return kioskRep;
        }

        // make use of "both cmsRep and kioskRep cannot be null" invariant

        AmenityImpl.Builder<?,?> builder = getKioskBuilder();

        SimpleCollectionType cmsRep = this.cmsRep;

        processKioskRep(builder);


        List<ImageField> imageFields = cmsRep.getImageFields();
        List<NumericField> numericFields = cmsRep.getNumericFields();
        List<BooleanField> booleanFields = cmsRep.getBooleanFields();
        List<TextField> textFields = cmsRep.getTextFields();
        List<LinkedCollectionField> linkedCollFields = cmsRep.getLinkedCollectionFields();

        builder.featImg(super.toKioskCollectionConverter.toImageField(imageFields.get(0)));

        builder.svgElemId(super.toKioskCollectionConverter.toLongField(numericFields.get(0)));

        builder.isWheelChairAccessible(super.toKioskCollectionConverter.toBooleanField(booleanFields.get(0)));

        textFields.stream().forEach(i -> {
            if(i.getFieldType().equals(NAME_FIELD_TYPE)) {
                builder.name(super.toKioskCollectionConverter.toStringField(i));
            }
            if(i.getFieldType().equals(NOTE_FIELD_TYPE)) {
                builder.note(super.toKioskCollectionConverter.toStringField(i));
            }
        });

        builder.location(super.toKioskCollectionConverter.toLinkedCollectionReferenceField(linkedCollFields.get(0)));

        this.kioskRep = builder.build();


        return kioskRep;
    }

    protected abstract <B extends AmenityImpl.Builder<B,M>, M extends AmenityImpl> AmenityImpl.Builder<B, M> getKioskBuilder();
    
}
