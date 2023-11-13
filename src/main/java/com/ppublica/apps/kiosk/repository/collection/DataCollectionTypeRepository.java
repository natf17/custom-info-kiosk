package com.ppublica.apps.kiosk.repository.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElement;

public interface DataCollectionTypeRepository {

    public DataCollectionType saveCollectionInstance(DataCollectionType collectionInstance);

    public void updateCollectionLocalizedFields(Long collectionInstanceId, LocalizedFields localizedFields, String localeAbbrev);

    public void updateCollectionInstanceDataElements(Long collectionInstanceId, List<DataCollectionElement> newElements);

    public DataCollectionType updateCollectionInstance(Long collectionInstanceId, DataCollectionType collectionInstance);

    public List<DataCollectionType> findByCollectionTypeAndLocale(String collectionType, String collectionSubType, String localeAbbrev);

    // only deletes localized part of the collection
    public void deleteCollectionTypeInstancesWithLocale(String collectionType, String collectionSubType, String localeAbbrev);

    public void deleteCollectionInstance(Long collectionInstanceId);

    public boolean doesInstanceOfCollectionExist(String collectionType, String collectionSubType, String localeAbbrev);


}
