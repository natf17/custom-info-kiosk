package com.ppublica.apps.kiosk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppublica.apps.kiosk.domain.model.kiosk.CollectionType;
import com.ppublica.apps.kiosk.domain.model.kiosk.LocationType;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.LocationKioskCollectionAdapter;
import com.ppublica.apps.kiosk.repository.collection.CollectionLocalizedPropertiesRepository;
import com.ppublica.apps.kiosk.repository.collection.CollectionSharedPropertiesRepository;
import com.ppublica.apps.kiosk.service.payloads.locations.LocationInput;
import com.ppublica.apps.kiosk.service.util.LocationKey;
import com.ppublica.apps.kiosk.service.util.converter.LocationInputConverter;
import com.ppublica.apps.kiosk.service.util.converter.LocationViewsConverter;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;
import com.ppublica.apps.kiosk.service.views.locations.LocationAdminView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;
import com.ppublica.apps.kiosk.service.views.locations.MapImage;

@Service
public class LocationsDataService extends LocalizedCollectionServiceBase<LocationKioskCollectionAdapter, LocationType, LocationKioskCollectionAdapter.Builder> {
    @Autowired
    private CollectionSharedPropertiesRepository collSharedPropsRepo;
    
    @Autowired
    private CollectionLocalizedPropertiesRepository collLocalizedPropsRepo;

    @Autowired
    private LocationViewsConverter locationViewsConverter;

    @Autowired
    private LocationInputConverter locationInputConverter;

    @Autowired
    private AdapterBuilderGenerator<LocationKioskCollectionAdapter.Builder> adapterBuilderGenerator;

    public List<LocationView> getLocations(String locale, String sort) {
        List<LocationView> locationViews = loadAdapters(CollectionType.LOCATION, locale, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(location -> locationViewsConverter.buildView(location))
                                                .collect(Collectors.toList());

        // TODO: sort

        return locationViews;
    }

    public List<LocationAdminView> getLocationsAdmin(String sort) {
        List<LocationAdminView> adminViews = loadListOfAdaptersList(CollectionType.LOCATION, collSharedPropsRepo, collLocalizedPropsRepo)
                                                .stream()
                                                .map(locationAdapters -> locationViewsConverter.buildAdminView(locationAdapters))
                                                .collect(Collectors.toList());

        return adminViews;
    }

    // maybe could be optimized?
    public Map<LocationKey,LocationView> getBatchLocations(Set<LocationKey> keys) {
        Map<LocationKey,LocationView> locationsMap = new HashMap<>();
        for(LocationKey key : keys) {
            Optional<LocationAdminView> adminViewOpt = getLocationAdmin(key.locationId());
            
            if(adminViewOpt.isEmpty()) {
                locationsMap.put(key, null);
                continue;
            }
            LocationAdminView adminView = adminViewOpt.get();
            
            LocationView view = new LocationView(adminView.id(), fieldMatchingLocale(adminView.fullname(), key.locale()), adminView.level_num(), fieldMatchingLocale(adminView.level_name(), key.locale()), mapFieldMatchingLocale(adminView.map(), key.locale()));

            locationsMap.put(key, view);
        }

        return locationsMap;
    }

    public Optional<LocationAdminView> getLocationAdmin(Long locationId) {
        List<LocationKioskCollectionAdapter> adapters = loadAdapters(locationId, collSharedPropsRepo, collLocalizedPropsRepo);

        if(adapters.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(locationViewsConverter.buildAdminView(adapters));
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public LocationAdminView createLocation(LocationInput data) {
        // locaiton input to location type
        List<? extends LocationType> locations = locationInputConverter.toLocalizedLocations(data);

        List<LocationKioskCollectionAdapter> newBathroomAdapters = save(locations, collSharedPropsRepo, collLocalizedPropsRepo);
        
        return locationViewsConverter.buildAdminView(newBathroomAdapters);
    }

    /*
     * Requires each LocalizedInput to have a locale id
     */
    public LocationAdminView updateLocation(Long locationId, LocationInput data) {
        List<? extends LocationType> locations = locationInputConverter.toLocalizedLocations(data);

        List<? extends LocationType> updatedLocationAdapters = update(locations, locationId, collSharedPropsRepo, collLocalizedPropsRepo);

        return locationViewsConverter.buildAdminView(updatedLocationAdapters);
    }

    public void deleteLocation(Long locationId) {
        delete(locationId, collSharedPropsRepo, collLocalizedPropsRepo);
    }

    @Override
    protected LocationKioskCollectionAdapter.Builder getAdapterBuilder() {
        return adapterBuilderGenerator.newAdapterBuilder();
    }

    private String fieldMatchingLocale(List<LocalizedField> fields, String locale) {
        LocalizedField matchingField =  fields.stream()
                                                .filter(field -> field.locale().equals(locale))
                                                .findFirst()
                                            .get();

        return matchingField != null ? matchingField.value() : null;
    }

    private MapImage mapFieldMatchingLocale(List<LocalizedView<MapImage>> fields, String locale) {
        LocalizedView<MapImage> matchingField =  fields.stream()
                                                        .filter(field -> field.locale().equals(locale))
                                                        .findFirst()
                                                    .get();

        return matchingField != null ? matchingField.value() : null;
    }
}
