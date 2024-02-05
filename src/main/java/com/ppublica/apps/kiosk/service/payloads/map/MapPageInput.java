package com.ppublica.apps.kiosk.service.payloads.map;

public record MapPageInput(String pageTitle, String pageDescription, TapWidgetViewInput tapWidget, MapViewConfigViewInput mapViewConfig, 
                            LocationAmenityViewInput bathroomAmenity, LocationAmenityViewInput waterFountainAmenity, LocationAmenityViewInput firstAidAmenity,
                            LocationAmenityViewInput donationAmenity, MapImagesViewInput maps) {}