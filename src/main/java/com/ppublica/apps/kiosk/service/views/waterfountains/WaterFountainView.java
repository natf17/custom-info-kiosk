package com.ppublica.apps.kiosk.service.views.waterfountains;

public record WaterFountainView(String id, String name, Boolean isWheelchairAccessible, String svgElemId, String note, Long locationId, WaterFountainImage featImg) {}