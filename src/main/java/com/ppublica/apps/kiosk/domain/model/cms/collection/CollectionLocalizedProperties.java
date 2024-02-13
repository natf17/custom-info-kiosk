package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;

public interface CollectionLocalizedProperties {
    CollectionLocalizedProperties withId(Long id);

    Long id();

    CollectionNameField collectionNameField();

    List<TextField> textFields();

    List<NumericField> numericFields();

    List<BooleanField> booleanFields();

    List<ImageField> imageFields();

    CollectionInternals collectionInternals();

    Long parentId();
}
