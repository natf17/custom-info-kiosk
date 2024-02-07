package com.ppublica.apps.kiosk.service;

import java.util.List;
import java.util.Optional;

import com.ppublica.apps.kiosk.service.payloads.donations.DonationInput;
import com.ppublica.apps.kiosk.service.views.donations.DonationAdminView;
import com.ppublica.apps.kiosk.service.views.donations.DonationView;

public class DonationsDataService {
    public List<DonationView> getDonations(String locale, String sort) {
        throw new UnsupportedOperationException();
    }

    public List<DonationAdminView> getDonationsAdmin(String sort) {
        throw new UnsupportedOperationException();
    }

    public Optional<DonationAdminView> getDonationAdmin(Long donationId) {
        throw new UnsupportedOperationException();
    }

    public DonationAdminView createDonation(DonationInput data) {
        throw new UnsupportedOperationException();
    }

    public DonationAdminView updateDonation(Long donationId, DonationInput data) {
        throw new UnsupportedOperationException();
    }

    public void deleteDonation(Long donationId) {
        throw new UnsupportedOperationException();
    }
}
