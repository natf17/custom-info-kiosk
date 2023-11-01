package com.ppublica.apps.kiosk.service.views.map;

public record MapPageView(String pageTitle, String pageDescription, TapWidgetView tapWidget, MapViewConfigView mapViewConfig, 
                            LocationAmenityView bathroomAmenity, LocationAmenityView waterFountainAmenity, LocationAmenityView firstAidAmenity,
                            LocationAmenityView donationAmenity, MapImagesView maps) {}
