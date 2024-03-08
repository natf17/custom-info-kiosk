package com.ppublica.apps.kiosk.repository.collection;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;

public record CollectionSharedPropertiesQueryResults(Long id, String type, String subType, CollectionNameField collectionNameField, CollectionSharedInternals collectionInternals) {}
