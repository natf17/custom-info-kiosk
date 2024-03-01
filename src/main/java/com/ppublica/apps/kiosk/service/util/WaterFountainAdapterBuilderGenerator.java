package com.ppublica.apps.kiosk.service.util;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.WaterFountainKioskCollectionAdapter;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.WaterFountainKioskCollectionAdapter.Builder;
import com.ppublica.apps.kiosk.service.AdapterBuilderGenerator;

@Component
public class WaterFountainAdapterBuilderGenerator implements AdapterBuilderGenerator<WaterFountainKioskCollectionAdapter.Builder> {

    @Override
    public Builder newAdapterBuilder() {
        return new WaterFountainKioskCollectionAdapter.Builder();
    }
    
}