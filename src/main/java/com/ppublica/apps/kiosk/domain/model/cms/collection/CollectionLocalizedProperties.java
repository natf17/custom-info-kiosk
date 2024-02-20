package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;

public interface CollectionLocalizedProperties {
    CollectionLocalizedProperties locWithId(Long id);

    Long locId();

    CollectionNameField locCollectionNameField();

    List<TextField> locTextFields();

    List<NumericField> locNumericFields();

    List<BooleanField> locBooleanFields();

    List<ImageField> locImageFields();

    CollectionInternals locCollectionInternals();

    Long parentId();
}
