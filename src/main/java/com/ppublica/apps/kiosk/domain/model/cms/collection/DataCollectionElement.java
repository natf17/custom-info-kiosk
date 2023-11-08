package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;

public interface DataCollectionElement {
    Long getId();
    
    String getType();

    List<TextField> getTextFields();

    List<NumericField> getNumericFields();

    List<BooleanField> getBooleanFields();

    LinkedCollectionField getParentCollection(); //necessary?

}
