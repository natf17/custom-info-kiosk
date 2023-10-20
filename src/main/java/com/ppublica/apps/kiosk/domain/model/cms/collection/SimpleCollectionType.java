package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;


public interface SimpleCollectionType {

    SimpleCollectionType withId(Long id);

    Long getId();

    String getType();

    CollectionNameField getCollectionNameField();

    List<TextField> getTextFields();

    List<NumericField> getNumericFields();

    List<BooleanField> getBooleanFields();

    List<ImageField> getImageFields();

    CollectionInternals getCollectionInternals();

    List<LinkedCollectionField> getLinkedCollectionFields();

}
