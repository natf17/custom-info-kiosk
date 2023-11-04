package com.ppublica.apps.kiosk.domain.model.cms.collection;

import java.util.List;

public interface DataCollectionType {
    DataCollectionType withId(Long id);

    Long getId();

    String getType();

    String getSubType();

    LocalizedFields getLocalizedFields();

    // locale here could be used to store language of field names in LinkedDataElements
    CollectionInternals getCollectionInternals();

    List<LinkedDataElement> getLinkedDataElements();

}
