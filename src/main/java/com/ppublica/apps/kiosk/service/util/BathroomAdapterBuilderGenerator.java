package com.ppublica.apps.kiosk.service.util;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.BathroomKioskCollectionAdapter;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.BathroomKioskCollectionAdapter.Builder;
import com.ppublica.apps.kiosk.service.AdapterBuilderGenerator;

@Component
public class BathroomAdapterBuilderGenerator implements AdapterBuilderGenerator<BathroomKioskCollectionAdapter.Builder> {

    @Override
    public Builder newAdapterBuilder() {
        return new BathroomKioskCollectionAdapter.Builder();
    }
    
}
