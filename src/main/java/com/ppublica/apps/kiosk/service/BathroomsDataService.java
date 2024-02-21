package com.ppublica.apps.kiosk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.BathroomKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;

@Service
public class BathroomsDataService {
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;


    public List<BathroomView> getBathrooms(String locale, String sort) {
        // get CollectionSharedProperties
        List<CollectionSharedProperties> matchingCollSharedProps = collSharedPropsRepo.findByCollectionType(CollectionTypeName.BATHROOM.toString(), null);
        
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

        // build a BathroomView from CollectionSharedProperties and the matching CollectionLocalizedProperties 
        List<BathroomView> bathroomViews = matchingCollSharedProps.stream()
                                                                .map(sharedProps -> {
                                                                    return new BathroomKioskCollectionAdapter.Builder()
                                                                                                        .sharedCmsPiece(sharedProps)
                                                                                                        .localizedCmsPiece(matchingCollLocPropsMap.get(sharedProps.id()))
                                                                                                        .build();

                                                                })
                                                                .map(bathroom -> buildView(bathroom))
                                                                .collect(Collectors.toList());


        // TODO: sort

        return bathroomViews;
    }

    public List<BathroomAdminView> getBathroomsAdmin(String sort) {
        // get CollectionSharedProperties
        List<CollectionSharedProperties> matchingCollSharedProps = collSharedPropsRepo.findByCollectionType(CollectionTypeName.BATHROOM.toString(), null);

        if(matchingCollSharedProps.isEmpty()) {
            return List.of();
        }
        
        // build BathroomAdminView from CollectionSharedProperties and matching CollectionLocalizedProperties
        List<BathroomAdminView> adminViews = matchingCollSharedProps
                                                            .stream()
                                                            .map(sharedProps -> {
                                                                List<CollectionLocalizedProperties> localizedProps = collLocalizedPropsRepo.findByParentCollectionId(sharedProps.id());
                                                                return localizedProps.stream().map(localizedProp -> {
                                                                                                        return new BathroomKioskCollectionAdapter.Builder()
                                                                                                                                                .sharedCmsPiece(sharedProps)
                                                                                                                                                .localizedCmsPiece(localizedProp)
                                                                                                                                                .build();
                                                                                                        }).collect(Collectors.toList());
                                                            })
                                                            .map(bathroomAdapters -> buildAdminView(bathroomAdapters))
                                                            .collect(Collectors.toList());


        return adminViews;
    }

    public Optional<BathroomAdminView> getBathroomAdmin(Long bathroomId) {
        throw new UnsupportedOperationException();
    }

    public BathroomAdminView createBathroom(BathroomInput data) {

        // bathroom input to Bathroom type
        List<BathroomType> bathrooms = toLocalizedBathrooms(data);

        // create adapter
        List<BathroomKioskCollectionAdapter> bathroomAdapters = bathrooms.stream()
                                                                            .map(bathroom -> {
                                                                                return new BathroomKioskCollectionAdapter.Builder()
                                                                                    .bathroom(bathroom)
                                                                                    .build();
                                                                            }).collect(Collectors.toList());
        
        
        // save to repo
        CollectionSharedProperties newSharedProps = collSharedPropsRepo.saveInstance(bathroomAdapters.get(0));
        
        List<CollectionLocalizedProperties> newLocalizedPropsList = bathroomAdapters.stream()
                                                                                    .map(bathroomAdapter -> collLocalizedPropsRepo.saveLocalizedInstance(newSharedProps.id(), bathroomAdapter))
                                                                                    .collect(Collectors.toList());

        
        // create adapters
        List<BathroomKioskCollectionAdapter> newBathroomAdapters = newLocalizedPropsList.stream()
                                                                                        .map(newLocalizedProps -> {
                                                                                            return new BathroomKioskCollectionAdapter.Builder()
                                                                                                        .sharedCmsPiece(newSharedProps)
                                                                                                        .localizedCmsPiece(newLocalizedProps)
                                                                                                        .build();
                                                                                        })
                                                                                        .collect(Collectors.toList());
        
        
        return buildAdminView(newBathroomAdapters);
    }

    
    public BathroomAdminView updateBathroom(Long bathroomId, BathroomInput data) {
        List<BathroomType> bathrooms = toLocalizedBathrooms(data);

        List<BathroomKioskCollectionAdapter> bathroomAdapters = bathrooms.stream()
                                                                        .map(bathroom -> {
                                                                            return new BathroomKioskCollectionAdapter.Builder()
                                                                                .bathroom(bathroom)
                                                                                .build();
                                                                        }).collect(Collectors.toList());
        
        collLocalizedPropsRepo.deleteLocalizedCollectionInstances(bathroomId);
        CollectionSharedProperties updatedSharedProps = collSharedPropsRepo.updateCollectionInstance(bathroomId, bathroomAdapters.get(0));

        List<CollectionLocalizedProperties> newLocalizedPropsList = bathroomAdapters.stream()
                                                                                    .map(bathroomAdapter -> collLocalizedPropsRepo.saveLocalizedInstance(bathroomId, bathroomAdapter))
                                                                                    .collect(Collectors.toList());
        
        // create adapters
        List<BathroomKioskCollectionAdapter> updatedBathroomAdapters = newLocalizedPropsList.stream()
                                                                                        .map(newLocalizedProps -> {
                                                                                            return new BathroomKioskCollectionAdapter.Builder()
                                                                                                        .sharedCmsPiece(updatedSharedProps)
                                                                                                        .localizedCmsPiece(newLocalizedProps)
                                                                                                        .build();
                                                                                        })
                                                                                        .collect(Collectors.toList());

        return buildAdminView(updatedBathroomAdapters);
    }

    public void deleteBathroom(Long bathroomId) {
        collSharedPropsRepo.deleteCollectionInstance(bathroomId);
        collLocalizedPropsRepo.deleteLocalizedCollectionInstances(bathroomId);
    }

    private BathroomView buildView(BathroomType bathroom) {
        throw new UnsupportedOperationException();
    }

    private BathroomAdminView buildAdminView(List<? extends BathroomType> bathroom) {
        throw new UnsupportedOperationException();
    }

    // will return list with >= 1 elements
    private List<BathroomType> toLocalizedBathrooms(BathroomInput adminInput) {
        throw new UnsupportedOperationException();
    }

    
}
