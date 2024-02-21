package com.ppublica.apps.kiosk.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.CmsCollectionAdapterBuilder;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.KioskCollectionTypeBaseAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;

public abstract class LocalizedCollectionServiceBase<T extends KioskCollectionTypeBaseAdapter, U extends KioskCollectionType, B extends CmsCollectionAdapterBuilder<B, U, T>> {
    private List<CmsLocalizedEntityHolder> getLocalizedCmsObjects(CollectionTypeName type, String locale, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        // get CollectionSharedProperties
        List<CollectionSharedProperties> matchingCollSharedProps = collSharedPropsRepo.findByCollectionType(type.toString(), null);
        
        // get all CollectionSharedProperties ids
        List<Long> ids = matchingCollSharedProps.stream()
                                                .map(sharedProps -> sharedProps.id())
                                                .collect(Collectors.toList());

        if(ids.isEmpty()) {
            return List.of();
        }

        // get CollectionLocalizedProperties
        List<CollectionLocalizedProperties> matchingCollLocalizedProps = collLocalizedPropsRepo.findByParentCollectionIdAndLocaleBatch(ids, locale);
       
        HashMap<Long, CollectionLocalizedProperties> matchingCollLocPropsMap = new HashMap<>();

        // put CollectionLocalizedProperties in a hash map
        matchingCollLocalizedProps.stream()
                                    .forEach(locProps -> matchingCollLocPropsMap.put(locProps.parentId(), locProps));

        List<CmsLocalizedEntityHolder> cmsLocalizedEntityHolders = matchingCollSharedProps.stream()
                                                                                            .map(parentProps -> {
                                                                                                return new CmsLocalizedEntityHolder(parentProps, matchingCollLocPropsMap.get(parentProps.id()));
                                                                                                })
                                                                                            .collect(Collectors.toList());

        
        return cmsLocalizedEntityHolders;
    }

    protected List<T> loadAdapters(CollectionTypeName type, String locale, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        List<CmsLocalizedEntityHolder> cmsLocalizedEntityHolders = getLocalizedCmsObjects(CollectionTypeName.BATHROOM, locale, collSharedPropsRepo, collLocalizedPropsRepo);
        List<T> adapters = cmsLocalizedEntityHolders
                                .stream()
                                .map(localizedCmsHolder -> createAdapter(localizedCmsHolder, getAdapterBuilder()))
                                .collect(Collectors.toList());

        return adapters;
    }

    protected List<T> loadAdapters(Long collId, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        CmsEntityHolder cmsEntityHolder = getCmsObject(collId, collSharedPropsRepo, collLocalizedPropsRepo);
        List<T> adapters = cmsEntityHolder.flatten()
                                .stream()
                                .map(localizedCmsHolder -> createAdapter(localizedCmsHolder, getAdapterBuilder()))
                                .collect(Collectors.toList());

        return adapters;
    }

    private CmsEntityHolder getCmsObject(Long collId, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        // get CollectionSharedProperties
        CollectionSharedProperties matchingCollSharedProps = collSharedPropsRepo.findById(collId);
        
        if(matchingCollSharedProps == null) {
            return null;
        }

        List<CollectionLocalizedProperties> matchingCollProps = collLocalizedPropsRepo.findByParentCollectionId(collId);

        return new CmsEntityHolder(matchingCollSharedProps, matchingCollProps);
    }

    private List<CmsEntityHolder> getCmsObjects(CollectionTypeName type, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        // get CollectionSharedProperties
        List<CollectionSharedProperties> matchingCollSharedProps = collSharedPropsRepo.findByCollectionType(type.toString(), null);
        
        if(matchingCollSharedProps.isEmpty()) {
            return List.of();
        }

        List<CmsEntityHolder> cmsLocalizedEntityHolders = matchingCollSharedProps.stream()
                                                                                .map(parentProps -> {
                                                                                    List<CollectionLocalizedProperties> matchingCollProps = collLocalizedPropsRepo.findByParentCollectionId(parentProps.id());
                                                                                    return new CmsEntityHolder(parentProps, matchingCollProps);
                                                                                })
                                                                                .collect(Collectors.toList());

        return cmsLocalizedEntityHolders;
    }

    protected List<List<T>> loadListOfAdaptersList(CollectionTypeName type, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        List<CmsEntityHolder> cmsEntityHolders = getCmsObjects(CollectionTypeName.BATHROOM, collSharedPropsRepo, collLocalizedPropsRepo);

        return cmsEntityHolders
                .stream()
                .map(cmsEntityHolder -> {
                    return cmsEntityHolder.flatten()
                                    .stream()
                                    .map(localizedCmsHolder -> createAdapter(localizedCmsHolder, getAdapterBuilder()))
                                    .collect(Collectors.toList());
                })
                .collect(Collectors.toList());
    }

    private T createAdapter(CmsLocalizedEntityHolder cmsLocalizedEntityHolder, B builder) {
        return builder.sharedCmsPiece(cmsLocalizedEntityHolder.parentProps())
                    .localizedCmsPiece(cmsLocalizedEntityHolder.localizedProps())
                .build();
    }

    protected List<T> createAdapters(List<U> kioskCollectionTypeList) {
        return kioskCollectionTypeList.stream()
                                    .map(kioskCollection -> {
                                                return getAdapterBuilder()
                                                            .kioskCollection(kioskCollection)
                                                            .build();
                                    })
                                    .collect(Collectors.toList());
        
    }

    protected List<T> save(List<U> kioskCollectionTypeList, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        List<T> adapters = createAdapters(kioskCollectionTypeList);

        CmsEntityHolder cmsEntityHolder = saveInternal(adapters, collSharedPropsRepo, collLocalizedPropsRepo);

        // create adapters
        List<T> newBathroomAdapters = cmsEntityHolder.flatten()
                                                    .stream()
                                                    .map(newLocalizedEntityHolder -> createAdapter(newLocalizedEntityHolder, getAdapterBuilder()))
                                                    .collect(Collectors.toList());

        return newBathroomAdapters;
    }

    private CmsEntityHolder saveInternal(List<T> adapters, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        CollectionSharedProperties newSharedProps = collSharedPropsRepo.saveInstance(adapters.get(0));

        List<CollectionLocalizedProperties> newLocalizedPropsList = adapters.stream()
                                                                                .map(adapter -> collLocalizedPropsRepo.saveLocalizedInstance(newSharedProps.id(), adapter))
                                                                                .collect(Collectors.toList());

        return new CmsEntityHolder(newSharedProps, newLocalizedPropsList);
    }

    protected List<T> update(List<U> kioskCollectionTypeList, Long collId, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        List<T> adapters = createAdapters(kioskCollectionTypeList);
        collLocalizedPropsRepo.deleteLocalizedCollectionInstances(collId);

        CollectionSharedProperties updatedSharedProps = collSharedPropsRepo.updateCollectionInstance(collId, adapters.get(0));


        List<T> newAdapters = adapters.stream()
                                    .map(adapter -> collLocalizedPropsRepo.saveLocalizedInstance(collId, adapter))
                                    .map(newLocalizedProps -> new CmsLocalizedEntityHolder(updatedSharedProps, newLocalizedProps))
                                    .map(newLocalizedEntityHolder -> createAdapter(newLocalizedEntityHolder, getAdapterBuilder()))
                                    .collect(Collectors.toList());

        
        return newAdapters;
    }
    
    protected void delete(Long collId, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        collSharedPropsRepo.deleteCollectionInstance(collId);
        collLocalizedPropsRepo.deleteLocalizedCollectionInstances(collId);
    }
    
    protected abstract B getAdapterBuilder();
}
