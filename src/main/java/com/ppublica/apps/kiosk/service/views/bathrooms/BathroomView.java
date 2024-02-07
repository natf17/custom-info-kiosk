package com.ppublica.apps.kiosk.service.views.bathrooms;

public record BathroomView(String id, String name, String gender, Boolean isWheelchairAccessible, String svgElemId, String note, Long locationId, BathroomImage featImg) {}
