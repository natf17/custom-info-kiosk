package com.ppublica.apps.kiosk.domain.model.collection.selector;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.ImageField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.Location;
import com.ppublica.apps.kiosk.domain.model.collection.LocationImpl;

public class LocationAdapter extends SimpleCollectionTypeAdapter implements Location {
    
    private SimpleCollectionType cmsRep;
    private Location kioskRep;

    private static final String LEVELNAME_FIELD_TYPE = "Level_name";
    private static final String LEVELNUM_FIELD_TYPE = "Level_num";
    private static final String FULLNAME_FIELD_TYPE = "Fullname";
    private static final String MAP_FIELD_NAME_TYPE = "Map";

    public LocationAdapter(SimpleCollectionType cmsRep) {
        this.cmsRep = cmsRep;
    }

    public LocationAdapter(Location kioskRep) {
        this.kioskRep = kioskRep;
    }


    @Override
    public KioskCollectionField<String> getLevelNameField() {
        return getAndSetKioskRep().getLevelNameField();
    }

    @Override
    public KioskCollectionField<Long> getLevelNumField() {
        return getAndSetKioskRep().getLevelNumField();
    }

    @Override
    public KioskCollectionField<String> getFullNameField() {
        return getAndSetKioskRep().getFullNameField();
    }

    @Override
    public KioskCollectionField<Image> getMapField() {
          return getAndSetKioskRep().getMapField();
    }

    @Override
    protected void processAndSetCmsRep(SimpleCollectionTypeImpl.Builder cmsRepBuilder) {

        cmsRepBuilder.addTextField(super.toCmsCollectionConverter.toTextField(kioskRep.getLevelNameField(), LEVELNAME_FIELD_TYPE))
                        .addNumericField(super.toCmsCollectionConverter.toNumericField(kioskRep.getLevelNumField(), LEVELNUM_FIELD_TYPE))
                        .addTextField(super.toCmsCollectionConverter.toTextField(kioskRep.getFullNameField(), FULLNAME_FIELD_TYPE))
                        .addImageField(super.toCmsCollectionConverter.toImageField(kioskRep.getMapField(), MAP_FIELD_NAME_TYPE));
        
        
        this.cmsRep = cmsRepBuilder.build();

    }

    @Override
    protected SimpleCollectionType getCmsRepInstance() {
        return this.cmsRep;
    }

    @Override
    protected Location getAndSetKioskRep() {
        if(this.kioskRep != null) {
            return kioskRep;
        }

        // make use of "both cmsRep and kioskRep cannot be null" invariant

        LocationImpl.Builder builder = new LocationImpl.Builder();

        SimpleCollectionType cmsRep = this.cmsRep;

        processAndSetKioskRep(builder);

        List<ImageField> imageFields = cmsRep.getImageFields();
        List<NumericField> numericFields = cmsRep.getNumericFields();
        List<TextField> textFields = cmsRep.getTextFields();

        builder.map(super.toKioskCollectionConverter.toImageField(imageFields.get(0)));
        builder.levelNum(super.toKioskCollectionConverter.toLongField(numericFields.get(0)));

        textFields.stream().forEach(i -> {
            if(i.getFieldType().equals(LEVELNAME_FIELD_TYPE)) {
                builder.levelName(super.toKioskCollectionConverter.toStringField(i));
            }
            if(i.getFieldType().equals(FULLNAME_FIELD_TYPE)) {
                builder.fullName(super.toKioskCollectionConverter.toStringField(i));
            }
        });


        return kioskRep;

    }

    @Override
    protected KioskCollectionType getKioskRepInstance() {
        return this.kioskRep;
    }

    
}
