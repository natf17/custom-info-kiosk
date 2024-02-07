package com.ppublica.apps.kiosk.service.views.firstaid;

import java.util.List;

import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;

public record FirstAidAdminView(String id, List<LocalizedField> name, Boolean isWheelchairAccessible, List<LocalizedField> svgElemId, List<LocalizedField> note, Long locationId, List<LocalizedView<FirstAidImage>> featImg) {}

