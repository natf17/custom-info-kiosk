package com.ppublica.apps.kiosk.service.views.waterfountains;

import java.util.List;

import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;

public record WaterFountainAdminView(String id, List<LocalizedField> name, Boolean isWheelchairAccessible, List<LocalizedField> svgElemId, List<LocalizedField> note, Long locationId, List<LocalizedView<WaterFountainImage>> featImg) {}
