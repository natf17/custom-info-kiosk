package com.ppublica.apps.kiosk.repository.collection;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionDataElement;
import com.ppublica.apps.kiosk.domain.model.cms.collection.SimpleCollectionType;

public interface CollectionDataElementRepository {
    public CollectionDataElement saveInstance(CollectionDataElement collectionInstance);

    public List<CollectionDataElement> findByCollectionType(String type, String subType);

    public List<CollectionDataElement> findByParentAndType(Long parentId, String type);

    public void deleteCollectionInstance(Long id);

    public boolean doesInstanceOfCollectionExistByCollectionType(String type, String subType);

    public boolean doesInstanceOfCollectionExistByParentAndCollectionType(Long parentId, String type);

    public SimpleCollectionType updateCollectionInstance(Long id, CollectionDataElement collectionInstance);
}
