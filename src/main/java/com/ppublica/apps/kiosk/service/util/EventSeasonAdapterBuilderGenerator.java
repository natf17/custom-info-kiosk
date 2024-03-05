package com.ppublica.apps.kiosk.service.util;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.EventSeasonKioskCollectionAdapter;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.EventSeasonKioskCollectionAdapter.Builder;
import com.ppublica.apps.kiosk.service.AdapterBuilderGenerator;

@Component
public class EventSeasonAdapterBuilderGenerator implements AdapterBuilderGenerator<EventSeasonKioskCollectionAdapter.Builder> {

    @Override
    public Builder newAdapterBuilder() {
        return new EventSeasonKioskCollectionAdapter.Builder();
    }
    
}