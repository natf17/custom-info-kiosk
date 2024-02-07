package com.ppublica.apps.kiosk.service.payloads.donations;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.LocalizedInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;
import com.ppublica.apps.kiosk.service.views.donations.DonationImage;

public record DonationInput(List<LocalizedInput> name, String gender, Boolean isWheelchairAccessible, List<LocalizedInput> svgElemId, List<LocalizedInput> note, Long locationId, List<LocalizedInputField<DonationImage>> featImg) {}
