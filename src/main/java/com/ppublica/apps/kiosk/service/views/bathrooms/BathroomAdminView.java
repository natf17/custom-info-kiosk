package com.ppublica.apps.kiosk.service.views.bathrooms;

import java.util.List;

import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;

public record BathroomAdminView(String id, List<LocalizedField> name, String gender, Boolean isWheelchairAccessible, List<LocalizedField> svgElemId, List<LocalizedField> note, Long locationId, List<LocalizedView<BathroomImage>> featImg) {}
