package com.ppublica.apps.kiosk.service.payloads;

import java.util.List;

public interface AmenityInput {
    List<LocalizedInputField<String>> name();
    Boolean isWheelchairAccessible();
    List<LocalizedInputField<String>> svgElemId();
    List<LocalizedInputField<String>> note();
    Long locationId();
    List<LocalizedInputField<ImageInput>> featImg();
}
