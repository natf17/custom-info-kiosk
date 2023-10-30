package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionTypeImpl;

/**
 * Base interface for converters that facilitate conversion betwee a SimpleCollectionType and an instance of type T.
 */
public interface KioskAndCmsTypeConverter<T> {

    /**
     * Converts a SimpleCollectionType cms collection to a kiosk colleciton of type T.
     * 
     * @param cmsRep    the SimpleCollectionType source that will be used to create a kiosk collection
     * @return          the kiosk collection
     */
    T convert(SimpleCollectionType cmsRep);

    /**
     * Takes a kiosk collection T and populates a SimpleCollectionTypeImpl.Builder.
     * 
     * Mostly used by adapters that build the cms collection piece by piece.
     * 
     * @param builder           the cms collection builder to be populated
     * @param kioskCollection   the kiosk collecton source
     */
    void transferKioskRep(SimpleCollectionTypeImpl.Builder builder, T kioskCollection);
    
}
