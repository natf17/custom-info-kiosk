package com.ppublica.apps.kiosk.service.payloads.donations;

import java.util.List;

import com.ppublica.apps.kiosk.service.payloads.AmenityInput;
import com.ppublica.apps.kiosk.service.payloads.ImageInput;
import com.ppublica.apps.kiosk.service.payloads.LocalizedInputField;

public record DonationInput(List<LocalizedInputField<String>> name, String paymentTypesAccepted, Boolean isWheelchairAccessible, List<LocalizedInputField<String>> svgElemId, List<LocalizedInputField<String>> note, Long locationId, List<LocalizedInputField<ImageInput>> featImg) implements AmenityInput {}
