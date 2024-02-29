package com.ppublica.apps.kiosk.service.payloads.bathrooms;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.AmenityInput;
import com.ppublica.apps.kiosk.service.payloads.ImageInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomImage;

public record BathroomInput(List<LocalizedInputField<String>> name, String gender, Boolean isWheelchairAccessible, List<LocalizedInputField<String>> svgElemId, List<LocalizedInputField<String>> note, Long locationId, List<LocalizedInputField<BathroomImage>> featImg){}
