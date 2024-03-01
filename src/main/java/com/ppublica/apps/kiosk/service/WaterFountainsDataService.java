package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.WaterFountainType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.WaterFountainKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.waterfountains.WaterFountainInput;
import com.ppublica.apps.kiosk.service.util.converter.WaterFountainInputConverter;
import com.ppublica.apps.kiosk.service.util.converter.WaterFountainViewsConverter;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainAdminView;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainView;

public class WaterFountainsDataService extends LocalizedCollectionServiceBase<WaterFountainKioskCollectionAdapter, WaterFountainType, WaterFountainKioskCollectionAdapter.Builder> {
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;

    @Autowired
    private WaterFountainViewsConverter waterFountainViewsConverter;

    @Autowired
    private WaterFountainInputConverter waterFountainInputConverter;

    @Autowired
    private AdapterBuilderGenerator<WaterFountainKioskCollectionAdapter.Builder> adapterBuilderGenerator;
    
    public List<WaterFountainView> getWaterFountains(String locale, String sort) {
        List<WaterFountainView> waterFountainViews = loadAdapters(CollectionType.WATER_FOUNTAIN, locale, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(waterFountain -> waterFountainViewsConverter.buildView(waterFountain))
                                                .collect(Collectors.toList());

        // TODO: sort

        return waterFountainViews;
    }

    public List<WaterFountainAdminView> getWaterFountainsAdmin(String sort) {
        List<WaterFountainAdminView> adminViews = loadListOfAdaptersList(CollectionType.WATER_FOUNTAIN, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(waterFountainAdapters -> waterFountainViewsConverter.buildAdminView(waterFountainAdapters))
                                                .collect(Collectors.toList());

        return adminViews;
    }

    public Optional<WaterFountainAdminView> getWaterFountainAdmin(Long waterFountainId) {
        List<WaterFountainKioskCollectionAdapter> adapters = loadAdapters(waterFountainId, collSharedPropsRepo, collLocalizedPropsRepo);

        if(adapters.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(waterFountainViewsConverter.buildAdminView(adapters));
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public WaterFountainAdminView createWaterFountain(WaterFountainInput data) {
        // water fountain input to water fountain type
        List<? extends WaterFountainType> waterFountains = waterFountainInputConverter.toLocalizedWaterFountains(data);

        List<WaterFountainKioskCollectionAdapter> newWaterFountainAdapters = save(waterFountains, collSharedPropsRepo, collLocalizedPropsRepo);
        
        return waterFountainViewsConverter.buildAdminView(newWaterFountainAdapters);
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public WaterFountainAdminView updateWaterFountain(Long waterFountainId, WaterFountainInput data) {
        List<? extends WaterFountainType> waterFountains = waterFountainInputConverter.toLocalizedWaterFountains(data);

        List<? extends WaterFountainType> updatedWaterFountainsAdapters = update(waterFountains, waterFountainId, collSharedPropsRepo, collLocalizedPropsRepo);

        return waterFountainViewsConverter.buildAdminView(updatedWaterFountainsAdapters);
    }

    public void deleteWaterFountain(Long waterFountainId) {
        delete(waterFountainId, collSharedPropsRepo, collLocalizedPropsRepo);
    }

    @Override
    protected WaterFountainKioskCollectionAdapter.Builder getAdapterBuilder() {
        return adapterBuilderGenerator.newAdapterBuilder();
    }
}
