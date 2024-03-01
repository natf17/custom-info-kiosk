package com.ppublica.apps.kiosk.service.util;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.DonationsKioskCollectionAdapter;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.DonationsKioskCollectionAdapter.Builder;
import com.ppublica.apps.kiosk.service.AdapterBuilderGenerator;

@Component
public class DonationsAdapterBuilderGenerator implements AdapterBuilderGenerator<DonationsKioskCollectionAdapter.Builder> {

    @Override
    public Builder newAdapterBuilder() {
        return new DonationsKioskCollectionAdapter.Builder();
    }
    
}
