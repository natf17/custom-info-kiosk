package com.ppublica.apps.kiosk.service.payloads.firstaid;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.LocalizedInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidImage;

public record FirstAidInput(List<LocalizedInput> name, Boolean isWheelchairAccessible, List<LocalizedInput> svgElemId, List<LocalizedInput> note, Long locationId, List<LocalizedInputField<FirstAidImage>> featImg) {}

