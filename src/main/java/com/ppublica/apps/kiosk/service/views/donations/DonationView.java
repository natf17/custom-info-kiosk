package com.ppublica.apps.kiosk.service.views.donations;

public record DonationView(String id, String name, Boolean isWheelchairAccessible, String svgElemId, String note, Long locationId, String paymentTypesAccepted, DonationImage featImg) {}
