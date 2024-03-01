package com.ppublica.apps.kiosk.service.util;

import org.springframework.stereotype.Component;

import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.FirstAidKioskCollectionAdapter;
import com.ppublica.apps.kiosk.domain.model.kiosk.adapter.FirstAidKioskCollectionAdapter.Builder;
import com.ppublica.apps.kiosk.service.AdapterBuilderGenerator;

@Component
public class FirstAidAdapterBuilderGenerator implements AdapterBuilderGenerator<FirstAidKioskCollectionAdapter.Builder> {

    @Override
    public Builder newAdapterBuilder() {
        return new FirstAidKioskCollectionAdapter.Builder();
    }
    
}
