package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.ppublica.apps.kiosk.service.collection.LocationKey;
import com.ppublica.apps.kiosk.service.payloads.locations.LocationInput;
import com.ppublica.apps.kiosk.service.views.locations.LocationAdminView;
import com.ppublica.apps.kiosk.service.views.locations.LocationView;

public class LocationsDataService {
    

    public List<LocationView> getLocations(String locale, String sort) {
        throw new UnsupportedOperationException();
    }

    public List<LocationAdminView> getLocationsAdmin(String sort) {
        throw new UnsupportedOperationException();
    }

    public Map<LocationKey,LocationView> getBatchLocations(Set<LocationKey> keys) {
        throw new UnsupportedOperationException();
    }

    public Optional<LocationAdminView> getLocationAdmin(Long locationId) {
        throw new UnsupportedOperationException();
    }

    public LocationAdminView createLocation(LocationInput data) {
        throw new UnsupportedOperationException();
    }

    public LocationAdminView updateLocation(Long locationId, LocationInput data) {
        throw new UnsupportedOperationException();
    }

    public void deleteLocation(Long locationId) {
        throw new UnsupportedOperationException();
    }
}
