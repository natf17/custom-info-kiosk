package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.Amenity;
import com.ppublica.apps.kiosk.domain.model.kiosk.GenderAware;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.GenderConverter;

public class GenderAwareAmenityAdapter extends AmenityKioskCollectionAdapter implements GenderAware {
    private GenderAware genderInfo;
    private GenderConverter genderConverter = new GenderConverter();

    public GenderAwareAmenityAdapter(GenderAware genderInfo, Amenity amenity, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(amenity, baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
        this.genderInfo = genderInfo;
    }

    public GenderAwareAmenityAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, null, localizedCmsPiece, sharedCmsPiece);
    }

    public GenderAwareAmenityAdapter(GenderAware genderInfo, Amenity amenity, KioskCollectionType baseKioskCollection) {
        this(genderInfo, amenity, baseKioskCollection, null, null);
    }

    @Override
    public KioskCollectionField<String> gender() {
        return getGenderInfo().gender();
    }

    @Override
    protected void processCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder) {
        super.processCmsBuilders(sharedCmsBuilder, localizedCmsBuilder);
        genderConverter.transferKioskRepToCmsBuilders(sharedCmsBuilder, localizedCmsBuilder, this.genderInfo);

    }

    protected GenderAware getGenderInfo() {
        if(this.genderInfo == null) {
            buildAndSetGenderInfo();
        }

        return this.genderInfo;
    }

    protected void buildAndSetGenderInfo() {
        this.genderInfo = genderConverter.convert(getSharedCmsPiece(), getLocalizedCmsPiece());
    }
}
