package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.BathroomKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;

@Service
public class BathroomsDataService extends LocalizedCollectionServiceBase<BathroomKioskCollectionAdapter, BathroomType, BathroomKioskCollectionAdapter.Builder> {
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;


    public List<BathroomView> getBathrooms(String locale, String sort) {
        List<BathroomView> bathroomViews = loadAdapters(CollectionTypeName.BATHROOM, locale, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(bathroom -> buildView(bathroom))
                                                .collect(Collectors.toList());

        // TODO: sort

        return bathroomViews;
    }

    public List<BathroomAdminView> getBathroomsAdmin(String sort) {        

        List<BathroomAdminView> adminViews = loadListOfAdaptersList(CollectionTypeName.BATHROOM, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(bathroomAdapters -> buildAdminView(bathroomAdapters))
                                                .collect(Collectors.toList());

        return adminViews;
    }

    public Optional<BathroomAdminView> getBathroomAdmin(Long bathroomId) {
        List<BathroomKioskCollectionAdapter> adapters = loadAdapters(bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);

        if(adapters.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildAdminView(adapters));

    }

    public BathroomAdminView createBathroom(BathroomInput data) {

        // bathroom input to Bathroom type
        List<BathroomType> bathrooms = toLocalizedBathrooms(data);

        List<BathroomKioskCollectionAdapter> newBathroomAdapters = save(bathrooms, collSharedPropsRepo, collLocalizedPropsRepo);
        
        return buildAdminView(newBathroomAdapters);
    }

    
    public BathroomAdminView updateBathroom(Long bathroomId, BathroomInput data) {
        List<BathroomType> bathrooms = toLocalizedBathrooms(data);

        List<BathroomKioskCollectionAdapter> updatedBathroomAdapters = update(bathrooms, bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);

        return buildAdminView(updatedBathroomAdapters);
    }

    public void deleteBathroom(Long bathroomId) {
        delete(bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);
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
    
    @Override
    protected BathroomKioskCollectionAdapter.Builder getAdapterBuilder() {
        return new BathroomKioskCollectionAdapter.Builder();
    }
    
}
