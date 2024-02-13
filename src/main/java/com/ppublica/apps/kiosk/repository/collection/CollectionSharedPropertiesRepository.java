package com.ppublica.apps.kiosk.repository.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;

public interface CollectionSharedPropertiesRepository {
    public CollectionLocalizedProperties saveInstance(CollectionLocalizedProperties collectionInstance);

    public List<SimpleCollectionType> findByCollectionType(String type, String subType);

    public void deleteCollectionInstance(Long id);

    public boolean doesInstanceOfCollectionExist(String type, String subType);

    public SimpleCollectionType updateCollectionInstance(Long id, CollectionLocalizedProperties collectionInstance);
    
}
