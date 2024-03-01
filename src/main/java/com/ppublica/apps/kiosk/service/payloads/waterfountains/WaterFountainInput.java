package com.ppublica.apps.kiosk.service.payloads.waterfountains;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.AmenityInput;
import com.ppublica.apps.kiosk.service.payloads.ImageInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;

public record WaterFountainInput(List<LocalizedInputField<String>> name, Boolean isWheelchairAccessible, List<LocalizedInputField<String>> svgElemId, List<LocalizedInputField<String>> note, Long locationId, List<LocalizedInputField<ImageInput>> featImg) implements AmenityInput{}
