package com.ppublica.apps.kiosk.repository.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElement;

public interface DataCollectionTypeRepository {

    public DataCollectionType saveCollectionInstance(DataCollectionType collectionInstance);

    public void updateCollectionLocalizedFields(Long collectionInstanceId, LocalizedFields localizedFields);

    public void updateCollectionInstanceDataElements(Long collectionInstanceId, List<DataCollectionElement> newElements);

    public List<DataCollectionType> findByCollectionTypeAndLocale(String collectionType, String collectionSubType, String localeAbbrev);

    public void deleteCollectionTypeInstancesWithLocale(String collectionType, String collectionSubType, String localeAbbrev);

    public void deleteCollectionInstance(Long id);

    public boolean doesInstanceOfCollectionExist(String collectionType, String collectionSubType, String localeAbbrev);

    public DataCollectionType updateCollectionInstance(Long id, DataCollectionType collectionInstance);

}
