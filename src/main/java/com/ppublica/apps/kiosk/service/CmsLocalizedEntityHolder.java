package com.ppublica.apps.kiosk.service;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;

public record CmsLocalizedEntityHolder(CollectionSharedProperties parentProps, CollectionLocalizedProperties localizedProps) {}
