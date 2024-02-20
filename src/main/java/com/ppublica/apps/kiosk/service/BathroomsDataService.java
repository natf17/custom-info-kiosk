package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.collection.CollectionTypeName;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;

public class BathroomsDataService {
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;

    public List<BathroomView> getBathrooms(String locale, String sort) {
        List<CollectionSharedProperties> matchingCollSharedProps = collSharedPropsRepo.findByCollectionType(CollectionTypeName.BATHROOM.toString(), null);
        matchingCollSharedProps.stream().map(new CollectionSharedPropertiesImpl)
        throw new UnsupportedOperationException();
    }

    public List<BathroomAdminView> getBathroomsAdmin(String sort) {
        throw new UnsupportedOperationException();
    }

    public Optional<BathroomAdminView> getBathroomAdmin(Long bathroomId) {
        throw new UnsupportedOperationException();
    }

    public BathroomAdminView createBathroom(BathroomInput data) {
        throw new UnsupportedOperationException();
    }

    public BathroomAdminView updateBathroom(Long bathroomId, BathroomInput data) {
        throw new UnsupportedOperationException();
    }

    public void deleteBathroom(Long bathroomId) {
        throw new UnsupportedOperationException();
    }
}
