package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppublica.apps.kiosk.domain.model.kiosk.BathroomType;
import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.BathroomKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;
import com.ppublica.apps.kiosk.service.util.converter.BathroomInputConverter;
import com.ppublica.apps.kiosk.service.util.converter.BathroomViewsConverter;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;

@Service
public class BathroomsDataService extends LocalizedCollectionServiceBase<BathroomKioskCollectionAdapter, BathroomType, BathroomKioskCollectionAdapter.Builder> {
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;

    @Autowired
    private BathroomViewsConverter bathroomsViewsConverter;

    @Autowired
    private BathroomInputConverter bathroomInputConverter;


    public List<BathroomView> getBathrooms(String locale, String sort) {
        List<BathroomView> bathroomViews = loadAdapters(CollectionType.BATHROOM, locale, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(bathroom -> bathroomsViewsConverter.buildView(bathroom))
                                                .collect(Collectors.toList());

        // TODO: sort

        return bathroomViews;
    }

    public List<BathroomAdminView> getBathroomsAdmin(String sort) {        

        List<BathroomAdminView> adminViews = loadListOfAdaptersList(CollectionType.BATHROOM, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(bathroomAdapters -> bathroomsViewsConverter.buildAdminView(bathroomAdapters))
                                                .collect(Collectors.toList());

        return adminViews;
    }

    public Optional<BathroomAdminView> getBathroomAdmin(Long bathroomId) {
        List<BathroomKioskCollectionAdapter> adapters = loadAdapters(bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);

        if(adapters.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(bathroomsViewsConverter.buildAdminView(adapters));

    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public BathroomAdminView createBathroom(BathroomInput data) {

        // bathroom input to Bathroom type
        List<? extends BathroomType> bathrooms = bathroomInputConverter.toLocalizedBathrooms(data);

        List<BathroomKioskCollectionAdapter> newBathroomAdapters = save(bathrooms, collSharedPropsRepo, collLocalizedPropsRepo);
        
        return bathroomsViewsConverter.buildAdminView(newBathroomAdapters);
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public BathroomAdminView updateBathroom(Long bathroomId, BathroomInput data) {
        List<? extends BathroomType> bathrooms = bathroomInputConverter.toLocalizedBathrooms(data);

        List<BathroomKioskCollectionAdapter> updatedBathroomAdapters = update(bathrooms, bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);

        return bathroomsViewsConverter.buildAdminView(updatedBathroomAdapters);
    }

    public void deleteBathroom(Long bathroomId) {
        delete(bathroomId, collSharedPropsRepo, collLocalizedPropsRepo);
    }

    
    @Override
    protected BathroomKioskCollectionAdapter.Builder getAdapterBuilder() {
        return new BathroomKioskCollectionAdapter.Builder();
    }

    
    
}
