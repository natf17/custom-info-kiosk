package com.ppublica.apps.kiosk.domain.model.kiosk.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskImage;
import com.ppublica.apps.kiosk.domain.model.kiosk.Location;
import com.ppublica.apps.kiosk.domain.model.kiosk.LocationType;
import com.ppublica.apps.kiosk.domain.model.kiosk.converter.LocationInfoConverter;

public class LocationKioskCollectionAdapter extends KioskCollectionTypeBaseAdapter implements LocationType {
    private Location locationInfo;

    private LocationInfoConverter locationInfoConverter = new LocationInfoConverter();


    public LocationKioskCollectionAdapter(Location locationInfo, KioskCollectionType baseKioskCollection, CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        super(baseKioskCollection, localizedCmsPiece, sharedCmsPiece);
        this.locationInfo = locationInfo;
    }

    public LocationKioskCollectionAdapter(LocationType locationType) {
        this(locationType, locationType, null, null);
    }

    public LocationKioskCollectionAdapter(Location locationInfo, KioskCollectionType baseKioskCollection) {
        this(locationInfo, baseKioskCollection, null, null);
    }

    public LocationKioskCollectionAdapter(CollectionLocalizedProperties localizedCmsPiece, CollectionSharedProperties sharedCmsPiece) {
        this(null, null, localizedCmsPiece, sharedCmsPiece);
    }

    @Override
    public KioskCollectionField<String> fullName() {
        return getLocation().fullName();
    }

    @Override
    public KioskCollectionField<Integer> level_num() {
        return getLocation().level_num();
    }

    @Override
    public KioskCollectionField<String> level_name() {
        return getLocation().level_name();
    }

    @Override
    public KioskCollectionField<KioskImage> map() {
        return getLocation().map();
    }

    @Override
    protected void processCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder) {
        locationInfoConverter.transferKioskRepToCmsBuilders(sharedCmsBuilder, localizedCmsBuilder, this.locationInfo);

    }

    protected Location getLocation() {
        if(this.locationInfo == null) {
            buildAndSetLocation();
        }

        return this.locationInfo;
    }

    protected void buildAndSetLocation() {
        this.locationInfo = locationInfoConverter.convert(getSharedCmsPiece(), getLocalizedCmsPiece());
    }

     public static class Builder extends CmsCollectionAdapterBuilder<Builder, LocationType, LocationKioskCollectionAdapter> {

        @Override
        protected LocationKioskCollectionAdapter buildChild() {
            return new LocationKioskCollectionAdapter(super.kioskCollection, super.kioskCollection, super.localizedCmsPiece, super.sharedCmsPiece);
        }

        @Override
        protected Builder self() {
            return this;
        }

    }
}
