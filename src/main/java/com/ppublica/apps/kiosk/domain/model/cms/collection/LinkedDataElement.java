package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;

public interface LinkedDataElement {
    Long getId();
    
    String getType();

    CollectionNameField getCollectionNameField();

    List<TextField> getTextFields();

    List<NumericField> getNumericFields();

    List<BooleanField> getBooleanFields();

    DataElementCollectionInternals getDataElementCollectionInternals();

    LinkedCollectionField getLinkedCollectionField(); 

}
