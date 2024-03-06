package com.ppublica.apps.kiosk.service.util;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.SeasonalEventKioskCollectionAdapter;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.SeasonalEventKioskCollectionAdapter.Builder;
import com.ppublica.apps.kiosk.service.AdapterBuilderGenerator;

@Component
public class SeasonalEventAdapterBuilderGenerator implements AdapterBuilderGenerator<SeasonalEventKioskCollectionAdapter.Builder> {

    @Override
    public Builder newAdapterBuilder() {
        return new SeasonalEventKioskCollectionAdapter.Builder();
    }
    
}