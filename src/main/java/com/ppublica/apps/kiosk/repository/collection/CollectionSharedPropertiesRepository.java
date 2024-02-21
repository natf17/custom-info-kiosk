package com.ppublica.apps.kiosk.repository.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;

public interface CollectionSharedPropertiesRepository {
    public CollectionSharedProperties saveInstance(CollectionLocalizedProperties collectionInstance);

    public List<CollectionSharedProperties> findByCollectionType(String type, String subType);

    public CollectionSharedProperties findById(Long id);

    public void deleteCollectionInstance(Long id);

    public boolean doesInstanceOfCollectionExist(String type, String subType);

    public CollectionSharedProperties updateCollectionInstance(Long id, CollectionLocalizedProperties collectionInstance);
    
}
