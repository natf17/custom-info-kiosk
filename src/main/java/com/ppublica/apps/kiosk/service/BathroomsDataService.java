package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;

import com.ppublica.apps.kiosk.service.payloads.bathrooms.BathroomInput;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomAdminView;
import com.ppublica.apps.kiosk.service.views.bathrooms.BathroomView;

public class BathroomsDataService {
    public List<BathroomView> getBathrooms(String locale, String sort) {
        throw new UnsupportedOperationException();
    }

    public List<BathroomAdminView> getBathroomsAdmin(String sort) {
        throw new UnsupportedOperationException();
    }

    public Optional<BathroomAdminView> getBathroomAdmin(Long bathroomId) {
        throw new UnsupportedOperationException();
    }

    public BathroomAdminView createBathroom(BathroomInput data) {
        throw new UnsupportedOperationException();
    }

    public BathroomAdminView updateBathroom(Long bathroomId, BathroomInput data) {
        throw new UnsupportedOperationException();
    }

    public void deleteBathroom(Long bathroomId) {
        throw new UnsupportedOperationException();
    }
}
