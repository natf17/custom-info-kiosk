package com.ppublica.apps.kiosk.service.views.donations;

import java.util.List;

import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.LocalizedView;

public record DonationAdminView(String id, List<LocalizedField> name, Boolean isWheelchairAccessible, List<LocalizedField> svgElemId, List<LocalizedField> note, Long locationId, String paymentTypesAccepted, List<LocalizedView<DonationImage>> featImg) {}

