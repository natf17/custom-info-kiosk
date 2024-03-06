package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;

public interface CollectionSharedProperties {
    CollectionSharedProperties withId(Long id);

    Long id();

    String type();

    String subType();

    CollectionNameField collectionNameField();

    List<TextField> textFields();

    List<NumericField> numericFields();

    List<BooleanField> booleanFields();

    List<ImageField> imageFields();

    CollectionSharedInternals collectionSharedInternals();

    List<LinkedCollectionField> linkedCollectionFields();

    List<CollectionRelationship> collectionRelationships();
}
