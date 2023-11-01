package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.Location;
import com.ppublica.apps.kiosk.domain.model.collection.LocationType;


public class LocationWithBaseAdapter extends BaseAdapter implements Location {
    private Location kioskRepLocation;
    private LocationConverter locationConverter = new LocationConverter();

    public LocationWithBaseAdapter(Location location, KioskCollectionType baseCollection, SimpleCollectionType baseCmsCollection) {
        super(baseCollection, baseCmsCollection);
        this.kioskRepLocation = location;
    }

    public LocationWithBaseAdapter(LocationType locationType) {
        this(locationType, locationType, null);
    }

    public LocationWithBaseAdapter(SimpleCollectionType baseCmsCollection) {
        this(null, null, baseCmsCollection);
    }


    @Override
    public KioskCollectionField<String> getLevelNameField() {
        return getLocation().getLevelNameField();
    }

    @Override
    public KioskCollectionField<Long> getLevelNumField() {
        return getLocation().getLevelNumField();
    }

    @Override
    public KioskCollectionField<String> getFullNameField() {
        return getLocation().getFullNameField();
    }

    @Override
    public KioskCollectionField<Image> getMapField() {
        return getLocation().getMapField();
    }

    @Override
    protected void processCmsBuilder(SimpleCollectionTypeImpl.Builder builder) {
        locationConverter.transferKioskRep(builder, kioskRepLocation);
    }

    protected Location getLocation() {
        if(this.kioskRepLocation == null) {
            buildAndSetLocation();
        }

        return this.kioskRepLocation;
    }

    protected void buildAndSetLocation() {
        this.kioskRepLocation = locationConverter.convert(getCmsCollection());
    }
    
}
