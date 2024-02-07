package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;

import com.ppublica.apps.kiosk.service.payloads.firstaid.FirstAidInput;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidAdminView;
import com.ppublica.apps.kiosk.service.views.firstaid.FirstAidView;

public class FirstAidDataService {
    public List<FirstAidView> getFirstAids(String locale, String sort) {
        throw new UnsupportedOperationException();
    }

    public List<FirstAidAdminView> getFirstAidsAdmin(String sort) {
        throw new UnsupportedOperationException();
    }

    public Optional<FirstAidAdminView> getFirstAidAdmin(Long firstAidId) {
        throw new UnsupportedOperationException();
    }

    public FirstAidAdminView createFirstAid(FirstAidInput data) {
        throw new UnsupportedOperationException();
    }

    public FirstAidAdminView updateFirstAid(Long firstAidId, FirstAidInput data) {
        throw new UnsupportedOperationException();
    }

    public void deleteFirstAid(Long donationId) {
        throw new UnsupportedOperationException();
    }
}
