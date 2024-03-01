package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.FirstAidType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.FirstAidKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.firstaid.FirstAidInput;
import com.ppublica.apps.kiosk.service.util.converter.FirstAidInputConverter;
import com.ppublica.apps.kiosk.service.util.converter.FirstAidViewsConverter;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidAdminView;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidView;

public class FirstAidDataService extends LocalizedCollectionServiceBase<FirstAidKioskCollectionAdapter, FirstAidType, FirstAidKioskCollectionAdapter.Builder>{
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;

    @Autowired
    private FirstAidViewsConverter firstAidViewsConverter;

    @Autowired
    private FirstAidInputConverter firstAidInputConverter;

    @Autowired
    private AdapterBuilderGenerator<FirstAidKioskCollectionAdapter.Builder> adapterBuilderGenerator;

    public List<FirstAidView> getFirstAids(String locale, String sort) {
        List<FirstAidView> firstAidViews = loadAdapters(CollectionType.FIRST_AID, locale, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(firstAid -> firstAidViewsConverter.buildView(firstAid))
                                                .collect(Collectors.toList());

        // TODO: sort

        return firstAidViews;
    }

    public List<FirstAidAdminView> getFirstAidsAdmin(String sort) {
        List<FirstAidAdminView> adminViews = loadListOfAdaptersList(CollectionType.FIRST_AID, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(firstAidAdapters -> firstAidViewsConverter.buildAdminView(firstAidAdapters))
                                                .collect(Collectors.toList());

        return adminViews;
    }

    public Optional<FirstAidAdminView> getFirstAidAdmin(Long firstAidId) {
        List<FirstAidKioskCollectionAdapter> adapters = loadAdapters(firstAidId, collSharedPropsRepo, collLocalizedPropsRepo);

        if(adapters.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(firstAidViewsConverter.buildAdminView(adapters));
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public FirstAidAdminView createFirstAid(FirstAidInput data) {
        // first aid input to first aid type
        List<? extends FirstAidType> firstAids = firstAidInputConverter.toLocalizedFirstAids(data);

        List<FirstAidKioskCollectionAdapter> newFirstAidAdapters = save(firstAids, collSharedPropsRepo, collLocalizedPropsRepo);
        
        return firstAidViewsConverter.buildAdminView(newFirstAidAdapters);
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public FirstAidAdminView updateFirstAid(Long firstAidId, FirstAidInput data) {
        List<? extends FirstAidType> firstAids = firstAidInputConverter.toLocalizedFirstAids(data);

        List<? extends FirstAidType> updatedFirstAidAdapters = update(firstAids, firstAidId, collSharedPropsRepo, collLocalizedPropsRepo);

        return firstAidViewsConverter.buildAdminView(updatedFirstAidAdapters);
    }

    public void deleteFirstAid(Long firstAidId) {
        delete(firstAidId, collSharedPropsRepo, collLocalizedPropsRepo);
    }

    @Override
    protected FirstAidKioskCollectionAdapter.Builder getAdapterBuilder() {
        return adapterBuilderGenerator.newAdapterBuilder();
    }
}
