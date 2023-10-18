package com.ppublica.apps.kiosk.repository.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;

public interface SimpleCollectionTypeRepository {

    public SimpleCollectionType saveInstance(SimpleCollectionType collectionInstance);

    public List<SimpleCollectionType> findByCollectionTypeAndLocale(String collectionType, String localeAbbrev);

    public void deleteCollectionTypeInstancesWithLocale(String collectionType, String localeAbbrev);

    public void deleteCollectionInstance(Long id);

    public boolean doesInstanceOfCollectionExist(String collectionType, String localeAbbrev);

    public SimpleCollectionType updateInstance(Long id, SimpleCollectionType collectionInstance);


}

