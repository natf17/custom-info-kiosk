package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;


public interface SimpleCollectionType {

    Long getId();

    String getType();

    CollectionNameField getCollectionNameField();

    List<TextField> getTextFields();

    List<NumericField> getNumericFields();

    List<ImageField> getImageFields();

    CollectionInternals getCollectionInternals();

}
