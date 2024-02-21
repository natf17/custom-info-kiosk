package com.ppublica.apps.kiosk.repository.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;

public interface CollectionLocalizedPropertiesRepository {
    public CollectionLocalizedProperties saveLocalizedInstance(Long parentId, CollectionLocalizedProperties collectionInstance);

    public List<CollectionLocalizedProperties> findByCollectionType(String type, String subType);

    public List<CollectionLocalizedProperties> findByCollectionTypeAndLocale(String type, String subType, String locale);

    public List<CollectionLocalizedProperties> findByParentCollectionIdAndLocaleBatch(List<Long> parentIds, String locale);

    public List<CollectionLocalizedProperties> findByParentCollectionId(Long parentId);

    public void deleteLocalizedCollectionInstances(Long parentId);

    public boolean doesLocalizedInstanceOfCollectionExist(String type, String subType, String locale);

    public CollectionLocalizedProperties updateLocalizedCollectionInstance(Long id, CollectionLocalizedProperties collectionInstance);
}
