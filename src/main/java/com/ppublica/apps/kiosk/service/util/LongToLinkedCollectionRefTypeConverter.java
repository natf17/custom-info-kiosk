package com.ppublica.apps.kiosk.service.util;

import com.ppublica.apps.kiosk.domain.model.kiosk.LinkedCollectionReference;

public class LongToLinkedCollectionRefTypeConverter implements ValueConverter<Long, LinkedCollectionReference> {
    public LinkedCollectionReference convert(Long input) {
        return input != null ? new  LinkedCollectionReference(input): null;
    }
}
