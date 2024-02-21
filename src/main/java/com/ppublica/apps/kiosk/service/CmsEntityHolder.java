package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;

public record CmsEntityHolder(CollectionSharedProperties parentProps, List<CollectionLocalizedProperties> localizedPropsList) {


    public List<CmsLocalizedEntityHolder> flatten() {
        return localizedPropsList.stream()
                        .map(localizedProps -> new CmsLocalizedEntityHolder(parentProps, localizedProps))
                        .collect(Collectors.toList());
    }
}
