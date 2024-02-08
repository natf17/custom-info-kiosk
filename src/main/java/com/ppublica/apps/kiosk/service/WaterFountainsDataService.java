package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;

import com.ppublica.apps.kiosk.service.payloads.waterfountains.WaterFountainInput;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainAdminView;
import com.ppublica.apps.kiosk.service.views.waterfountains.WaterFountainView;

public class WaterFountainsDataService {
    public List<WaterFountainView> getWaterFountains(String locale, String sort) {
        throw new UnsupportedOperationException();
    }

    public List<WaterFountainAdminView> getWaterFountainsAdmin(String sort) {
        throw new UnsupportedOperationException();
    }

    public Optional<WaterFountainAdminView> getWaterFountainAdmin(Long waterFountainId) {
        throw new UnsupportedOperationException();
    }

    public WaterFountainAdminView createWaterFountain(WaterFountainInput data) {
        throw new UnsupportedOperationException();
    }

    public WaterFountainAdminView updateWaterFountain(Long waterFountainId, WaterFountainInput data) {
        throw new UnsupportedOperationException();
    }

    public void deleteWaterFountain(Long waterFountainId) {
        throw new UnsupportedOperationException();
    }
}
