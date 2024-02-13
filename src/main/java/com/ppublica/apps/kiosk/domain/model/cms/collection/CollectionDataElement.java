package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;

public interface CollectionDataElement {
    CollectionDataElement withId(Long id);

    Long id();
    
    String type();

    String subType();

    List<TextField> textFields();

    List<NumericField> numericFields();

    List<BooleanField> booleanFields();

    Long parent();
}
