package com.ppublica.apps.kiosk.service.payloads.bathrooms;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.LocalizedInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomImage;

public record BathroomInput(List<LocalizedInput> name, String gender, Boolean isWheelchairAccessible, List<LocalizedInput> svgElemId, List<LocalizedInput> note, Long locationId, List<LocalizedInputField<BathroomImage>> featImg) {}
