package com.ppublica.apps.kiosk.service.util;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.LocationKioskCollectionAdapter;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.LocationKioskCollectionAdapter.Builder;
import com.ppublica.apps.kiosk.service.AdapterBuilderGenerator;

@Component
public class LocationAdapterBuilderGenerator implements AdapterBuilderGenerator<LocationKioskCollectionAdapter.Builder> {

    @Override
    public Builder newAdapterBuilder() {
        return new LocationKioskCollectionAdapter.Builder();
    }
    
}
