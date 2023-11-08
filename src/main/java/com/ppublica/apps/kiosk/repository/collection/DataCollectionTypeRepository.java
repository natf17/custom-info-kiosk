package com.ppublica.apps.kiosk.repository.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElement;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;

public interface DataCollectionTypeRepository {

    public DataCollectionType saveCollectionInstance(DataCollectionType collectionInstance);

    public DataCollectionType saveCollectionLocalizedFields(Long collectionInstanceId, LocalizedFields localizedFields);

    public DataCollectionType saveDataElementsToCollectionInstance(Long collectionInstanceId, List<DataCollectionElement> element);

    public List<SimpleCollectionType> findByCollectionTypeAndLocale(String collectionType, String collectionSubType, String localeAbbrev);

    public void deleteCollectionTypeInstancesWithLocale(String collectionType, String collectionSubType, String localeAbbrev);

    public void deleteCollectionInstance(Long id);

    public boolean doesInstanceOfCollectionExist(String collectionType, String collectionSubType, String localeAbbrev);

    public DataCollectionType updateCollectionInstance(Long id, DataCollectionType collectionInstance);

}
