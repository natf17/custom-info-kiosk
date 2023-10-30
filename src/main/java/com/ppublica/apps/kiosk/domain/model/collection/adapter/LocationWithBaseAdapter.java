package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.collection.Location;


public class LocationWithBaseAdapter extends BaseAdapter implements Location {
    private Location kioskRepLocation;
    private LocationConverter locationConverter;

/*
    public static final CollectionTypeName KIOSK_COLLECTION_TYPE_NAME = CollectionTypeName.LOCATION;
    public static final String CMS_COLLECTION_TYPE_NAME = KIOSK_COLLECTION_TYPE_NAME.toString();

    
     * Expects SimpleCollectionType to be compatible with Location!
     *
    public LocationWithBaseAdapter(SimpleCollectionType cmsRep) {
        super(cmsRep, CMS_COLLECTION_TYPE_NAME);
    }

    public LocationWithBaseAdapter(LocationType kioskRep) {
        super(kioskRep, KIOSK_COLLECTION_TYPE_NAME);
        this.kioskRep = kioskRep;
    } */

    public LocationWithBaseAdapter(Location location, KioskCollectionType baseCollection, SimpleCollectionType baseCmsCollection) {
        super(baseCollection, baseCmsCollection);
        this.kioskRepLocation = location;
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
