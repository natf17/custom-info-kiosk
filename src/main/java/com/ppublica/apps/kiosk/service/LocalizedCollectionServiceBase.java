package com.ppublica.apps.kiosk.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.CmsCollectionAdapterBuilder;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.KioskCollectionTypeBaseAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;

public abstract class LocalizedCollectionServiceBase<T extends KioskCollectionTypeBaseAdapter, U extends KioskCollectionType, B extends CmsCollectionAdapterBuilder<B, U, T>> {
    /*
     * Requires CollectionSharedProperties to have an id, and CollectionLocalizedProperties to have a parentId
     */
    
    private List<CmsLocalizedEntityHolder> getLocalizedCmsObjects(CollectionType type, String locale, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        // get CollectionSharedProperties
        List<CollectionSharedProperties> matchingCollSharedProps = collSharedPropsRepo.findByCollectionType(type.toString(), null);
        
        // get all CollectionSharedProperties ids
        List<Long> ids = matchingCollSharedProps.stream()
                                                .map(sharedProps -> sharedProps.id())
                                                .collect(Collectors.toList());

        if(ids.isEmpty()) {
            throw new RuntimeException("ids empty");
            //return List.of();
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

    protected List<T> loadAdapters(CollectionType type, String locale, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        List<CmsLocalizedEntityHolder> cmsLocalizedEntityHolders = getLocalizedCmsObjects(CollectionType.BATHROOM, locale, collSharedPropsRepo, collLocalizedPropsRepo);
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

    private List<CmsEntityHolder> getCmsObjects(CollectionType type, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
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

    protected List<List<T>> loadListOfAdaptersList(CollectionType type, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        List<CmsEntityHolder> cmsEntityHolders = getCmsObjects(CollectionType.BATHROOM, collSharedPropsRepo, collLocalizedPropsRepo);

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
        if(cmsLocalizedEntityHolder.parentProps() == null) {
            throw new RuntimeException("parentProps null");
        }

        if(cmsLocalizedEntityHolder.localizedProps() == null) {
            throw new RuntimeException("localizedProps null");
        }

        return builder.sharedCmsPiece(cmsLocalizedEntityHolder.parentProps())
                    .localizedCmsPiece(cmsLocalizedEntityHolder.localizedProps())
                .build();
    }

    protected List<T> createAdapters(List<? extends U> kioskCollectionTypeList) {
        return kioskCollectionTypeList.stream()
                                    .map(kioskCollection -> {
                                        if(kioskCollection == null) {
                                            throw new RuntimeException("kioskCollection null");
                                        } else if(kioskCollection != null){
                                            //throw new RuntimeException("eee..." + kioskCollection);
                                        }
                                                return getAdapterBuilder()
                                                            .kioskCollection(kioskCollection)
                                                            .build();
                                    })
                                    .collect(Collectors.toList());
        
    }

    protected List<T> save(List<? extends U> kioskCollectionTypeList, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
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

    protected List<T> update(List<? extends U> kioskCollectionTypeList, Long collId, CollectionSharedPropertiesRepository collSharedPropsRepo, CollectionLocalizedPropertiesRepository collLocalizedPropsRepo) {
        List<T> adapters = createAdapters(kioskCollectionTypeList);
        collLocalizedPropsRepo.deleteLocalizedCollectionInstances(collId);

        CollectionSharedProperties updatedSharedProps = collSharedPropsRepo.updateCollectionInstance(collId, adapters.get(0));


        List<T> newAdapters = adapters.stream()
                                    .map(adapter -> {
                                        if(collLocalizedPropsRepo.saveLocalizedInstance(collId, adapter) == null) {
                                            throw new RuntimeException("saveLocalizedInstance: ");
                                        }
                                        return collLocalizedPropsRepo.saveLocalizedInstance(collId, adapter);})
                                    .map(newLocalizedProps -> {
                                        if(newLocalizedProps == null) {
                                            throw new RuntimeException("newLocalizedProps stream null");
                                        }
                                        return new CmsLocalizedEntityHolder(updatedSharedProps, newLocalizedProps);})
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
