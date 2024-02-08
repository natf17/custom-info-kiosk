package com.ppublica.apps.kiosk.service.payloads.waterfountains;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.LocalizedInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainImage;

public record WaterFountainInput(List<LocalizedInput> name, Boolean isWheelchairAccessible, List<LocalizedInput> svgElemId, List<LocalizedInput> note, Long locationId, List<LocalizedInputField<WaterFountainImage>> featImg) {}
