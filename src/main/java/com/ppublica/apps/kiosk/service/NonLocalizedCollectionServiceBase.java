package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.NonLocalizableKioskCollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.NonLocalizableCmsCollectionAdapterBuilder;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.NonLocalizableKioskCollectionTypeBaseAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;

public abstract class NonLocalizedCollectionServiceBase <T extends NonLocalizableKioskCollectionTypeBaseAdapter, U extends NonLocalizableKioskCollectionType, B extends NonLocalizableCmsCollectionAdapterBuilder<B, U, T>> {
    /*
     * Requires CollectionSharedProperties to have an id
     */
    
    private List<CollectionSharedProperties> getSharedPropsCmsObjects(CollectionType type, CollectionSharedPropertiesRepository collSharedPropsRepo) {
        // get CollectionSharedProperties
        List<CollectionSharedProperties> matchingCollSharedProps = collSharedPropsRepo.findByCollectionType(type.toString(), null);
        

        return matchingCollSharedProps;
    }

    protected List<T> loadAdapters(CollectionType type, CollectionSharedPropertiesRepository collSharedPropsRepo) {
        List<CollectionSharedProperties> cmsSharedProps = getSharedPropsCmsObjects(type, collSharedPropsRepo);
        List<T> adapters = cmsSharedProps
                                .stream()
                                .map(cmsSharedProp -> createAdapter(cmsSharedProp, getAdapterBuilder()))
                                .collect(Collectors.toList());

        return adapters;
    }

    protected T loadAdapter(Long collId, CollectionSharedPropertiesRepository collSharedPropsRepo) {
        CollectionSharedProperties cmsSharedPros = collSharedPropsRepo.findById(collId);

        if(cmsSharedPros == null) {
            return null;
        }

        return createAdapter(cmsSharedPros, getAdapterBuilder());
    }

    private T createAdapter(CollectionSharedProperties sharedPropsInstance, B builder) {

        return builder.sharedCmsPiece(sharedPropsInstance)
                .build();
    }

    protected T createAdapter(U kioskCollectionType) {
        if(kioskCollectionType == null) {
            throw new RuntimeException("kioskCollection null");
        }
        
        return getAdapterBuilder()
                    .kioskCollection(kioskCollectionType)
                    .build();
        
    }

    protected T save(U kioskCollectionType, CollectionSharedPropertiesRepository collSharedPropsRepo) {
        T adapter = createAdapter(kioskCollectionType);

        CollectionSharedProperties sharedProps = collSharedPropsRepo.saveInstance(adapter);

        return createAdapter(sharedProps, getAdapterBuilder());
    }

    protected T update(U kioskCollectionTypeList, Long collId, CollectionSharedPropertiesRepository collSharedPropsRepo) {
        T adapter = createAdapter(kioskCollectionTypeList);

        CollectionSharedProperties updatedSharedProps = collSharedPropsRepo.updateCollectionInstance(collId, adapter);

        return createAdapter(updatedSharedProps, getAdapterBuilder());

    }
    
    protected void delete(Long collId, CollectionSharedPropertiesRepository collSharedPropsRepo) {
        collSharedPropsRepo.deleteCollectionInstance(collId);
    }
    
    protected abstract B getAdapterBuilder();


}